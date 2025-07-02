package jwave.transforms;

import jwave.Transform;
import jwave.PerformanceTestUtils;
import jwave.PerformanceTestUtils.*;
import jwave.datatypes.natives.Complex;
import org.junit.Test;

public class DiscreteFourierTransformPerformanceTest {
    
    private static final int[] TEST_SIZES = {64, 128, 256, 512, 1024, 2048, 4096, 8192};
    private static final int WARMUP_ITERATIONS = 3;
    private static final int TEST_ITERATIONS = 10;
    
    @Test
    public void testDFTPerformanceScaling() {
        System.out.println("=== Discrete Fourier Transform Performance Test ===");
        System.out.println("Testing DFT performance scaling with data size\n");
        
        Transform dftTransform = new Transform(new DiscreteFourierTransform());
        
        System.out.println("Size\tForward (ms)\tReverse (ms)\tTotal (ms)\tComplexity Factor");
        System.out.println("----\t------------\t------------\t----------\t-----------------");
        
        double previousTime = 0;
        int previousSize = 0;
        
        for (int size : TEST_SIZES) {
            double[] data = PerformanceTestUtils.generateRandomData(size);
            
            // Measure forward transform
            PerformanceStats forwardStats = PerformanceTestUtils.measureRepeated(
                () -> dftTransform.forward(data.clone()),
                TEST_ITERATIONS,
                WARMUP_ITERATIONS,
                "DFT Forward"
            );
            
            // Measure reverse transform
            double[] transformed = dftTransform.forward(data.clone());
            PerformanceStats reverseStats = PerformanceTestUtils.measureRepeated(
                () -> dftTransform.reverse(transformed.clone()),
                TEST_ITERATIONS,
                WARMUP_ITERATIONS,
                "DFT Reverse"
            );
            
            double totalMs = forwardStats.meanMs + reverseStats.meanMs;
            
            // Calculate complexity factor (should be ~4 for O(n²) algorithm)
            String complexityFactor = "";
            if (previousTime > 0) {
                double sizeRatio = (double) size / previousSize;
                double timeRatio = totalMs / previousTime;
                double factor = timeRatio / (sizeRatio * sizeRatio); // For O(n²)
                complexityFactor = String.format("%.2f", factor);
            }
            
            System.out.printf("%d\t%.3f\t\t%.3f\t\t%.3f\t\t%s\n",
                size, forwardStats.meanMs, reverseStats.meanMs, totalMs, complexityFactor);
            
            previousTime = totalMs;
            previousSize = size;
        }
        
        System.out.println("\nNote: Complexity factor should be close to 1.0 for O(n²) scaling");
    }
    
    @Test
    public void testDFTvsFFTComparison() {
        System.out.println("\n=== DFT vs FWT Performance Comparison ===");
        System.out.println("Comparing DFT with Fast Wavelet Transform\n");
        
        Transform dftTransform = new Transform(new DiscreteFourierTransform());
        Transform fwtTransform = new Transform(
            new FastWaveletTransform(new jwave.transforms.wavelets.haar.Haar1())
        );
        
        int[] comparisonSizes = {64, 256, 1024, 4096};
        
        System.out.println("Size\tDFT (ms)\tFWT (ms)\tSpeedup Factor");
        System.out.println("----\t--------\t--------\t--------------");
        
        for (int size : comparisonSizes) {
            double[] data = PerformanceTestUtils.generateRandomData(size);
            
            // Measure DFT
            PerformanceStats dftStats = PerformanceTestUtils.measureRepeated(
                () -> {
                    double[] result = dftTransform.forward(data.clone());
                    dftTransform.reverse(result);
                },
                TEST_ITERATIONS,
                WARMUP_ITERATIONS,
                "DFT"
            );
            
            // Measure FWT
            PerformanceStats fwtStats = PerformanceTestUtils.measureRepeated(
                () -> {
                    double[] result = fwtTransform.forward(data.clone());
                    fwtTransform.reverse(result);
                },
                TEST_ITERATIONS * 5, // More iterations for FWT since it's faster
                WARMUP_ITERATIONS,
                "FWT"
            );
            
            double speedup = dftStats.meanMs / fwtStats.meanMs;
            
            System.out.printf("%d\t%.3f\t\t%.3f\t\t%.1fx\n",
                size, dftStats.meanMs, fwtStats.meanMs, speedup);
        }
    }
    
    @Test
    public void testComplexDFTPerformance() {
        System.out.println("\n=== Complex DFT Performance Test ===");
        System.out.println("Testing DFT with complex number inputs\n");
        
        DiscreteFourierTransform dft = new DiscreteFourierTransform();
        
        int[] complexSizes = {64, 256, 1024};
        
        for (int size : complexSizes) {
            Complex[] complexData = new Complex[size];
            for (int i = 0; i < size; i++) {
                complexData[i] = new Complex(Math.random(), Math.random());
            }
            
            PerformanceStats complexStats = PerformanceTestUtils.measureRepeated(
                () -> {
                    Complex[] result = dft.forward(complexData.clone());
                    dft.reverse(result);
                },
                TEST_ITERATIONS,
                WARMUP_ITERATIONS,
                String.format("Complex DFT size %d", size)
            );
            
            System.out.println(complexStats);
        }
    }
    
    @Test
    public void testDFTMemoryPattern() {
        System.out.println("\n=== DFT Memory Access Pattern Test ===");
        System.out.println("Testing performance with different data patterns\n");
        
        Transform dftTransform = new Transform(new DiscreteFourierTransform());
        int size = 1024;
        
        // Test 1: Sequential data
        double[] sequentialData = new double[size];
        for (int i = 0; i < size; i++) {
            sequentialData[i] = i;
        }
        
        PerformanceStats seqStats = PerformanceTestUtils.measureRepeated(
            () -> dftTransform.forward(sequentialData.clone()),
            TEST_ITERATIONS,
            WARMUP_ITERATIONS,
            "Sequential data"
        );
        
        // Test 2: Random data
        double[] randomData = PerformanceTestUtils.generateRandomData(size);
        
        PerformanceStats randStats = PerformanceTestUtils.measureRepeated(
            () -> dftTransform.forward(randomData.clone()),
            TEST_ITERATIONS,
            WARMUP_ITERATIONS,
            "Random data"
        );
        
        // Test 3: Sparse data
        double[] sparseData = new double[size];
        for (int i = 0; i < size / 10; i++) {
            sparseData[(int)(Math.random() * size)] = Math.random();
        }
        
        PerformanceStats sparseStats = PerformanceTestUtils.measureRepeated(
            () -> dftTransform.forward(sparseData.clone()),
            TEST_ITERATIONS,
            WARMUP_ITERATIONS,
            "Sparse data (10% non-zero)"
        );
        
        System.out.println(seqStats);
        System.out.println(randStats);
        System.out.println(sparseStats);
        
        System.out.printf("\nPerformance ratio (random/sequential): %.2f\n", 
            randStats.meanMs / seqStats.meanMs);
        System.out.printf("Performance ratio (sparse/sequential): %.2f\n", 
            sparseStats.meanMs / seqStats.meanMs);
    }
}