package jwave.transforms;

import jwave.Transform;
import jwave.PerformanceTestUtils;
import jwave.PerformanceTestUtils.*;
import jwave.transforms.wavelets.Wavelet;
import jwave.transforms.wavelets.haar.Haar1;
import jwave.transforms.wavelets.daubechies.Daubechies4;
import org.junit.Test;
import org.junit.After;

public class ParallelPerformanceTest {
    
    private static final int WARMUP_ITERATIONS = 3;
    private static final int TEST_ITERATIONS = 10;
    
    private ParallelDiscreteFourierTransform parallelDFT;
    private ParallelTransform parallelTransform;
    
    @After
    public void cleanup() {
        if (parallelDFT != null) {
            parallelDFT.shutdown();
        }
        if (parallelTransform != null) {
            parallelTransform.shutdown();
        }
    }
    
    @Test
    public void testParallelDFTPerformance() {
        System.out.println("=== Parallel DFT Performance Test ===\n");
        
        int[] sizes = {512, 1024, 2048, 4096, 8192};
        
        System.out.println("Size\tSequential (ms)\tParallel (ms)\tSpeedup\tEfficiency");
        System.out.println("----\t--------------\t-------------\t-------\t----------");
        
        for (int size : sizes) {
            double[] data = PerformanceTestUtils.generateRandomData(size);
            
            // Test sequential DFT
            Transform sequentialTransform = new Transform(new DiscreteFourierTransform());
            PerformanceStats seqStats = PerformanceTestUtils.measureRepeated(
                () -> {
                    double[] result = sequentialTransform.forward(data.clone());
                    sequentialTransform.reverse(result);
                },
                TEST_ITERATIONS,
                WARMUP_ITERATIONS,
                "Sequential DFT"
            );
            
            // Test parallel DFT
            parallelDFT = new ParallelDiscreteFourierTransform();
            Transform parallelTransform = new Transform(parallelDFT);
            PerformanceStats parStats = PerformanceTestUtils.measureRepeated(
                () -> {
                    double[] result = parallelTransform.forward(data.clone());
                    parallelTransform.reverse(result);
                },
                TEST_ITERATIONS,
                WARMUP_ITERATIONS,
                "Parallel DFT"
            );
            
            double speedup = seqStats.meanMs / parStats.meanMs;
            double efficiency = speedup / Runtime.getRuntime().availableProcessors();
            
            System.out.printf("%d\t%.3f\t\t%.3f\t\t%.2fx\t%.2f%%\n",
                size, seqStats.meanMs, parStats.meanMs, speedup, efficiency * 100);
        }
    }
    
    @Test
    public void test2DTransformParallelPerformance() {
        System.out.println("\n=== 2D Transform Parallel Performance Test ===\n");
        
        int[] sizes = {64, 128, 256, 512, 1024};
        Wavelet wavelet = new Daubechies4();
        
        System.out.println("Size\tSequential (ms)\tParallel (ms)\tSpeedup\tEfficiency");
        System.out.println("----\t--------------\t-------------\t-------\t----------");
        
        for (int size : sizes) {
            double[][] data2D = PerformanceTestUtils.generateRandom2DData(size, size);
            
            // Test sequential 2D transform
            Transform sequentialTransform = new Transform(new FastWaveletTransform(wavelet));
            PerformanceStats seqStats = PerformanceTestUtils.measureRepeated(
                () -> {
                    double[][] result = sequentialTransform.forward(data2D);
                    sequentialTransform.reverse(result);
                },
                TEST_ITERATIONS,
                WARMUP_ITERATIONS,
                "Sequential 2D"
            );
            
            // Test parallel 2D transform
            parallelTransform = new ParallelTransform(new FastWaveletTransform(wavelet));
            Transform parallelTransformWrapper = new Transform(parallelTransform);
            PerformanceStats parStats = PerformanceTestUtils.measureRepeated(
                () -> {
                    double[][] result = parallelTransformWrapper.forward(data2D);
                    parallelTransformWrapper.reverse(result);
                },
                TEST_ITERATIONS,
                WARMUP_ITERATIONS,
                "Parallel 2D"
            );
            
            double speedup = seqStats.meanMs / parStats.meanMs;
            double efficiency = speedup / Runtime.getRuntime().availableProcessors();
            
            System.out.printf("%dx%d\t%.3f\t\t%.3f\t\t%.2fx\t%.2f%%\n",
                size, size, seqStats.meanMs, parStats.meanMs, speedup, efficiency * 100);
        }
    }
    
