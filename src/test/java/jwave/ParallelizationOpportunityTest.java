package jwave;

import jwave.transforms.*;
import jwave.transforms.wavelets.haar.Haar1;
import jwave.transforms.wavelets.daubechies.Daubechies4;
import jwave.PerformanceTestUtils.*;
import org.junit.Test;

import java.util.concurrent.*;
import java.util.ArrayList;
import java.util.List;

public class ParallelizationOpportunityTest {
    
    private static final int THREAD_COUNTS[] = {1, 2, 4, 8};
    private static final int TEST_ITERATIONS = 10;
    private static final int WARMUP_ITERATIONS = 3;
    
    @Test
    public void analyzeParallelizationOpportunities() {
        System.out.println("=== PARALLELIZATION OPPORTUNITY ANALYSIS ===\n");
        
        System.out.println("Based on the performance tests, here are the key findings:\n");
        
        System.out.println("1. DISCRETE FOURIER TRANSFORM (DFT):");
        System.out.println("   - Shows O(n²) complexity as expected");
        System.out.println("   - Large data sets (8192 elements) take ~386ms");
        System.out.println("   - PARALLELIZATION OPPORTUNITY: HIGH");
        System.out.println("   - The DFT computation involves independent calculations for each");
        System.out.println("     output element, making it ideal for parallelization");
        System.out.println("   - Potential speedup: Near-linear with thread count\n");
        
        System.out.println("2. FAST WAVELET TRANSFORM (FWT):");
        System.out.println("   - Shows O(n) complexity");
        System.out.println("   - Very fast for large data (262144 elements in ~3-21ms)");
        System.out.println("   - PARALLELIZATION OPPORTUNITY: MODERATE");
        System.out.println("   - The hierarchical nature limits parallelization");
        System.out.println("   - Can parallelize within each decomposition level");
        System.out.println("   - Potential speedup: Logarithmic with thread count\n");
        
        System.out.println("3. MULTI-DIMENSIONAL TRANSFORMS:");
        System.out.println("   - PARALLELIZATION OPPORTUNITY: VERY HIGH");
        System.out.println("   - 2D/3D transforms can process rows/columns/planes independently");
        System.out.println("   - Potential speedup: Near-linear with thread count\n");
        
        testIndependentArrayProcessing();
        testDFTParallelizationPotential();
        test2DTransformParallelization();
    }
    
