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
 * BiOrthogonal Wavelet of type 2.2 - Two vanishing moments in wavelet function
 * and two vanishing moments in scaling function.
 * 
 * @author Christian (graetz23@gmail.com)
 * @date 16.02.2014 16:15:01
 */
public class BiOrthogonal22 extends BiOrthogonal {

  /**
   * Already orthonormal coefficients taken from Filip Wasilewski's webpage
   * http://wavelets.pybytes.com/wavelet/bior2.2/ Thanks!
   * 
   * @author Christian (graetz23@gmail.com)
   * @date 16.02.2014 16:15:01
   */
  public BiOrthogonal22( ) {

    _name = "BiOrthogonal 2/2"; // name of the wavelet

    _transformWavelength = 2; // minimal wavelength of input signal

    _motherWavelength = 6; // wavelength of mother wavelet

    _scalingDeCom = new double[ _motherWavelength ];
    _scalingDeCom[ 0 ] = 0.;
    _scalingDeCom[ 1 ] = -0.1767766952966369;
    _scalingDeCom[ 2 ] = 0.3535533905932738;
    _scalingDeCom[ 3 ] = 1.0606601717798214;
    _scalingDeCom[ 4 ] = 0.3535533905932738;
    _scalingDeCom[ 5 ] = -0.1767766952966369;

    _waveletDeCom = new double[ _motherWavelength ];
    _waveletDeCom[ 0 ] = 0.;
    _waveletDeCom[ 1 ] = 0.3535533905932738;
    _waveletDeCom[ 2 ] = -0.7071067811865476;
    _waveletDeCom[ 3 ] = 0.3535533905932738;
    _waveletDeCom[ 4 ] = 0.;
    _waveletDeCom[ 5 ] = 0.;

    _scalingReCon = new double[ _motherWavelength ];
    _scalingReCon[ 0 ] = 0.;
    _scalingReCon[ 1 ] = 0.3535533905932738;
    _scalingReCon[ 2 ] = 0.7071067811865476;
    _scalingReCon[ 3 ] = 0.3535533905932738;
    _scalingReCon[ 4 ] = 0.;
    _scalingReCon[ 5 ] = 0.;

    _waveletReCon = new double[ _motherWavelength ];
    _waveletReCon[ 0 ] = 0.;
    _waveletReCon[ 1 ] = 0.1767766952966369;
    _waveletReCon[ 2 ] = 0.3535533905932738;
    _waveletReCon[ 3 ] = -1.0606601717798214;
    _waveletReCon[ 4 ] = 0.3535533905932738;
    _waveletReCon[ 5 ] = 0.1767766952966369;

    // build all other coefficients from low & high pass decomposition
    //_buildBiOrthonormalSpace( );

  } // BiOrthogonal22

} // BiOrthogonal22