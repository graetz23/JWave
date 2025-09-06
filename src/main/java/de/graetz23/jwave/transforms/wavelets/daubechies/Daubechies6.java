/**
 * JWave is distributed under the MIT License (MIT); this file is part of.
 *
 * Copyright (c) 2008-2024 Christian (graetz23@gmail.com)
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package jwave.transforms.wavelets.daubechies;

import jwave.transforms.wavelets.Wavelet;

/**
 * Ingrid Daubechies' orthonormal wavelet of 12 coefficients and the scales;
 * normed, due to ||*||2 - euclidean norm.
 * 
 * @author Christian (graetz23@gmail.com)
 * @date 16.02.2014 00:21:31
 */
public class Daubechies6 extends Wavelet {

  /**
   * Already orthonormal coefficients taken from Filip Wasilewski's webpage
   * http://wavelets.pybytes.com/wavelet/db6/ Thanks!
   * 
   * @author Christian (graetz23@gmail.com)
   * @date 16.02.2014 00:21:31
   */
  public Daubechies6( ) {

    _name = "Daubechies 6"; // name of the wavelet

    _transformWavelength = 2; // minimal wavelength of input signal

    _motherWavelength = 12; // wavelength of mother wavelet

    _scalingDeCom = new double[ _motherWavelength ];
    _scalingDeCom[ 0 ] = -0.00107730108499558;
    _scalingDeCom[ 1 ] = 0.004777257511010651;
    _scalingDeCom[ 2 ] = 0.0005538422009938016;
    _scalingDeCom[ 3 ] = -0.031582039318031156;
    _scalingDeCom[ 4 ] = 0.02752286553001629;
    _scalingDeCom[ 5 ] = 0.09750160558707936;
    _scalingDeCom[ 6 ] = -0.12976686756709563;
    _scalingDeCom[ 7 ] = -0.22626469396516913;
    _scalingDeCom[ 8 ] = 0.3152503517092432;
    _scalingDeCom[ 9 ] = 0.7511339080215775;
    _scalingDeCom[ 10 ] = 0.4946238903983854;
    _scalingDeCom[ 11 ] = 0.11154074335008017;

    _buildOrthonormalSpace( ); // build all other coefficients from low pass decomposition

  } // Daubechies6

} // Daubechies6
