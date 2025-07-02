package jwave;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class PerformanceTestUtils {
    
    public static class TimingResult {
        public final long nanoTime;
        public final double milliseconds;
        public final double seconds;
        public final String description;
        
        public TimingResult(long nanoTime, String description) {
            this.nanoTime = nanoTime;
            this.milliseconds = nanoTime / 1_000_000.0;
            this.seconds = nanoTime / 1_000_000_000.0;
            this.description = description;
        }
        
        @Override
        public String toString() {
            return String.format("%s: %.3f ms (%.6f s)", description, milliseconds, seconds);
        }
    }
    
    public static class PerformanceStats {
        public final double meanMs;
        public final double minMs;
        public final double maxMs;
        public final double stdDevMs;
        public final int iterations;
        public final String description;
        
        public PerformanceStats(List<Long> times, String description) {
            this.iterations = times.size();
            this.description = description;
            
            double sum = 0;
            long min = Long.MAX_VALUE;
            long max = Long.MIN_VALUE;
            
            for (long time : times) {
                sum += time;
                min = Math.min(min, time);
                max = Math.max(max, time);
            }
            
            double mean = sum / iterations;
            
            double variance = 0;
            for (long time : times) {
                variance += Math.pow(time - mean, 2);
            }
            variance /= iterations;
            
            this.meanMs = mean / 1_000_000.0;
            this.minMs = min / 1_000_000.0;
            this.maxMs = max / 1_000_000.0;
            this.stdDevMs = Math.sqrt(variance) / 1_000_000.0;
        }
        
        @Override
        public String toString() {
            return String.format("%s - Mean: %.3f ms, Min: %.3f ms, Max: %.3f ms, StdDev: %.3f ms (n=%d)",
                    description, meanMs, minMs, maxMs, stdDevMs, iterations);
        }
    }
    
    public static TimingResult measureTime(Runnable task, String description) {
        long startTime = System.nanoTime();
        task.run();
        long endTime = System.nanoTime();
        return new TimingResult(endTime - startTime, description);
    }
    
    public static <T> TimingResult measureTimeWithResult(Supplier<T> task, String description) {
        long startTime = System.nanoTime();
        T result = task.get();
        long endTime = System.nanoTime();
        return new TimingResult(endTime - startTime, description);
    }
    
    public static PerformanceStats measureRepeated(Runnable task, int iterations, int warmupIterations, String description) {
        // Warmup phase
        for (int i = 0; i < warmupIterations; i++) {
            task.run();
        }
        
        // Measurement phase
        List<Long> times = new ArrayList<>(iterations);
        for (int i = 0; i < iterations; i++) {
            long startTime = System.nanoTime();
            task.run();
            long endTime = System.nanoTime();
            times.add(endTime - startTime);
        }
        
        return new PerformanceStats(times, description);
    }
    
    public static double[] generateRandomData(int size) {
        double[] data = new double[size];
        for (int i = 0; i < size; i++) {
            data[i] = Math.random();
        }
        return data;
    }
    
    public static double[][] generateRandom2DData(int rows, int cols) {
        double[][] data = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                data[i][j] = Math.random();
            }
        }
        return data;
    }
    
    public static double[][][] generateRandom3DData(int x, int y, int z) {
        double[][][] data = new double[x][y][z];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                for (int k = 0; k < z; k++) {
                    data[i][j][k] = Math.random();
                }
            }
        }
        return data;
    }
    
    public static void printMemoryUsage(String phase) {
        Runtime runtime = Runtime.getRuntime();
        long usedMemory = runtime.totalMemory() - runtime.freeMemory();
        System.out.printf("%s - Memory used: %.2f MB%n", phase, usedMemory / (1024.0 * 1024.0));
    }
    
    public static void forceGC() {
        System.gc();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}