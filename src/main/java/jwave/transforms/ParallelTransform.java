package jwave.transforms;

import jwave.exceptions.JWaveException;
import jwave.tools.MathToolKit;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.Callable;

/**
 * Parallel wrapper for BasicTransform implementations that provides
 * multi-threaded execution for 2D and 3D transforms.
 * 
 * This class wraps any existing transform (DFT, FWT, WPT) and executes
 * row/column/plane operations in parallel for improved performance on
 * multi-core systems.
 * 
 * @author Stephen Romano
 */
public class ParallelTransform extends BasicTransform {

    private final BasicTransform _transform;
    private final ForkJoinPool _pool;
    private final int _parallelism;
    private static final int MIN_PARALLEL_SIZE = 16;

    public ParallelTransform(BasicTransform transform) {
        this(transform, ForkJoinPool.getCommonPoolParallelism());
    }

    public ParallelTransform(BasicTransform transform, int parallelism) {
        this._transform = transform;
        this._parallelism = parallelism;
        this._pool = new ForkJoinPool(parallelism);
    }

    @Override
    public double[] forward(double[] arrTime) throws JWaveException {
        return _transform.forward(arrTime);
    }

    @Override
    public double[] forward(double[] arrTime, int arrTimeLength) throws JWaveException {
        // For 1D transforms, delegate to the wrapped transform
        return _transform.forward(arrTime, arrTimeLength);
    }

    @Override
    public double[] reverse(double[] arrFreq) throws JWaveException {
        return _transform.reverse(arrFreq);
    }

    @Override
    public double[] reverse(double[] arrFreq, int arrFreqLength) throws JWaveException {
        // For 1D transforms, delegate to the wrapped transform
        return _transform.reverse(arrFreq, arrFreqLength);
    }

    @Override
    public double[][] forward(double[][] matTime) throws JWaveException {
        int maxM = MathToolKit.getExponent(matTime.length);
        int maxN = MathToolKit.getExponent(matTime[0].length);
        return forward(matTime, maxM, maxN);
    }

    @Override
    public double[][] forward(double[][] matTime, int lvlM, int lvlN) throws JWaveException {
        int noOfRows = matTime.length;
        int noOfCols = matTime[0].length;

        // Skip parallel execution for small matrices
        if (noOfRows < MIN_PARALLEL_SIZE || noOfCols < MIN_PARALLEL_SIZE) {
            return _transform.forward(matTime, lvlM, lvlN);
        }

        double[][] matFreq = new double[noOfRows][noOfCols];

        try {
            // Transform rows in parallel
            _pool.invoke(new RowTransformTask(matTime, matFreq, 0, noOfRows, lvlN, true));

            // Transform columns in parallel
            _pool.invoke(new ColumnTransformTask(matFreq, matFreq, 0, noOfCols, lvlM, true));

        } catch (Exception e) {
            throw new JWaveException("Error in parallel 2D forward transform: " + e.getMessage());
        }

        return matFreq;
    }

    @Override
    public double[][] reverse(double[][] matFreq) throws JWaveException {
        int maxM = MathToolKit.getExponent(matFreq.length);
        int maxN = MathToolKit.getExponent(matFreq[0].length);
        return reverse(matFreq, maxM, maxN);
    }

    @Override
    public double[][] reverse(double[][] matFreq, int lvlM, int lvlN) throws JWaveException {
        int noOfRows = matFreq.length;
        int noOfCols = matFreq[0].length;

        // Skip parallel execution for small matrices
        if (noOfRows < MIN_PARALLEL_SIZE || noOfCols < MIN_PARALLEL_SIZE) {
            return _transform.reverse(matFreq, lvlM, lvlN);
        }

        double[][] matTime = new double[noOfRows][noOfCols];

        try {
            // Reverse transform columns in parallel
            _pool.invoke(new ColumnTransformTask(matFreq, matTime, 0, noOfCols, lvlM, false));

            // Reverse transform rows in parallel
            _pool.invoke(new RowTransformTask(matTime, matTime, 0, noOfRows, lvlN, false));

        } catch (Exception e) {
            throw new JWaveException("Error in parallel 2D reverse transform: " + e.getMessage());
        }

        return matTime;
    }

