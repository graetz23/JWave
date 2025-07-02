package jwave.transforms;

import jwave.transforms.wavelets.Wavelet;

import java.util.Arrays;

/**
 * An implementation of the Maximal Overlap Discrete Wavelet Transform (MODWT)
 * designed to integrate with the JWave library structure.
 * This version uses the standard, conventional scaling for its filters.
 */
public class MODWTTransform extends WaveletTransform {

    /**
     * Constructor for the MODWTTransform.
     * @param wavelet The mother wavelet to use for the transform.
     */
    public MODWTTransform(Wavelet wavelet) {
        super(wavelet);
    }

    /**
     * Performs a full forward Maximal Overlap Discrete Wavelet Transform.
     * This is the recommended method to use for this class.
     * @param data      The input time series data.
     * @param maxLevel  The maximum level of decomposition to perform.
     * @return A 2D array where rows represent coefficients for each level.
     * Structure: [D1, D2, ..., D_maxLevel, A_maxLevel]
     */
    public double[][] forwardMODWT(double[] data, int maxLevel) {
        int N = data.length;

        // Step 1: Get the DWT filter coefficients.
        double[] g_dwt = _wavelet.getScalingDeComposition();
        double[] h_dwt = _wavelet.getWaveletDeComposition();

        // Step 2: IMPORTANT - Ensure filters are orthonormal.
        // The Haar1 wavelet in JWave returns {1,1}, not {1/√2, 1/√2}.
        // This step makes the logic robust for ALL wavelet types.
        normalize(g_dwt);
        normalize(h_dwt);

        // Step 3: Create the MODWT filters by scaling the DWT filters by 1/√2.
        double scaleFactor = Math.sqrt(2.0);
        double[] g_modwt = new double[g_dwt.length];
        double[] h_modwt = new double[h_dwt.length];
        for (int i = 0; i < g_dwt.length; i++) {
            g_modwt[i] = g_dwt[i] / scaleFactor;
            h_modwt[i] = h_dwt[i] / scaleFactor;
        }

        double[][] modwtCoeffs = new double[maxLevel + 1][N];
        double[] vCurrent = Arrays.copyOf(data, N);

        for (int j = 1; j <= maxLevel; j++) {
            // Step 4: Upsample the MODWT filters for the current level.
            double[] gUpsampled = upsample(g_modwt, j);
            double[] hUpsampled = upsample(h_modwt, j);

            // Step 5: Convolve to get the next level of coefficients.
            double[] wNext = circularConvolve(vCurrent, hUpsampled);
            double[] vNext = circularConvolve(vCurrent, gUpsampled);

            modwtCoeffs[j - 1] = wNext;
            vCurrent = vNext;

            if (j == maxLevel) {
                modwtCoeffs[j] = vNext;
            }
        }
        return modwtCoeffs;
    }

    /**
     * Normalizes a filter so that the sum of its squared coefficients is 1.
     * @param filter The filter to normalize in-place.
     */
    private void normalize(double[] filter) {
        double energy = 0.0;
        for (double c : filter) {
            energy += c * c;
        }
        double norm = Math.sqrt(energy);
        if (norm > 1e-12) { // Avoid division by zero
            for (int i = 0; i < filter.length; i++) {
                filter[i] /= norm;
            }
        }
    }

    /**
     * Performs a circular convolution using Math.floorMod for robust indexing.
     * @return The resulting convolved signal.
     */
    private static double[] circularConvolve(double[] signal, double[] filter) {
        int N = signal.length;
        int M = filter.length;
        double[] output = new double[N];
        for (int n = 0; n < N; n++) {
            double sum = 0.0;
            for (int m = 0; m < M; m++) {
                // Using Math.floorMod is the safest way to handle circular indexing in Java.
                int signalIndex = Math.floorMod(n - m, N);
                sum += signal[signalIndex] * filter[m];
            }
            output[n] = sum;
        }
        return output;
    }

    // --- Other methods required by WaveletTransform base class ---
    @Override
    public double[] forward(double[] arrTime) {
        if (arrTime == null || arrTime.length == 0) return new double[0];
        double[][] allCoeffs = forwardMODWT(arrTime, 1);
        return allCoeffs.length > 0 ? allCoeffs[0] : new double[0];
    }