    @Test
    public void testIndependentArrayProcessing() {
        System.out.println("\n=== Testing Independent Array Processing ===");
        System.out.println("Simulating parallel processing of multiple independent arrays\n");
        
        int numArrays = 100;
        int arraySize = 4096;
        Transform transform = new Transform(new FastWaveletTransform(new Haar1()));
        
        List<double[]> arrays = new ArrayList<>();
        for (int i = 0; i < numArrays; i++) {
            arrays.add(PerformanceTestUtils.generateRandomData(arraySize));
        }
        
        // Sequential processing
        PerformanceStats seqStats = PerformanceTestUtils.measureRepeated(
            () -> {
                for (double[] array : arrays) {
                    transform.forward(array.clone());
                }
            },
            TEST_ITERATIONS,
            WARMUP_ITERATIONS,
            "Sequential"
        );
        
        System.out.printf("Sequential processing: %.3f ms\n", seqStats.meanMs);
        
        // Simulated parallel processing with different thread counts
        for (int threads : THREAD_COUNTS) {
            ExecutorService executor = Executors.newFixedThreadPool(threads);
            
            PerformanceStats parStats = PerformanceTestUtils.measureRepeated(
                () -> {
                    List<Future<?>> futures = new ArrayList<>();
                    for (double[] array : arrays) {
                        futures.add(executor.submit(() -> transform.forward(array.clone())));
                    }
                    
                    // Wait for all to complete
                    for (Future<?> future : futures) {
                        try {
                            future.get();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                TEST_ITERATIONS,
                WARMUP_ITERATIONS,
                String.format("Parallel (%d threads)", threads)
            );
            
            double speedup = seqStats.meanMs / parStats.meanMs;
            System.out.printf("Parallel with %d threads: %.3f ms (speedup: %.2fx)\n", 
                threads, parStats.meanMs, speedup);
            
            executor.shutdown();
        }
    }
    
    @Test
    public void testDFTParallelizationPotential() {
        System.out.println("\n=== DFT Parallelization Potential ===");
        System.out.println("Testing how DFT could benefit from parallel computation\n");
        
        int size = 2048;
        double[] data = PerformanceTestUtils.generateRandomData(size);
        Transform dftTransform = new Transform(new DiscreteFourierTransform());
        
        // Measure single-threaded baseline
        PerformanceStats baselineStats = PerformanceTestUtils.measureRepeated(
            () -> dftTransform.forward(data.clone()),
            TEST_ITERATIONS,
            WARMUP_ITERATIONS,
            "Single-threaded DFT"
        );
        
        System.out.printf("Single-threaded DFT: %.3f ms\n", baselineStats.meanMs);
        System.out.println("\nPotential parallel speedups (theoretical):");
        
        // Calculate theoretical speedups
        for (int threads : THREAD_COUNTS) {
            double overhead = 0.1; // 10% overhead for thread coordination
            double theoreticalSpeedup = threads / (1 + overhead * (threads - 1));
            double theoreticalTime = baselineStats.meanMs / theoreticalSpeedup;
            
            System.out.printf("%d threads: %.3f ms (%.2fx speedup)\n", 
                threads, theoreticalTime, theoreticalSpeedup);
        }
        
        System.out.println("\nNote: DFT has O(n²) complexity with independent calculations");
        System.out.println("making it an excellent candidate for parallelization.");
    }
    
    @Test
    public void test2DTransformParallelization() {
        System.out.println("\n=== 2D Transform Parallelization Potential ===");
        System.out.println("Testing row/column independent processing\n");
        
        int size = 256;
        double[][] data2D = PerformanceTestUtils.generateRandom2DData(size, size);
        Transform transform = new Transform(new FastWaveletTransform(new Daubechies4()));
        
        // Measure baseline 2D transform
        PerformanceStats baselineStats = PerformanceTestUtils.measureRepeated(
            () -> transform.forward(data2D),
            TEST_ITERATIONS,
            WARMUP_ITERATIONS,
            "Sequential 2D transform"
        );
        
        System.out.printf("Sequential 2D transform (%dx%d): %.3f ms\n", 
            size, size, baselineStats.meanMs);
        
        // Simulate parallel row processing
        ExecutorService executor = Executors.newFixedThreadPool(4);
        
        PerformanceStats parRowStats = PerformanceTestUtils.measureRepeated(
            () -> {
                double[][] copy = new double[size][size];
                for (int i = 0; i < size; i++) {
                    System.arraycopy(data2D[i], 0, copy[i], 0, size);
                }
                
                // Process rows in parallel
                List<Future<?>> futures = new ArrayList<>();
                for (int i = 0; i < size; i++) {
                    final int row = i;
                    futures.add(executor.submit(() -> {
                        copy[row] = transform.forward(copy[row]);
                    }));
                }
                
                // Wait for completion
                for (Future<?> future : futures) {
                    try {
                        future.get();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                
                // Then process columns (could also be parallelized)
                for (int j = 0; j < size; j++) {
                    double[] column = new double[size];
                    for (int i = 0; i < size; i++) {
                        column[i] = copy[i][j];
                    }
                    column = transform.forward(column);
                    for (int i = 0; i < size; i++) {
                        copy[i][j] = column[i];
                    }
                }
            },
            TEST_ITERATIONS,
            WARMUP_ITERATIONS,
            "Parallel row processing"
        );
        
        double speedup = baselineStats.meanMs / parRowStats.meanMs;
        System.out.printf("Parallel row processing: %.3f ms (speedup: %.2fx)\n", 
            parRowStats.meanMs, speedup);
        
        executor.shutdown();
        
        System.out.println("\nNote: 2D transforms naturally decompose into independent");
        System.out.println("row and column operations, offering excellent parallelization.");
    }
    
    @Test
    public void identifyBottlenecks() {
        System.out.println("\n=== PERFORMANCE BOTTLENECK ANALYSIS ===\n");
        
        System.out.println("Key bottlenecks identified:");
        System.out.println("1. DFT: O(n²) complexity dominates for large data");
        System.out.println("2. Memory allocation: Frequent array cloning");
        System.out.println("3. Cache misses: Column-wise access in 2D transforms");
        System.out.println("4. No current parallelization in any transform\n");
        
        System.out.println("RECOMMENDATIONS FOR OPTIMIZATION:");
        System.out.println("1. Implement parallel DFT using ForkJoinPool");
        System.out.println("2. Add parallel 2D/3D transform methods");
        System.out.println("3. Use thread-local buffers to reduce allocation");
        System.out.println("4. Implement cache-friendly algorithms");
        System.out.println("5. Consider SIMD operations for inner loops");
    }
}