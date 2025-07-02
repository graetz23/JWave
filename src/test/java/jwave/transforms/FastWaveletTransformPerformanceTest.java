package jwave.transforms;

import jwave.Transform;
import jwave.PerformanceTestUtils;
import jwave.PerformanceTestUtils.*;
import jwave.transforms.wavelets.Wavelet;
import jwave.transforms.wavelets.WaveletBuilder;
import jwave.transforms.wavelets.haar.Haar1;
import jwave.transforms.wavelets.daubechies.*;
import jwave.transforms.wavelets.symlets.*;
import jwave.transforms.wavelets.coiflet.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class FastWaveletTransformPerformanceTest {
    
    private static final int[] TEST_SIZES = {256, 1024, 4096, 16384, 65536, 262144};
    private static final int WARMUP_ITERATIONS = 5;
    private static final int TEST_ITERATIONS = 20;
    
    @Test
    public void testFWTPerformanceWithDifferentWavelets() {
        System.out.println("=== Fast Wavelet Transform Performance Test ===");
        System.out.println("Testing various wavelets with different data sizes\n");
        
        List<Wavelet> wavelets = new ArrayList<>();
        wavelets.add(new Haar1());
        wavelets.add(new Daubechies2());
        wavelets.add(new Daubechies4());
        wavelets.add(new Daubechies8());
        wavelets.add(new Daubechies20());
        wavelets.add(new Symlet2());
        wavelets.add(new Symlet8());
        wavelets.add(new Symlet20());
        wavelets.add(new Coiflet1());
        wavelets.add(new Coiflet5());
        
        for (int size : TEST_SIZES) {
            System.out.printf("\n--- Data Size: %d elements ---\n", size);
            double[] data = PerformanceTestUtils.generateRandomData(size);
            
            for (Wavelet wavelet : wavelets) {
                testWaveletPerformance(wavelet, data.clone());
            }
        }
    }
    
    @Test
    public void testFWTScalability() {
        System.out.println("\n=== FWT Scalability Test ===");
        System.out.println("Testing how performance scales with data size\n");
        
        Wavelet[] testWavelets = {
            new Haar1(),
            new Daubechies4(),
            new Symlet8()
        };
        
        for (Wavelet wavelet : testWavelets) {
            System.out.printf("\nWavelet: %s\n", wavelet.getName());
            System.out.println("Size\tForward (ms)\tReverse (ms)\tTotal (ms)\tThroughput (MB/s)");
            
            for (int size : TEST_SIZES) {
                double[] data = PerformanceTestUtils.generateRandomData(size);
                Transform transform = new Transform(new FastWaveletTransform(wavelet));
                
                // Measure forward transform
                PerformanceStats forwardStats = PerformanceTestUtils.measureRepeated(
                    () -> transform.forward(data.clone()),
                    TEST_ITERATIONS,
                    WARMUP_ITERATIONS,
                    "Forward"
                );
                
                // Measure reverse transform
                double[] transformed = transform.forward(data.clone());
                PerformanceStats reverseStats = PerformanceTestUtils.measureRepeated(
                    () -> transform.reverse(transformed.clone()),
                    TEST_ITERATIONS,
                    WARMUP_ITERATIONS,
                    "Reverse"
                );
                
                double totalMs = forwardStats.meanMs + reverseStats.meanMs;
                double mbPerSec = (size * 8.0 / (1024 * 1024)) / (totalMs / 1000.0);
                
                System.out.printf("%d\t%.3f\t\t%.3f\t\t%.3f\t\t%.2f\n",
                    size, forwardStats.meanMs, reverseStats.meanMs, totalMs, mbPerSec);
            }
        }
    }
    
    @Test
    public void testAncientEgyptianDecompositionOverhead() {
        System.out.println("\n=== Ancient Egyptian Decomposition Overhead Test ===");
        System.out.println("Comparing performance with and without AED wrapper\n");
        
        int[] arbitrarySizes = {1000, 5000, 10000, 50000, 100000};
        Wavelet wavelet = new Daubechies4();
        
        for (int size : arbitrarySizes) {
            System.out.printf("\nData size: %d (non-power of 2)\n", size);
            double[] data = PerformanceTestUtils.generateRandomData(size);
            
            // Test with AED
            Transform aedTransform = new Transform(
                new AncientEgyptianDecomposition(
                    new FastWaveletTransform(wavelet)
                )
            );
            
            PerformanceStats aedStats = PerformanceTestUtils.measureRepeated(
                () -> {
                    double[] result = aedTransform.forward(data.clone());
                    aedTransform.reverse(result);
                },
                TEST_ITERATIONS,
                WARMUP_ITERATIONS,
                "With AED"
            );
            
            System.out.printf("AED Transform: %.3f ms\n", aedStats.meanMs);
            
            // For comparison, test next power of 2 size without AED
            int nextPowerOf2 = 1;
            while (nextPowerOf2 < size) nextPowerOf2 *= 2;
            
            double[] paddedData = new double[nextPowerOf2];
            System.arraycopy(data, 0, paddedData, 0, size);
            
            Transform directTransform = new Transform(new FastWaveletTransform(wavelet));
            
            PerformanceStats directStats = PerformanceTestUtils.measureRepeated(
                () -> {
                    double[] result = directTransform.forward(paddedData.clone());
                    directTransform.reverse(result);
                },
                TEST_ITERATIONS,
                WARMUP_ITERATIONS,
                "Direct (padded)"
            );
            
            System.out.printf("Direct Transform (size %d): %.3f ms\n", nextPowerOf2, directStats.meanMs);
            System.out.printf("Overhead ratio: %.2fx\n", aedStats.meanMs / directStats.meanMs);
        }
    }
    
    @Test
    public void testMemoryUsage() {
        System.out.println("\n=== Memory Usage Test ===");
        System.out.println("Monitoring memory consumption during transforms\n");
        
        int size = 1048576; // 1M elements
        double[] data = PerformanceTestUtils.generateRandomData(size);
        Wavelet wavelet = new Daubechies4();
        Transform transform = new Transform(new FastWaveletTransform(wavelet));
        
        PerformanceTestUtils.forceGC();
        PerformanceTestUtils.printMemoryUsage("Before transform");
        
        double[] result = transform.forward(data.clone());
        PerformanceTestUtils.printMemoryUsage("After forward transform");
        
        double[] reversed = transform.reverse(result);
        PerformanceTestUtils.printMemoryUsage("After reverse transform");
        
        // Force references out of scope and GC
        result = null;
        reversed = null;
        PerformanceTestUtils.forceGC();
        PerformanceTestUtils.printMemoryUsage("After cleanup");
    }
    
    private void testWaveletPerformance(Wavelet wavelet, double[] data) {
        Transform transform = new Transform(new FastWaveletTransform(wavelet));
        
        PerformanceStats stats = PerformanceTestUtils.measureRepeated(
            () -> {
                double[] result = transform.forward(data.clone());
                transform.reverse(result);
            },
            TEST_ITERATIONS,
            WARMUP_ITERATIONS,
            wavelet.getName()
        );
        
        System.out.printf("%-20s: %.3f ms (±%.3f ms)\n", 
            wavelet.getName(), stats.meanMs, stats.stdDevMs);
    }
}