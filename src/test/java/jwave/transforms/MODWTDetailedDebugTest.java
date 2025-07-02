package jwave.transforms;

import jwave.transforms.wavelets.haar.Haar1;
import org.junit.Test;

public class MODWTDetailedDebugTest {
    
    @Test
    public void debugMODWTFilters() {
        MODWTTransform modwt = new MODWTTransform(new Haar1());
        Haar1 haar = new Haar1();
        
        System.out.println("=== Haar Wavelet Filters ===");
        System.out.println("Scaling decomposition (g):");
        printArray(haar.getScalingDeComposition());
        System.out.println("Wavelet decomposition (h):");
        printArray(haar.getWaveletDeComposition());
        System.out.println("Scaling reconstruction (g~):");
        printArray(haar.getScalingReConstruction());
        System.out.println("Wavelet reconstruction (h~):");
        printArray(haar.getWaveletReConstruction());
        
        // Test simple reconstruction formula
        System.out.println("\n=== Testing reconstruction formula ===");
        double[] signal = {1.0, 2.0, 3.0, 4.0};
        double[][] coeffs = modwt.forwardMODWT(signal, 1);
        
        System.out.println("Original signal: ");
        printArray(signal);
        System.out.println("Detail coefficients D1:");
        printArray(coeffs[0]);
        System.out.println("Approximation coefficients A1:");
        printArray(coeffs[1]);
        
        // Manual reconstruction for verification
        System.out.println("\n=== Manual reconstruction test ===");
        // For Haar with MODWT filters g=[0.5, 0.5] and h=[0.5, -0.5]
        // The reconstruction should be: x[n] = sum of convolutions
        
        double[] reconstructed = modwt.inverseMODWT(coeffs);
        System.out.println("Reconstructed signal:");
        printArray(reconstructed);
    }
    
    @Test 
    public void testMODWTConvolution() {
        System.out.println("\n=== Testing MODWT Convolution ===");
        
        // Simple test signal
        double[] signal = {1.0, 0.0, 0.0, 0.0};
        double[] filter = {0.5, 0.5};
        
        System.out.println("Signal:");
        printArray(signal);
        System.out.println("Filter:");
        printArray(filter);
        
        // Forward convolution
        double[] result = circularConvolve(signal, filter);
        System.out.println("Forward convolution result:");
        printArray(result);
        
        // Reverse convolution
        double[] reverseResult = circularConvolveReverse(signal, filter);
        System.out.println("Reverse convolution result:");
        printArray(reverseResult);
    }
    
    private static double[] circularConvolve(double[] signal, double[] filter) {
        int N = signal.length;
        int M = filter.length;
        double[] output = new double[N];
        for (int n = 0; n < N; n++) {
            double sum = 0.0;
            for (int m = 0; m < M; m++) {
                int signalIndex = Math.floorMod(n - m, N);
                sum += signal[signalIndex] * filter[m];
            }
            output[n] = sum;
        }
        return output;
    }
    
    private static double[] circularConvolveReverse(double[] signal, double[] filter) {
        int N = signal.length;
        int M = filter.length;
        double[] output = new double[N];
        for (int n = 0; n < N; n++) {
            double sum = 0.0;
            for (int m = 0; m < M; m++) {
                int signalIndex = Math.floorMod(n - m, N);
                sum += signal[signalIndex] * filter[m];
            }
            output[n] = sum;
        }
        return output;
    }
    
    private void printArray(double[] arr) {
        System.out.print("[");
        for (int i = 0; i < arr.length; i++) {
            System.out.printf("%.4f", arr[i]);
            if (i < arr.length - 1) System.out.print(", ");
        }
        System.out.println("]");
    }
}