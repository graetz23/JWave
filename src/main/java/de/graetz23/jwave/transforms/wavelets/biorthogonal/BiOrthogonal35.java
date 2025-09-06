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
package jwave.transforms.wavelets.biorthogonal;

/**
 * BiOrthogonal Wavelet of type 3.5 - Three vanishing moments in wavelet
 * function and five vanishing moments in scaling function.
 * 
 * @author Christian (graetz23@gmail.com)
 * @date 16.02.2014 17:17:51
 */
public class BiOrthogonal35 extends BiOrthogonal {

  /**
   * Already orthonormal coefficients taken from Filip Wasilewski's webpage
   * http://wavelets.pybytes.com/wavelet/bior3.5/ Thanks!
   * 
   * @author Christian (graetz23@gmail.com)
   * @date 16.02.2014 17:17:51
   */
  public BiOrthogonal35( ) {

    _name = "BiOrthogonal 3/5"; // name of the wavelet

    _transformWavelength = 2; // minimal wavelength of input signal

    _motherWavelength = 12; // wavelength of mother wavelet

    _scalingDeCom = new double[ _motherWavelength ];
    _scalingDeCom[ 0 ] = -0.013810679320049757;
    _scalingDeCom[ 1 ] = 0.04143203796014927;
    _scalingDeCom[ 2 ] = 0.052480581416189075;
    _scalingDeCom[ 3 ] = -0.26792717880896527;
    _scalingDeCom[ 4 ] = -0.07181553246425874;
    _scalingDeCom[ 5 ] = 0.966747552403483;
    _scalingDeCom[ 6 ] = 0.966747552403483;
    _scalingDeCom[ 7 ] = -0.07181553246425874;
    _scalingDeCom[ 8 ] = -0.26792717880896527;
    _scalingDeCom[ 9 ] = 0.052480581416189075;
    _scalingDeCom[ 10 ] = 0.04143203796014927;
    _scalingDeCom[ 11 ] = -0.013810679320049757;

    _waveletDeCom = new double[ _motherWavelength ];
    _waveletDeCom[ 0 ] = 0.;
    _waveletDeCom[ 1 ] = 0.;
    _waveletDeCom[ 2 ] = 0.;
    _waveletDeCom[ 3 ] = 0.;
    _waveletDeCom[ 4 ] = -0.1767766952966369;
    _waveletDeCom[ 5 ] = 0.5303300858899107;
    _waveletDeCom[ 6 ] = -0.5303300858899107;
    _waveletDeCom[ 7 ] = 0.1767766952966369;
    _waveletDeCom[ 8 ] = 0.;
    _waveletDeCom[ 9 ] = 0.;
    _waveletDeCom[ 10 ] = 0.;
    _waveletDeCom[ 11 ] = 0.;

    // build all other coefficients from low & high pass decomposition
    _buildBiOrthonormalSpace( );

  } // BiOrthogonal35

} // BiOrthogonal35