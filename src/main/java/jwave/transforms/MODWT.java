package jwave.transforms;

import jwave.transforms.wavelets.Wavelet;

import java.util.Arrays;

/**
 * An implementation of the Maximal Overlap Discrete Wavelet Transform (MODWT)
 * designed to integrate with the JWave library structure.
 * This version uses the standard, conventional scaling for its filters.
 */
public class MODWT extends WaveletTransform {

    /**
     * Constructor for the MODWT.
     *
     * @param wavelet The mother wavelet to use for the transform.
     */
    public MODWT(Wavelet wavelet) {
        super(wavelet);
    }

    /**
     * Performs the forward transform.
     * NOTE: To satisfy the abstract base class, this method returns only the
     * first level of detail coefficients (D1). For a full decomposition,
     * you MUST call the forwardMODWT() method.
     *
     * @param arrTime The input time series data.
     * @return The detail coefficients for the first decomposition level (D1).
     */
    @Override
    public double[] forward(double[] arrTime) {
        if (arrTime == null || arrTime.length == 0) {
            return new double[0];
        }
        double[][] allCoeffs = forwardMODWT(arrTime, 1);
        return allCoeffs.length > 0 ? allCoeffs[0] : new double[0];
    }

    /**
     * Performs a full forward Maximal Overlap Discrete Wavelet Transform.
     * This is the recommended method to use for this class.
     *
     * @param data      The input time series data.
     * @param maxLevel  The maximum level of decomposition to perform.
     * @return A 2D array where rows represent coefficients for each level.
     * Structure: [D1, D2, ..., D_maxLevel, A_maxLevel]
     */
    public double[][] forwardMODWT(double[] data, int maxLevel) {
        int N = data.length;

        // Step 1: Get the orthonormal DWT filter coefficients from the Wavelet object.
        // For Haar1 from JWave, this returns {1,1}, which is not normalized.
        // For other wavelets like Daubechies, it returns orthonormal coefficients.
        // To be robust, we should normalize the filters ourselves.
        double[] g_dwt = _wavelet.getScalingDeComposition();
        double[] h_dwt = _wavelet.getWaveletDeComposition();
        normalize(g_dwt); // Ensures we have orthonormal DWT filters to start with.
        normalize(h_dwt);

        // Step 2: Create the MODWT filters by scaling the DWT filters by 1/sqrt(2).
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
            // Step 3: Upsample the MODWT filters for the current level.
            double[] gUpsampled = upsample(g_modwt, j);
            double[] hUpsampled = upsample(h_modwt, j);

            // Step 4: Convolve to get the next level of coefficients.
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
     * This is necessary because some JWave wavelets (like Haar1) do not
     * return pre-normalized DWT coefficients.
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

    @Override
    public double[] reverse(double[] arrHilb) {
        System.err.println("Reverse MODWT (iMODWT) is not implemented.");
        return new double[0];
    }

    private static double[] upsample(double[] filter, int level) {
        if (level == 1) return filter.clone();  // return a copy to avoid unintended side effects if the caller modifies
        int gap = (1 << (level - 1)) - 1;
        int newLength = filter.length + (filter.length - 1) * gap;
        double[] upsampled = new double[newLength];
        for (int i = 0; i < filter.length; i++) {
            upsampled[i * (gap + 1)] = filter[i];
        }
        return upsampled;
    }

    private static double[] circularConvolve(double[] signal, double[] filter) {
        int N = signal.length;
        int M = filter.length;
        double[] output = new double[N];
        for (int n = 0; n < N; n++) {
            double sum = 0.0;
            for (int m = 0; m < M; m++) {
                int signalIndex = (n - m + N) % N;
                sum += signal[signalIndex] * filter[m];
            }
            output[n] = sum;
        }
        return output;
    }
}