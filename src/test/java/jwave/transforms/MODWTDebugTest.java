package jwave.transforms;

import jwave.transforms.wavelets.haar.Haar1;
import org.junit.Test;

public class MODWTDebugTest {
    
    @Test
    public void debugSingleLevelMODWT() {
        MODWTTransform modwt = new MODWTTransform(new Haar1());
        double[] signal = {1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0};
        
        System.out.println("Original signal:");
        printArray(signal);
        
        // Forward transform
        double[][] coeffs = modwt.forwardMODWT(signal, 1);
        
        System.out.println("\nForward MODWT coefficients:");
        System.out.println("Detail D1:");
        printArray(coeffs[0]);
        System.out.println("Approximation A1:");
        printArray(coeffs[1]);
        
        // Verify expected values from test
        System.out.println("\nExpected Detail D1: [-3.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5]");
        System.out.println("Expected Approx A1: [4.5, 1.5, 2.5, 3.5, 4.5, 5.5, 6.5, 7.5]");
        
        // Try inverse
        double[] reconstructed = modwt.inverseMODWT(coeffs);
        System.out.println("\nReconstructed signal:");
        printArray(reconstructed);
        
        // Check reconstruction error
        System.out.println("\nReconstruction errors:");
        for (int i = 0; i < signal.length; i++) {
            System.out.printf("Position %d: expected %.1f, got %.4f, error: %.4f\n", 
                i, signal[i], reconstructed[i], signal[i] - reconstructed[i]);
        }
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