    @Test
    public void test3DTransformParallelPerformance() {
        System.out.println("\n=== 3D Transform Parallel Performance Test ===\n");
        
        int[] sizes = {16, 32, 64, 128};
        Wavelet wavelet = new Haar1();
        
        System.out.println("Size\t\tSequential (ms)\tParallel (ms)\tSpeedup\tEfficiency");
        System.out.println("--------\t--------------\t-------------\t-------\t----------");
        
        for (int size : sizes) {
            double[][][] data3D = PerformanceTestUtils.generateRandom3DData(size, size, size);
            
            // Test sequential 3D transform
            Transform sequentialTransform = new Transform(new FastWaveletTransform(wavelet));
            PerformanceStats seqStats = PerformanceTestUtils.measureRepeated(
                () -> {
                    double[][][] result = sequentialTransform.forward(data3D);
                    sequentialTransform.reverse(result);
                },
                TEST_ITERATIONS,
                WARMUP_ITERATIONS,
                "Sequential 3D"
            );
            
            // Test parallel 3D transform
            parallelTransform = new ParallelTransform(new FastWaveletTransform(wavelet));
            Transform parallelTransformWrapper = new Transform(parallelTransform);
            PerformanceStats parStats = PerformanceTestUtils.measureRepeated(
                () -> {
                    double[][][] result = parallelTransformWrapper.forward(data3D);
                    parallelTransformWrapper.reverse(result);
                },
                TEST_ITERATIONS,
                WARMUP_ITERATIONS,
                "Parallel 3D"
            );
            
            double speedup = seqStats.meanMs / parStats.meanMs;
            double efficiency = speedup / Runtime.getRuntime().availableProcessors();
            
            System.out.printf("%dx%dx%d\t%.3f\t\t%.3f\t\t%.2fx\t%.2f%%\n",
                size, size, size, seqStats.meanMs, parStats.meanMs, speedup, efficiency * 100);
        }
    }
    
    @Test
    public void testScalabilityWithThreadCount() {
        System.out.println("\n=== Thread Scalability Test ===\n");
        
        int size = 512;
        double[][] data2D = PerformanceTestUtils.generateRandom2DData(size, size);
        Wavelet wavelet = new Daubechies4();
        
        // Baseline sequential performance
        Transform sequentialTransform = new Transform(new FastWaveletTransform(wavelet));
        PerformanceStats baselineStats = PerformanceTestUtils.measureRepeated(
            () -> {
                double[][] result = sequentialTransform.forward(data2D);
                sequentialTransform.reverse(result);
            },
            TEST_ITERATIONS,
            WARMUP_ITERATIONS,
            "Sequential"
        );
        
        System.out.printf("Sequential baseline: %.3f ms\n\n", baselineStats.meanMs);
        
        System.out.println("Threads\tTime (ms)\tSpeedup\tEfficiency");
        System.out.println("-------\t---------\t-------\t----------");
        
        int[] threadCounts = {1, 2, 4, 8};
        for (int threads : threadCounts) {
            ParallelTransform pt = new ParallelTransform(new FastWaveletTransform(wavelet), threads);
            Transform parallelTransform = new Transform(pt);
            
            PerformanceStats stats = PerformanceTestUtils.measureRepeated(
                () -> {
                    double[][] result = parallelTransform.forward(data2D);
                    parallelTransform.reverse(result);
                },
                TEST_ITERATIONS,
                WARMUP_ITERATIONS,
                threads + " threads"
            );
            
            double speedup = baselineStats.meanMs / stats.meanMs;
            double efficiency = speedup / threads;
            
            System.out.printf("%d\t%.3f\t\t%.2fx\t%.2f%%\n",
                threads, stats.meanMs, speedup, efficiency * 100);
            
            pt.shutdown();
        }
    }
    
    @Test
    public void testParallelizationOverhead() {
        System.out.println("\n=== Parallelization Overhead Test ===\n");
        System.out.println("Testing overhead for small data sizes where parallelization may hurt performance\n");
        
        int[] sizes = {4, 8, 16, 32, 64, 128, 256};
        Wavelet wavelet = new Haar1();
        
        System.out.println("Size\tSequential (μs)\tParallel (μs)\tOverhead");
        System.out.println("----\t--------------\t-------------\t--------");
        
        for (int size : sizes) {
            double[][] data2D = PerformanceTestUtils.generateRandom2DData(size, size);
            
            // Sequential
            Transform sequentialTransform = new Transform(new FastWaveletTransform(wavelet));
            PerformanceStats seqStats = PerformanceTestUtils.measureRepeated(
                () -> sequentialTransform.forward(data2D),
                TEST_ITERATIONS * 10,
                WARMUP_ITERATIONS,
                "Sequential"
            );
            
            // Parallel
            parallelTransform = new ParallelTransform(new FastWaveletTransform(wavelet));
            Transform parallelTransformWrapper = new Transform(parallelTransform);
            PerformanceStats parStats = PerformanceTestUtils.measureRepeated(
                () -> parallelTransformWrapper.forward(data2D),
                TEST_ITERATIONS * 10,
                WARMUP_ITERATIONS,
                "Parallel"
            );
            
            double seqMicros = seqStats.meanMs * 1000;
            double parMicros = parStats.meanMs * 1000;
            String overhead = parMicros > seqMicros ? 
                String.format("+%.1f%%", ((parMicros / seqMicros) - 1) * 100) : 
                String.format("-%.1f%%", (1 - (parMicros / seqMicros)) * 100);
            
            System.out.printf("%dx%d\t%.1f\t\t%.1f\t\t%s\n",
                size, size, seqMicros, parMicros, overhead);
        }
    }
}