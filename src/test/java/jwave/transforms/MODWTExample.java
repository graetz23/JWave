package jwave.transforms;

import jwave.transforms.wavelets.daubechies.Daubechies4;
import jwave.transforms.wavelets.haar.Haar1;

/**
 * Example demonstrating the use of MODWT (Maximal Overlap Discrete Wavelet Transform)
 * and its inverse (iMODWT) for signal analysis and reconstruction.
 */
public class MODWTExample {
    
    public static void main(String[] args) {
        // Example 1: Basic MODWT and inverse
        basicExample();
        
        // Example 2: Denoising with MODWT
        denoisingExample();
        
        // Example 3: Multi-resolution analysis
        multiResolutionExample();
    }
    
    private static void basicExample() {
        System.out.println("=== Basic MODWT Example ===\n");
        
        // Create a test signal (combination of frequencies)
        int N = 128;
        double[] signal = new double[N];
        for (int i = 0; i < N; i++) {
            signal[i] = Math.sin(2 * Math.PI * i / 32.0) +     // Low frequency
                       0.5 * Math.sin(2 * Math.PI * i / 8.0);  // High frequency
        }
        
        // Create MODWT transform with Haar wavelet
        MODWTTransform modwt = new MODWTTransform(new Haar1());
        
        // Forward MODWT - 3 levels
        double[][] coeffs = modwt.forwardMODWT(signal, 3);
        
        System.out.println("Forward MODWT produced " + coeffs.length + " coefficient arrays:");
        for (int i = 0; i < coeffs.length - 1; i++) {
            System.out.println("  Detail level " + (i + 1) + ": " + coeffs[i].length + " coefficients");
        }
        System.out.println("  Approximation level " + (coeffs.length - 1) + ": " + 
                          coeffs[coeffs.length - 1].length + " coefficients");
        
        // Inverse MODWT
        double[] reconstructed = modwt.inverseMODWT(coeffs);
        
        // Check reconstruction error
        double error = 0.0;
        for (int i = 0; i < signal.length; i++) {
            error += Math.pow(signal[i] - reconstructed[i], 2);
        }
        error = Math.sqrt(error / signal.length);
        
        System.out.println("\nReconstruction RMS error: " + error);
        System.out.println("Perfect reconstruction: " + (error < 1e-10 ? "YES" : "NO"));
    }
    
    private static void denoisingExample() {
        System.out.println("\n\n=== Denoising Example ===\n");
        
        // Create a noisy signal
        int N = 256;
        double[] cleanSignal = new double[N];
        double[] noisySignal = new double[N];
        
        for (int i = 0; i < N; i++) {
            cleanSignal[i] = Math.sin(2 * Math.PI * i / 64.0) + 
                            0.3 * Math.cos(2 * Math.PI * i / 16.0);
            noisySignal[i] = cleanSignal[i] + 0.2 * (Math.random() - 0.5); // Add noise
        }
        
        // Create MODWT transform with Daubechies-4 wavelet
        MODWTTransform modwt = new MODWTTransform(new Daubechies4());
        
        // Forward transform
        double[][] coeffs = modwt.forwardMODWT(noisySignal, 4);
        
        // Apply soft thresholding to detail coefficients
        double threshold = 0.1;
        for (int level = 0; level < coeffs.length - 1; level++) {
            for (int i = 0; i < coeffs[level].length; i++) {
                double coeff = coeffs[level][i];
                if (Math.abs(coeff) <= threshold) {
                    coeffs[level][i] = 0.0;
                } else if (coeff > 0) {
                    coeffs[level][i] = coeff - threshold;
                } else {
                    coeffs[level][i] = coeff + threshold;
                }
            }
        }
        
        // Inverse transform to get denoised signal
        double[] denoised = modwt.inverseMODWT(coeffs);
        
        // Calculate signal-to-noise ratio improvement
        double noiseBeforeMSE = calculateMSE(cleanSignal, noisySignal);
        double noiseAfterMSE = calculateMSE(cleanSignal, denoised);
        
        System.out.println("Noise level before denoising: " + Math.sqrt(noiseBeforeMSE));
        System.out.println("Noise level after denoising: " + Math.sqrt(noiseAfterMSE));
        System.out.println("Noise reduction: " + 
                          String.format("%.1f%%", (1 - noiseAfterMSE/noiseBeforeMSE) * 100));
    }
    
