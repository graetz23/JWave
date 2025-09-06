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
package de.graetz23.jwave;

import de.graetz23.jwave.exceptions.JWaveException;
import de.graetz23.jwave.exceptions.JWaveFailure;
import de.graetz23.jwave.transforms.BasicTransform;
import de.graetz23.jwave.transforms.DiscreteFourierTransform;
import de.graetz23.jwave.transforms.FastWaveletTransform;
import de.graetz23.jwave.transforms.WaveletPacketTransform;
import de.graetz23.jwave.transforms.wavelets.Wavelet;
import de.graetz23.jwave.transforms.wavelets.WaveletBuilder;

/**
 * Class for creating and identifying Transform object.
 *
 * @author Christian (graetz23@gmail.com)
 */
public class TransformBuilder {

    /**
     * Create a Transform object by a given string and a given Wavelet object.
     * Look into each Transform for matching string identifier. By the way the
     * method requires Java 7, due to the switch statement on a string. *rofl*
     *
     * @author Christian (graetz23@gmail.com)
     * @param transformName
     *          identifier as stored in Transform object
     * @param wavelet
     * @return a matching object of type Transform
     */
    static public Transform create(String transformName, Wavelet wavelet) {

        BasicTransform basicTransform = null;

        try {

            switch (transformName) {

                case "Discrete Fourier Transform":
                    basicTransform = new DiscreteFourierTransform();
                    break;

                case "Fast Wavelet Transform":
                    basicTransform = new FastWaveletTransform(wavelet);
                    break;

                case "Wavelet Packet Transform":
                    basicTransform = new WaveletPacketTransform(wavelet);
                    break;

                default:

                    throw new JWaveFailure(
                            "TransformBuilder::create - unknown type of transform for given string!");

            } // switch

        } catch (JWaveException e) {

            e.showMessage();
            e.printStackTrace();

        } // try

        return new Transform(basicTransform);

    } // create

    /**
     * Create a Transform object by a given string and a given string for a
     * Wavelet object. Look into each Transform and Wavelet for matching string
     * identifier.
     *
     * @author Christian (graetz23@gmail.com)
     * @param transformName
     *          identifier as stored in Transform object
     * @param waveletName
     * @return identifier as stored in Wavelet object
     */
    static public Transform create(String transformName, String waveletName) {

        return create(transformName, WaveletBuilder.create(waveletName));

    } // create

    /**
     * Returns the identifier string of a given Transform object.
     *
     * @author Christian (graetz23@gmail.com)
     * @param transform
     *          string identifier of the given Transform object
     * @return a string as the identifier of the given Transform object
     */
    static public String identify(Transform transform) {

        BasicTransform basicTransform = transform.getBasicTransform();
        return basicTransform.getName();

    } // identify

} // class
