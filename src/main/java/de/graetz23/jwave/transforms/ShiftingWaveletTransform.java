/**
 * JWave is distributed under the MIT License (MIT); this file is part of.
 * <p>
 * Copyright (c) 2008-2025 JWave Christian (graetz23@gmail.com)
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package de.graetz23.jwave.transforms;

import de.graetz23.jwave.exceptions.JWaveException;
import de.graetz23.jwave.exceptions.JWaveFailureNotImplemented;
import de.graetz23.jwave.tools.MathToolKit;
import de.graetz23.jwave.transforms.wavelets.Wavelet;

/**
 * Shifting Wavelet Transform shifts a wavelet of smallest wavelength over the
 * input array, then by the double wavelength, .., and so on.
 *
 * @author Christian (graetz23@gmail.com)
 * @date 15.02.2016 23:12:55
 */
public class ShiftingWaveletTransform extends WaveletTransform {

    /**
     * Constructor taking Wavelet object for performing the transform.
     *
     * @author Christian (graetz23@gmail.com)
     */
    public ShiftingWaveletTransform(Wavelet wavelet) {
        super(wavelet);
    } // ShiftingWaveletTransform

    /**
     * Forward method that uses strictly the abilities of an orthogonal transform.
     *
     * @author Christian (graetz23@gmail.com)
     */
    @Override
    public double[] forward(double[] arrTime) throws JWaveException {

        int length = arrTime.length;

        int div = 2;
        int odd = length % div; // if odd == 1 => steps * 2 + odd else steps * 2

        double[] arrHilb = new double[length];
        for (int i = 0; i < length; i++)
            arrHilb[i] = arrTime[i];

        while (div <= length) {

            int splits = length / div; // cuts the digits == round down to full

            // doing smallest wavelength of div by no of steps

            for (int s = 0; s < splits; s++) {

                double[] arrDiv = new double[div];
                double[] arrRes = null;

                for (int p = 0; p < div; p++)
                    arrDiv[p] = arrHilb[s * div + p];

                arrRes = _wavelet.forward(arrDiv, div);

                for (int q = 0; q < div; q++)
                    arrHilb[s * div + q] = arrRes[q];

            } // s

            div *= 2;

        } // while

        if (odd == 1)
            arrHilb[length - 1] = arrTime[length - 1];

        return arrHilb;

    } // forward

    /**
     * Reverse method that uses strictly the abilities of an orthogonal transform.
     *
     * @author Christian (graetz23@gmail.com)
     */
    @Override
    public double[] reverse(double[] arrHilb) throws JWaveException {

        int length = arrHilb.length;

        int div = 0;
        if (length % 2 == 0)
            div = length;
        else {
            div = length / 2; // 2 = 4.5 => 4
            div *= 2; // 4 * 2 = 8
        }

        int odd = length % div; // if odd == 1 => steps * 2 + odd else steps * 2

        double[] arrTime = new double[length];
        for (int i = 0; i < length; i++)
            arrTime[i] = arrHilb[i];

        while (div >= 2) {

            int splits = length / div; // cuts the digits == round down to full

            // doing smallest wavelength of div by no of steps

            for (int s = 0; s < splits; s++) {

                double[] arrDiv = new double[div];
                double[] arrRes = null;

                for (int p = 0; p < div; p++)
                    arrDiv[p] = arrTime[s * div + p];

                arrRes = _wavelet.reverse(arrDiv, div);

                for (int q = 0; q < div; q++)
                    arrTime[s * div + q] = arrRes[q];

            } // s

            div /= 2;

        } // while

        if (odd == 1)
            arrTime[length - 1] = arrHilb[length - 1];

        return arrTime;

    } // reverse

} // class
