package jwave.transforms;

import jwave.datatypes.natives.Complex;
import jwave.exceptions.JWaveException;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * Parallel implementation of the Discrete Fourier Transform using ForkJoinPool.
 * This implementation divides the computation across multiple threads for improved
 * performance on multi-core systems.
 * 
 * @author Stephen Romano
 */
public class ParallelDiscreteFourierTransform extends BasicTransform {

    private static final int SEQUENTIAL_THRESHOLD = 64;
    private final ForkJoinPool pool;
    private final int parallelism;

    public ParallelDiscreteFourierTransform() {
        this(ForkJoinPool.getCommonPoolParallelism());
    }

    public ParallelDiscreteFourierTransform(int parallelism) {
        this.parallelism = parallelism;
        this.pool = new ForkJoinPool(parallelism);
    }

    @Override
    public double[] forward(double[] arrTime) throws JWaveException {
        return forward(arrTime, arrTime.length);
    }

    @Override
    public double[] forward(double[] arrTime, int arrTimeLength) {
        double[] arrFreq = new double[arrTimeLength];
        
        if (arrTimeLength <= SEQUENTIAL_THRESHOLD) {
            // Fall back to sequential for small arrays
            return sequentialForward(arrTime, arrTimeLength);
        }
        
        try {
            pool.invoke(new ParallelDFTTask(arrTime, arrFreq, 0, arrTimeLength, true));
        } finally {
            // Don't shut down the pool, it can be reused
        }
        
        return arrFreq;
    }

    @Override
    public double[] reverse(double[] arrFreq) throws JWaveException {
        return reverse(arrFreq, arrFreq.length);
    }

    @Override
    public double[] reverse(double[] arrFreq, int arrFreqLength) {
        double[] arrTime = new double[arrFreqLength];
        
        if (arrFreqLength <= SEQUENTIAL_THRESHOLD) {
            // Fall back to sequential for small arrays
            return sequentialReverse(arrFreq, arrFreqLength);
        }
        
        try {
            pool.invoke(new ParallelDFTTask(arrFreq, arrTime, 0, arrFreqLength, false));
        } finally {
            // Don't shut down the pool, it can be reused
        }
        
        return arrTime;
    }

    public Complex[] forward(Complex[] arrTime) {
        int n = arrTime.length;
        Complex[] arrFreq = new Complex[n];
        
        if (n <= SEQUENTIAL_THRESHOLD) {
            return sequentialForwardComplex(arrTime);
        }
        
        try {
            pool.invoke(new ParallelComplexDFTTask(arrTime, arrFreq, 0, n, true));
        } finally {
            // Don't shut down the pool, it can be reused
        }
        
        return arrFreq;
    }

    public Complex[] reverse(Complex[] arrFreq) {
        int n = arrFreq.length;
        Complex[] arrTime = new Complex[n];
        
        if (n <= SEQUENTIAL_THRESHOLD) {
            return sequentialReverseComplex(arrFreq);
        }
        
        try {
            pool.invoke(new ParallelComplexDFTTask(arrFreq, arrTime, 0, n, false));
        } finally {
            // Don't shut down the pool, it can be reused
        }
        
        return arrTime;
    }

    private double[] sequentialForward(double[] arrTime, int n) {
        double[] arrFreq = new double[n];
        int k = n >> 1;
        
        for (int i = 0; i < n; i += 2) {
            for (int j = 0; j < n; j += 2) {
                int j2 = j >> 1;
                double arg = -2.0 * Math.PI * i * j / n;
                double cos = Math.cos(arg);
                double sin = Math.sin(arg);
                arrFreq[i] += arrTime[j] * cos - arrTime[j + 1] * sin;
                arrFreq[i + 1] += arrTime[j] * sin + arrTime[j + 1] * cos;
                
                if (j2 > 0 && j2 < k) {
                    arg = -2.0 * Math.PI * i * (n - j) / n;
                    cos = Math.cos(arg);
                    sin = Math.sin(arg);
                    arrFreq[i] += arrTime[n - j] * cos - arrTime[n - j + 1] * sin;
                    arrFreq[i + 1] += arrTime[n - j] * sin + arrTime[n - j + 1] * cos;
                }
            }
        }
        
        return arrFreq;
    }

    private double[] sequentialReverse(double[] arrFreq, int n) {
        double[] arrTime = new double[n];
        int k = n >> 1;
        
        for (int i = 0; i < n; i += 2) {
            for (int j = 0; j < n; j += 2) {
                int j2 = j >> 1;
                double arg = 2.0 * Math.PI * i * j / n;
                double cos = Math.cos(arg);
                double sin = Math.sin(arg);
                arrTime[i] += arrFreq[j] * cos - arrFreq[j + 1] * sin;
                arrTime[i + 1] += arrFreq[j] * sin + arrFreq[j + 1] * cos;
                
                if (j2 > 0 && j2 < k) {
                    arg = 2.0 * Math.PI * i * (n - j) / n;
                    cos = Math.cos(arg);
                    sin = Math.sin(arg);
                    arrTime[i] += arrFreq[n - j] * cos - arrFreq[n - j + 1] * sin;
                    arrTime[i + 1] += arrFreq[n - j] * sin + arrFreq[n - j + 1] * cos;
                }
            }
        }
        
        for (int i = 0; i < n; i++) {
            arrTime[i] /= k;
        }
        
        return arrTime;
    }

