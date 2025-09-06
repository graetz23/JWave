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
 * BiOrthogonal Wavelet of type 3.1 - Three vanishing moments in wavelet
 * function and one vanishing moment in scaling function.
 * 
 * @author Christian (graetz23@gmail.com)
 * @date 16.02.2014 16:50:58
 */
public class BiOrthogonal31 extends BiOrthogonal {

  /**
   * Already orthonormal coefficients taken from Filip Wasilewski's webpage
   * http://wavelets.pybytes.com/wavelet/bior3.1/ Thanks!
   * 
   * @author Christian (graetz23@gmail.com)
   * @date 16.02.2014 16:50:58
   */
  public BiOrthogonal31( ) {

    _name = "BiOrthogonal 3/1"; // name of the wavelet

    _transformWavelength = 2; // minimal wavelength of input signal

    _motherWavelength = 4; // wavelength of mother wavelet

    _scalingDeCom = new double[ _motherWavelength ];
    _scalingDeCom[ 0 ] = -0.3535533905932738;
    _scalingDeCom[ 1 ] = 1.0606601717798214;
    _scalingDeCom[ 2 ] = 1.0606601717798214;
    _scalingDeCom[ 3 ] = -0.3535533905932738;

    _waveletDeCom = new double[ _motherWavelength ];
    _waveletDeCom[ 0 ] = -0.1767766952966369;
    _waveletDeCom[ 1 ] = 0.5303300858899107;
    _waveletDeCom[ 2 ] = -0.5303300858899107;
    _waveletDeCom[ 3 ] = 0.1767766952966369;

    // build all other coefficients from low & high pass decomposition
    _buildBiOrthonormalSpace( );

  } // BiOrthogonal31

} // BiOrthogonal31