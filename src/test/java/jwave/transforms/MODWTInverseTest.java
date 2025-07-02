package jwave.transforms;

import jwave.transforms.wavelets.haar.Haar1;
import jwave.transforms.wavelets.daubechies.Daubechies4;
import jwave.transforms.wavelets.daubechies.Daubechies6;
import jwave.transforms.wavelets.symlets.Symlet8;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test class for the inverse MODWT (iMODWT) implementation.
 * Verifies perfect reconstruction, energy conservation, and various edge cases.
 */
public class MODWTInverseTest {

    private static final double TOLERANCE = 1e-10;

    @Test
    public void testPerfectReconstructionHaar() {
        MODWTTransform modwt = new MODWTTransform(new Haar1());
        double[] signal = generateTestSignal(256);
        
        // Forward transform
        double[][] coeffs = modwt.forwardMODWT(signal, 4);
        
        // Inverse transform
        double[] reconstructed = modwt.inverseMODWT(coeffs);
        
        // Check reconstruction accuracy
        assertEquals("Reconstructed signal length mismatch", signal.length, reconstructed.length);
        double mse = calculateMSE(signal, reconstructed);
        assertTrue("MSE should be negligible but was: " + mse, mse < TOLERANCE);
    }

    @Test
    public void testPerfectReconstructionDaubechies4() {
        MODWTTransform modwt = new MODWTTransform(new Daubechies4());
        double[] signal = generateTestSignal(128);
        
        // Forward transform
        double[][] coeffs = modwt.forwardMODWT(signal, 3);
        
        // Inverse transform
        double[] reconstructed = modwt.inverseMODWT(coeffs);
        
        // Check reconstruction accuracy
        assertEquals("Reconstructed signal length mismatch", signal.length, reconstructed.length);
        double mse = calculateMSE(signal, reconstructed);
        assertTrue("MSE should be negligible but was: " + mse, mse < TOLERANCE);
    }

    @Test
    public void testPerfectReconstructionMultipleWavelets() {
        // Test with different wavelets
        testWaveletReconstruction(new Haar1(), 64, 3);
        testWaveletReconstruction(new Daubechies4(), 128, 4);
        testWaveletReconstruction(new Daubechies6(), 256, 5);
        testWaveletReconstruction(new Symlet8(), 512, 6);
    }

    private void testWaveletReconstruction(jwave.transforms.wavelets.Wavelet wavelet, 
                                          int signalLength, int levels) {
        MODWTTransform modwt = new MODWTTransform(wavelet);
        double[] signal = generateTestSignal(signalLength);
        
        double[][] coeffs = modwt.forwardMODWT(signal, levels);
        double[] reconstructed = modwt.inverseMODWT(coeffs);
        
        double mse = calculateMSE(signal, reconstructed);
        assertTrue("Failed for " + wavelet.getName() + " with MSE: " + mse, 
                   mse < TOLERANCE);
    }

    @Test
    public void testNonPowerOfTwoLength() {
        MODWTTransform modwt = new MODWTTransform(new Daubechies6());
        
        int[] testLengths = {100, 288, 500, 1000};
        for (int length : testLengths) {
            double[] signal = generateTestSignal(length);
            double[][] coeffs = modwt.forwardMODWT(signal, 3);
            double[] reconstructed = modwt.inverseMODWT(coeffs);
            
            assertEquals("Length mismatch for size " + length, 
                        length, reconstructed.length);
            double mse = calculateMSE(signal, reconstructed);
            assertTrue("Failed for length " + length + " with MSE: " + mse, 
                       mse < TOLERANCE);
        }
    }

    @Test
    public void testEnergyConservation() {
        MODWTTransform modwt = new MODWTTransform(new Daubechies4());
        double[] signal = generateTestSignal(256);
        
        // Calculate signal energy
        double signalEnergy = calculateEnergy(signal);
        
        // Forward transform
        double[][] coeffs = modwt.forwardMODWT(signal, 4);
        
        // Inverse transform
        double[] reconstructed = modwt.inverseMODWT(coeffs);
        
        // Calculate reconstructed signal energy
        double reconstructedEnergy = calculateEnergy(reconstructed);
        
        // Check energy conservation
        double energyRatio = Math.abs(signalEnergy - reconstructedEnergy) / signalEnergy;
        assertTrue("Energy not conserved, ratio: " + energyRatio, 
                   energyRatio < TOLERANCE);
    }