    @Override
    public double[] reverse(double[] arrHilb) {
        // This method expects a flattened array of coefficients
        // We need to reconstruct the 2D structure first
        
        if (arrHilb == null || arrHilb.length == 0) {
            return new double[0];
        }
        
        // For MODWT, we need to determine the structure of the coefficient array
        // The reverse() method in JWave typically works with single-level transforms
        // So we'll assume this is a single-level transform result
        
        // For single-level MODWT, the array should contain:
        // First half: detail coefficients
        // Second half: approximation coefficients
        
        int totalLength = arrHilb.length;
        if (totalLength % 2 != 0) {
            System.err.println("Invalid coefficient array length for MODWT inverse.");
            return new double[0];
        }
        
        int N = totalLength / 2;
        double[][] coeffs = new double[2][N];
        
        // Split the flattened array into detail and approximation
        System.arraycopy(arrHilb, 0, coeffs[0], 0, N);  // Detail coefficients
        System.arraycopy(arrHilb, N, coeffs[1], 0, N);  // Approximation coefficients
        
        return inverseMODWT(coeffs);
    }

    /**
     * Performs the inverse Maximal Overlap Discrete Wavelet Transform (iMODWT).
     * @param coefficients A 2D array of MODWT coefficients with structure:
     *                     [D1, D2, ..., D_maxLevel, A_maxLevel]
     * @return The reconstructed time-domain signal.
     */
    public double[] inverseMODWT(double[][] coefficients) {
        if (coefficients == null || coefficients.length == 0) {
            return new double[0];
        }
        
        int maxLevel = coefficients.length - 1;
        if (maxLevel < 0) {
            return new double[0];
        }
        
        int N = coefficients[0].length;
        
        // Get the DWT decomposition filters (for orthogonal wavelets, these are the same as reconstruction)
        double[] g_dwt = _wavelet.getScalingDeComposition();
        double[] h_dwt = _wavelet.getWaveletDeComposition();
        
        // Normalize the filters
        normalize(g_dwt);
        normalize(h_dwt);
        
        // Create MODWT filters by scaling DWT filters by 1/√2
        double scaleFactor = Math.sqrt(2.0);
        double[] g_modwt = new double[g_dwt.length];
        double[] h_modwt = new double[h_dwt.length];
        for (int i = 0; i < g_dwt.length; i++) {
            g_modwt[i] = g_dwt[i] / scaleFactor;
            h_modwt[i] = h_dwt[i] / scaleFactor;
        }
        
        // Start with the approximation coefficients at the deepest level
        double[] vCurrent = Arrays.copyOf(coefficients[maxLevel], N);
        
        // Reconstruct from the deepest level to level 1
        for (int j = maxLevel; j >= 1; j--) {
            // Upsample the filters for the current level
            double[] gUpsampled = upsample(g_modwt, j);
            double[] hUpsampled = upsample(h_modwt, j);
            
            // Get the detail coefficients for this level
            double[] wj = coefficients[j - 1];
            
            // Apply circular convolution for reconstruction
            // For MODWT inverse, we need to perform the adjoint operation
            double[] vFromApprox = circularConvolveAdjoint(vCurrent, gUpsampled);
            double[] vFromDetail = circularConvolveAdjoint(wj, hUpsampled);
            
            // Combine approximation and detail reconstructions
            double[] vNext = new double[N];
            for (int i = 0; i < N; i++) {
                vNext[i] = vFromApprox[i] + vFromDetail[i];
            }
            
            vCurrent = vNext;
        }
        
        return vCurrent;
    }

    /**
     * Performs the adjoint operation of circular convolution for MODWT reconstruction.
     * This implements the transpose of the convolution matrix.
     * @return The resulting signal after adjoint convolution.
     */
    private static double[] circularConvolveAdjoint(double[] signal, double[] filter) {
        int N = signal.length;
        int M = filter.length;
        double[] output = new double[N];
        for (int n = 0; n < N; n++) {
            double sum = 0.0;
            for (int m = 0; m < M; m++) {
                // Adjoint operation: we sum over different indices
                int signalIndex = Math.floorMod(n + m, N);
                sum += signal[signalIndex] * filter[m];
            }
            output[n] = sum;
        }
        return output;
    }

    private static double[] upsample(double[] filter, int level) {
        if (level <= 1) return filter;
        
        // Check for overflow before calculating gap
        if (level > 30) {
            throw new IllegalArgumentException("Level too large for upsampling: " + level);
        }
        
        int gap = (1 << (level - 1)) - 1;
        int newLength = filter.length + (filter.length - 1) * gap;
        
        // Check for negative or overflow
        if (newLength < 0 || newLength < filter.length) {
            throw new IllegalArgumentException("Upsampling would result in array too large");
        }
        
        double[] upsampled = new double[newLength];
        for (int i = 0; i < filter.length; i++) {
            upsampled[i * (gap + 1)] = filter[i];
        }
        return upsampled;
    }
}