    private Complex[] sequentialForwardComplex(Complex[] arrTime) {
        int n = arrTime.length;
        Complex[] arrFreq = new Complex[n];
        
        for (int k = 0; k < n; k++) {
            arrFreq[k] = new Complex();
            for (int t = 0; t < n; t++) {
                double arg = -2.0 * Math.PI * t * k / n;
                Complex w = new Complex(Math.cos(arg), Math.sin(arg));
                arrFreq[k] = arrFreq[k].add(arrTime[t].mul(w));
            }
        }
        
        return arrFreq;
    }

    private Complex[] sequentialReverseComplex(Complex[] arrFreq) {
        int n = arrFreq.length;
        Complex[] arrTime = new Complex[n];
        
        for (int k = 0; k < n; k++) {
            arrTime[k] = new Complex();
            for (int t = 0; t < n; t++) {
                double arg = 2.0 * Math.PI * t * k / n;
                Complex w = new Complex(Math.cos(arg), Math.sin(arg));
                arrTime[k] = arrTime[k].add(arrFreq[t].mul(w));
            }
            arrTime[k] = arrTime[k].mul(1.0 / n);
        }
        
        return arrTime;
    }

    private class ParallelDFTTask extends RecursiveAction {
        private final double[] input;
        private final double[] output;
        private final int start;
        private final int end;
        private final boolean isForward;

        ParallelDFTTask(double[] input, double[] output, int start, int end, boolean isForward) {
            this.input = input;
            this.output = output;
            this.start = start;
            this.end = end;
            this.isForward = isForward;
        }

        @Override
        protected void compute() {
            int length = end - start;
            
            if (length <= SEQUENTIAL_THRESHOLD) {
                computeDirectly();
                return;
            }
            
            int mid = start + length / 2;
            
            ParallelDFTTask leftTask = new ParallelDFTTask(input, output, start, mid, isForward);
            ParallelDFTTask rightTask = new ParallelDFTTask(input, output, mid, end, isForward);
            
            leftTask.fork();
            rightTask.compute();
            leftTask.join();
        }

        private void computeDirectly() {
            int n = input.length;
            int k = n >> 1;
            double sign = isForward ? -2.0 : 2.0;
            
            for (int i = start; i < end; i += 2) {
                for (int j = 0; j < n; j += 2) {
                    int j2 = j >> 1;
                    double arg = sign * Math.PI * i * j / n;
                    double cos = Math.cos(arg);
                    double sin = Math.sin(arg);
                    output[i] += input[j] * cos - input[j + 1] * sin;
                    output[i + 1] += input[j] * sin + input[j + 1] * cos;
                    
                    if (j2 > 0 && j2 < k) {
                        arg = sign * Math.PI * i * (n - j) / n;
                        cos = Math.cos(arg);
                        sin = Math.sin(arg);
                        output[i] += input[n - j] * cos - input[n - j + 1] * sin;
                        output[i + 1] += input[n - j] * sin + input[n - j + 1] * cos;
                    }
                }
                
                if (!isForward) {
                    output[i] /= k;
                    output[i + 1] /= k;
                }
            }
        }
    }

    private class ParallelComplexDFTTask extends RecursiveAction {
        private final Complex[] input;
        private final Complex[] output;
        private final int start;
        private final int end;
        private final boolean isForward;

        ParallelComplexDFTTask(Complex[] input, Complex[] output, int start, int end, boolean isForward) {
            this.input = input;
            this.output = output;
            this.start = start;
            this.end = end;
            this.isForward = isForward;
        }

        @Override
        protected void compute() {
            int length = end - start;
            
            if (length <= SEQUENTIAL_THRESHOLD / 2) {
                computeDirectly();
                return;
            }
            
            int mid = start + length / 2;
            
            ParallelComplexDFTTask leftTask = new ParallelComplexDFTTask(input, output, start, mid, isForward);
            ParallelComplexDFTTask rightTask = new ParallelComplexDFTTask(input, output, mid, end, isForward);
            
            leftTask.fork();
            rightTask.compute();
            leftTask.join();
        }

        private void computeDirectly() {
            int n = input.length;
            double sign = isForward ? -2.0 : 2.0;
            
            for (int k = start; k < end; k++) {
                output[k] = new Complex();
                for (int t = 0; t < n; t++) {
                    double arg = sign * Math.PI * t * k / n;
                    Complex w = new Complex(Math.cos(arg), Math.sin(arg));
                    output[k] = output[k].add(input[t].mul(w));
                }
                
                if (!isForward) {
                    output[k] = output[k].mul(1.0 / n);
                }
            }
        }
    }

    public void shutdown() {
        pool.shutdown();
    }
}