    @Test
    public void testCoefficientModification() {
        MODWTTransform modwt = new MODWTTransform(new Haar1());
        double[] signal = generateTestSignal(128);
        
        // Forward transform
        double[][] coeffs = modwt.forwardMODWT(signal, 3);
        
        // Zero out detail coefficients at level 1 (high frequency)
        for (int i = 0; i < coeffs[0].length; i++) {
            coeffs[0][i] = 0.0;
        }
        
        // Inverse transform
        double[] reconstructed = modwt.inverseMODWT(coeffs);
        
        // The reconstructed signal should be smoother (less high frequency content)
        // but should still have the same length
        assertEquals("Reconstructed signal length mismatch", 
                    signal.length, reconstructed.length);
        
        // Verify that the signal has been modified
        double diff = 0.0;
        for (int i = 0; i < signal.length; i++) {
            diff += Math.abs(signal[i] - reconstructed[i]);
        }
        assertTrue("Signal should be modified after zeroing coefficients", 
                   diff > 1.0);
    }

    @Test
    public void testReverseMethodIntegration() {
        MODWTTransform modwt = new MODWTTransform(new Daubechies4());
        double[] signal = generateTestSignal(64);
        
        // Forward transform - single level
        double[][] coeffs2D = modwt.forwardMODWT(signal, 1);
        
        // For single-level transform, we have 2 arrays: detail and approximation
        // Flatten coefficients for reverse() method
        double[] flatCoeffs = new double[coeffs2D[0].length + coeffs2D[1].length];
        System.arraycopy(coeffs2D[0], 0, flatCoeffs, 0, coeffs2D[0].length);
        System.arraycopy(coeffs2D[1], 0, flatCoeffs, coeffs2D[0].length, coeffs2D[1].length);
        
        // Use reverse() method
        double[] reconstructed = modwt.reverse(flatCoeffs);
        
        // Check reconstruction
        assertEquals("Reconstructed signal length mismatch", 
                    signal.length, reconstructed.length);
        double mse = calculateMSE(signal, reconstructed);
        assertTrue("MSE should be negligible but was: " + mse, mse < TOLERANCE);
    }

    @Test
    public void testSingleLevelReconstruction() {
        MODWTTransform modwt = new MODWTTransform(new Haar1());
        double[] signal = {1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0};
        
        // Single level forward transform
        double[][] coeffs = modwt.forwardMODWT(signal, 1);
        
        // Inverse transform
        double[] reconstructed = modwt.inverseMODWT(coeffs);
        
        // Check perfect reconstruction
        assertArrayEquals("Single level reconstruction failed", 
                         signal, reconstructed, TOLERANCE);
    }

    @Test
    public void testEmptyInput() {
        MODWTTransform modwt = new MODWTTransform(new Haar1());
        
        // Test empty coefficient array
        double[][] emptyCoeffs = new double[0][];
        double[] result = modwt.inverseMODWT(emptyCoeffs);
        assertEquals("Empty input should return empty array", 0, result.length);
        
        // Test null input
        result = modwt.inverseMODWT(null);
        assertEquals("Null input should return empty array", 0, result.length);
    }

    @Test
    public void testConstantSignal() {
        MODWTTransform modwt = new MODWTTransform(new Daubechies4());
        
        // Create a constant signal
        double[] signal = new double[100];
        for (int i = 0; i < signal.length; i++) {
            signal[i] = 5.0;
        }
        
        // Forward and inverse transform
        double[][] coeffs = modwt.forwardMODWT(signal, 3);
        double[] reconstructed = modwt.inverseMODWT(coeffs);
        
        // Check reconstruction
        assertArrayEquals("Constant signal reconstruction failed", 
                         signal, reconstructed, TOLERANCE);
    }

    @Test
    public void testLinearTrendSignal() {
        MODWTTransform modwt = new MODWTTransform(new Symlet8());
        
        // Create a linear trend signal
        double[] signal = new double[200];
        for (int i = 0; i < signal.length; i++) {
            signal[i] = 0.5 * i;
        }
        
        // Forward and inverse transform
        double[][] coeffs = modwt.forwardMODWT(signal, 4);
        double[] reconstructed = modwt.inverseMODWT(coeffs);
        
        // Check reconstruction
        double mse = calculateMSE(signal, reconstructed);
        assertTrue("Linear trend reconstruction failed with MSE: " + mse, 
                   mse < TOLERANCE);
    }

    // Helper methods
    
    private double[] generateTestSignal(int length) {
        double[] signal = new double[length];
        for (int i = 0; i < length; i++) {
            // Combination of sinusoids
            signal[i] = Math.sin(2 * Math.PI * i / 32.0) + 
                       0.5 * Math.sin(2 * Math.PI * i / 8.0) +
                       0.25 * Math.cos(2 * Math.PI * i / 64.0);
        }
        return signal;
    }
    
    private double calculateMSE(double[] signal1, double[] signal2) {
        double sum = 0.0;
        for (int i = 0; i < signal1.length; i++) {
            double diff = signal1[i] - signal2[i];
            sum += diff * diff;
        }
        return sum / signal1.length;
    }
    
    private double calculateEnergy(double[] signal) {
        double energy = 0.0;
        for (double value : signal) {
            energy += value * value;
        }
        return energy;
    }
}