package jwave.transforms;

import jwave.transforms.wavelets.haar.Haar1;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class MODWTTransformTest {

    private MODWTTransform modwtHaar;
    private double[] simpleSignal;

    @Before
    public void setUp() {
        // Use the simplest wavelet (Haar) for predictable results.
        modwtHaar = new MODWTTransform(new Haar1());
        // A simple signal for which we can manually calculate results.
        simpleSignal = new double[]{1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0};
    }

    @Test
    public void testOutputDimensions() {
        int maxLevel = 3;
        double[][] coeffs = modwtHaar.forwardMODWT(simpleSignal, maxLevel);

        // We expect maxLevel detail arrays + 1 approximation array.
        assertEquals("Should be maxLevel+1 rows of coefficients.", maxLevel + 1, coeffs.length);
        // Each row of coefficients should have the same length as the input signal.
        for (int i = 0; i <= maxLevel; i++) {
            assertEquals("Row " + i + " should have the same length as the input signal.", simpleSignal.length, coeffs[i].length);
        }
    }

    @Test
    public void testKnownValuesWithHaar() {
        // This test validates the behavior with the standard MODWT filter scaling.
        double[][] coeffs = modwtHaar.forwardMODWT(simpleSignal, 1);

        double[] d1 = coeffs[0]; // Detail coefficients D1
        double[] a1 = coeffs[1]; // Approximation coefficients A1

        // The MODWT filters for Haar are {0.5, -0.5} and {0.5, 0.5}.
        double[] expectedD1 = {
                (1.0 * 0.5) + (8.0 * -0.5), // -3.5
                (2.0 * 0.5) + (1.0 * -0.5), //  0.5
                (3.0 * 0.5) + (2.0 * -0.5), //  0.5
                (4.0 * 0.5) + (3.0 * -0.5), //  0.5
                (5.0 * 0.5) + (4.0 * -0.5), //  0.5
                (6.0 * 0.5) + (5.0 * -0.5), //  0.5
                (7.0 * 0.5) + (6.0 * -0.5), //  0.5
                (8.0 * 0.5) + (7.0 * -0.5)  //  0.5
        };

        double[] expectedA1 = {
                (1.0 * 0.5) + (8.0 * 0.5),  // 4.5
                (2.0 * 0.5) + (1.0 * 0.5),  // 1.5
                (3.0 * 0.5) + (2.0 * 0.5),  // 2.5
                (4.0 * 0.5) + (3.0 * 0.5),  // 3.5
                (5.0 * 0.5) + (4.0 * 0.5),  // 4.5
                (6.0 * 0.5) + (5.0 * 0.5),  // 5.5
                (7.0 * 0.5) + (6.0 * 0.5),  // 6.5
                (8.0 * 0.5) + (7.0 * 0.5)   // 7.5
        };

        assertArrayEquals("Detail coefficients for Level 1 do not match expected values.", expectedD1, d1, 1e-9);
        assertArrayEquals("Approximation coefficients for Level 1 do not match expected values.", expectedA1, a1, 1e-9);
    }

    @Test
    public void testEnergyConservation() {
        int maxLevel = 3;
        double[][] coeffs = modwtHaar.forwardMODWT(simpleSignal, maxLevel);

        // Calculate variance of the original signal
        double signalVariance = calculateVariance(simpleSignal);

        // Calculate the sum of variances of all coefficient arrays
        double coeffsVarianceSum = 0.0;
        for (double[] coeffLevel : coeffs) {
            coeffsVarianceSum += calculateVariance(coeffLevel);
        }

        // The variance of the signal should equal the sum of variances of the coefficients
        assertEquals("Energy (variance) should be conserved.", signalVariance, coeffsVarianceSum, 1e-9);
    }

    @Test
    public void testEmptyInput() {
        int maxLevel = 3;
        double[][] coeffs = modwtHaar.forwardMODWT(new double[0], maxLevel);
        assertEquals(maxLevel + 1, coeffs.length); // It will create the outer array
        assertEquals(0, coeffs[0].length); // But inner arrays will be empty
    }

    /**
     * Helper method to calculate the variance of a data array.
     * @param data The array of doubles.
     * @return The variance of the data.
     */
    private double calculateVariance(double[] data) {
        if (data == null || data.length == 0) {
            return 0.0;
        }
        double sum = Arrays.stream(data).sum();
        double mean = sum / data.length;
        double squaredDifferences = Arrays.stream(data)
                .map(x -> (x - mean) * (x - mean))
                .sum();
        return squaredDifferences / data.length;
    }
}