    @Override
    public double[][][] forward(double[][][] spcTime) throws JWaveException {
        int maxP = MathToolKit.getExponent(spcTime.length);
        int maxQ = MathToolKit.getExponent(spcTime[0].length);
        int maxR = MathToolKit.getExponent(spcTime[0][0].length);
        return forward(spcTime, maxP, maxQ, maxR);
    }

    @Override
    public double[][][] forward(double[][][] spcTime, int lvlP, int lvlQ, int lvlR) throws JWaveException {
        int noOfRows = spcTime.length;
        int noOfCols = spcTime[0].length;
        int noOfHigh = spcTime[0][0].length;

        double[][][] spcHilb = new double[noOfRows][noOfCols][noOfHigh];

        try {
            // Process 2D slices in parallel
            List<Future<?>> futures = new ArrayList<>();
            for (int i = 0; i < noOfRows; i++) {
                final int idx = i;
                futures.add(_pool.submit(() -> {
                    try {
                        double[][] slice = spcTime[idx];
                        double[][] transformed = forward(slice, lvlP, lvlQ);
                        spcHilb[idx] = transformed;
                    } catch (JWaveException e) {
                        throw new RuntimeException(e);
                    }
                }));
            }

            // Wait for all 2D transforms to complete
            for (Future<?> future : futures) {
                future.get();
            }

            // Process remaining dimension in parallel
            _pool.invoke(new Space3DTransformTask(spcHilb, spcHilb, 0, noOfCols, noOfHigh, lvlR, true));

        } catch (Exception e) {
            throw new JWaveException("Error in parallel 3D forward transform: " + e.getMessage());
        }

        return spcHilb;
    }

    @Override
    public double[][][] reverse(double[][][] spcHilb) throws JWaveException {
        int maxP = MathToolKit.getExponent(spcHilb.length);
        int maxQ = MathToolKit.getExponent(spcHilb[0].length);
        int maxR = MathToolKit.getExponent(spcHilb[0][0].length);
        return reverse(spcHilb, maxP, maxQ, maxR);
    }

    @Override
    public double[][][] reverse(double[][][] spcHilb, int lvlP, int lvlQ, int lvlR) throws JWaveException {
        int noOfRows = spcHilb.length;
        int noOfCols = spcHilb[0].length;
        int noOfHigh = spcHilb[0][0].length;

        double[][][] spcTime = new double[noOfRows][noOfCols][noOfHigh];

        try {
            // Reverse the third dimension first
            _pool.invoke(new Space3DTransformTask(spcHilb, spcTime, 0, noOfCols, noOfHigh, lvlR, false));

            // Process 2D slices in parallel
            List<Future<?>> futures = new ArrayList<>();
            for (int i = 0; i < noOfRows; i++) {
                final int idx = i;
                futures.add(_pool.submit(() -> {
                    try {
                        double[][] slice = spcTime[idx];
                        double[][] transformed = reverse(slice, lvlP, lvlQ);
                        spcTime[idx] = transformed;
                    } catch (JWaveException e) {
                        throw new RuntimeException(e);
                    }
                }));
            }

            // Wait for all 2D transforms to complete
            for (Future<?> future : futures) {
                future.get();
            }

        } catch (Exception e) {
            throw new JWaveException("Error in parallel 3D reverse transform: " + e.getMessage());
        }

        return spcTime;
    }

    private class RowTransformTask extends RecursiveAction {
        private final double[][] input;
        private final double[][] output;
        private final int startRow;
        private final int endRow;
        private final int level;
        private final boolean isForward;

        RowTransformTask(double[][] input, double[][] output, int startRow, int endRow, int level, boolean isForward) {
            this.input = input;
            this.output = output;
            this.startRow = startRow;
            this.endRow = endRow;
            this.level = level;
            this.isForward = isForward;
        }