    private static void multiResolutionExample() {
        System.out.println("\n\n=== Multi-Resolution Analysis Example ===\n");
        
        // Create a signal with multiple frequency components
        int N = 512;
        double[] signal = new double[N];
        for (int i = 0; i < N; i++) {
            signal[i] = 2.0 * Math.sin(2 * Math.PI * i / 128.0) +    // Very low freq
                       1.0 * Math.sin(2 * Math.PI * i / 32.0) +      // Low freq
                       0.5 * Math.sin(2 * Math.PI * i / 8.0) +       // Medium freq
                       0.25 * Math.sin(2 * Math.PI * i / 2.0);       // High freq
        }
        
        // Create MODWT transform
        MODWTTransform modwt = new MODWTTransform(new Daubechies4());
        
        // Perform 5-level decomposition
        double[][] coeffs = modwt.forwardMODWT(signal, 5);
        
        // Analyze energy distribution across scales
        System.out.println("Energy distribution across scales:");
        double totalEnergy = calculateEnergy(signal);
        
        for (int i = 0; i < coeffs.length - 1; i++) {
            double levelEnergy = calculateEnergy(coeffs[i]);
            double percentage = (levelEnergy / totalEnergy) * 100;
            System.out.printf("  Detail level %d: %.1f%% of total energy\n", i + 1, percentage);
        }
        
        double approxEnergy = calculateEnergy(coeffs[coeffs.length - 1]);
        double approxPercentage = (approxEnergy / totalEnergy) * 100;
        System.out.printf("  Approximation level %d: %.1f%% of total energy\n", 
                         coeffs.length - 1, approxPercentage);
        
        // Reconstruct signal using only certain levels
        System.out.println("\nSelective reconstruction:");
        
        // Keep only low-frequency components (approximation + last 2 detail levels)
        double[][] lowFreqCoeffs = new double[coeffs.length][coeffs[0].length];
        for (int i = 0; i < coeffs.length; i++) {
            if (i < 3) {
                // Zero out high-frequency details
                lowFreqCoeffs[i] = new double[coeffs[i].length];
            } else {
                // Keep low-frequency components
                lowFreqCoeffs[i] = coeffs[i].clone();
            }
        }
        
        double[] lowFreqSignal = modwt.inverseMODWT(lowFreqCoeffs);
        System.out.println("Low-frequency reconstruction completed");
        
        // Keep only high-frequency components (first 3 detail levels)
        double[][] highFreqCoeffs = new double[coeffs.length][coeffs[0].length];
        for (int i = 0; i < coeffs.length; i++) {
            if (i < 3) {
                // Keep high-frequency details
                highFreqCoeffs[i] = coeffs[i].clone();
            } else {
                // Zero out low-frequency components
                highFreqCoeffs[i] = new double[coeffs[i].length];
            }
        }
        
        double[] highFreqSignal = modwt.inverseMODWT(highFreqCoeffs);
        System.out.println("High-frequency reconstruction completed");
    }
    
    private static double calculateMSE(double[] signal1, double[] signal2) {
        double sum = 0.0;
        for (int i = 0; i < signal1.length; i++) {
            double diff = signal1[i] - signal2[i];
            sum += diff * diff;
        }
        return sum / signal1.length;
    }
    
    private static double calculateEnergy(double[] signal) {
        double energy = 0.0;
        for (double value : signal) {
            energy += value * value;
        }
        return energy;
    }
}