        @Override
        protected void compute() {
            int rows = endRow - startRow;
            
            if (rows <= MIN_PARALLEL_SIZE) {
                computeDirectly();
                return;
            }

            int mid = startRow + rows / 2;
            
            RowTransformTask leftTask = new RowTransformTask(input, output, startRow, mid, level, isForward);
            RowTransformTask rightTask = new RowTransformTask(input, output, mid, endRow, level, isForward);
            
            leftTask.fork();
            rightTask.compute();
            leftTask.join();
        }

        private void computeDirectly() {
            try {
                for (int i = startRow; i < endRow; i++) {
                    if (isForward) {
                        output[i] = _transform.forward(input[i].clone(), level);
                    } else {
                        output[i] = _transform.reverse(input[i].clone(), level);
                    }
                }
            } catch (JWaveException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private class ColumnTransformTask extends RecursiveAction {
        private final double[][] input;
        private final double[][] output;
        private final int startCol;
        private final int endCol;
        private final int level;
        private final boolean isForward;

        ColumnTransformTask(double[][] input, double[][] output, int startCol, int endCol, int level, boolean isForward) {
            this.input = input;
            this.output = output;
            this.startCol = startCol;
            this.endCol = endCol;
            this.level = level;
            this.isForward = isForward;
        }

        @Override
        protected void compute() {
            int cols = endCol - startCol;
            
            if (cols <= MIN_PARALLEL_SIZE) {
                computeDirectly();
                return;
            }

            int mid = startCol + cols / 2;
            
            ColumnTransformTask leftTask = new ColumnTransformTask(input, output, startCol, mid, level, isForward);
            ColumnTransformTask rightTask = new ColumnTransformTask(input, output, mid, endCol, level, isForward);
            
            leftTask.fork();
            rightTask.compute();
            leftTask.join();
        }

        private void computeDirectly() {
            try {
                int noOfRows = input.length;
                
                for (int j = startCol; j < endCol; j++) {
                    double[] column = new double[noOfRows];
                    
                    for (int i = 0; i < noOfRows; i++) {
                        column[i] = input[i][j];
                    }
                    
                    double[] result;
                    if (isForward) {
                        result = _transform.forward(column, level);
                    } else {
                        result = _transform.reverse(column, level);
                    }
                    
                    for (int i = 0; i < noOfRows; i++) {
                        output[i][j] = result[i];
                    }
                }
            } catch (JWaveException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private class Space3DTransformTask extends RecursiveAction {
        private final double[][][] input;
        private final double[][][] output;
        private final int startCol;
        private final int endCol;
        private final int noOfHigh;
        private final int level;
        private final boolean isForward;

        Space3DTransformTask(double[][][] input, double[][][] output, int startCol, int endCol, 
                           int noOfHigh, int level, boolean isForward) {
            this.input = input;
            this.output = output;
            this.startCol = startCol;
            this.endCol = endCol;
            this.noOfHigh = noOfHigh;
            this.level = level;
            this.isForward = isForward;
        }

        @Override
        protected void compute() {
            int cols = endCol - startCol;
            
            if (cols <= MIN_PARALLEL_SIZE / 2) {
                computeDirectly();
                return;
            }

            int mid = startCol + cols / 2;
            
            Space3DTransformTask leftTask = new Space3DTransformTask(input, output, startCol, mid, 
                                                                    noOfHigh, level, isForward);
            Space3DTransformTask rightTask = new Space3DTransformTask(input, output, mid, endCol, 
                                                                     noOfHigh, level, isForward);
            
            leftTask.fork();
            rightTask.compute();
            leftTask.join();
        }

        private void computeDirectly() {
            try {
                int noOfRows = input.length;
                
                for (int j = startCol; j < endCol; j++) {
                    for (int k = 0; k < noOfHigh; k++) {
                        double[] arr = new double[noOfRows];
                        
                        for (int i = 0; i < noOfRows; i++) {
                            arr[i] = input[i][j][k];
                        }
                        
                        double[] result;
                        if (isForward) {
                            result = _transform.forward(arr, level);
                        } else {
                            result = _transform.reverse(arr, level);
                        }
                        
                        for (int i = 0; i < noOfRows; i++) {
                            output[i][j][k] = result[i];
                        }
                    }
                }
            } catch (JWaveException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void shutdown() {
        _pool.shutdown();
    }
}