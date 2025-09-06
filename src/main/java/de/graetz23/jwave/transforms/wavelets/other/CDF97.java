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
package jwave.transforms.wavelets.other;

import jwave.transforms.wavelets.Wavelet;

/**
 * Cohen Daubechies Feauveau (CDF) 9/7 Wavelet
 * 
 * @author Christian (graetz23@gmail.com)
 * @date 17.08.2014 13:52:41
 */
public class CDF97 extends Wavelet {

  /**
   * Cohen Daubechies Feauveau (CDF) 9/7 Wavelet. THIS WAVELET IS NOT WORKING -
   * DUE TO ODD NUMBER COEFFICIENTS!!!
   * 
   * @author Christian (graetz23@gmail.com)
   * @date 17.08.2014 13:52:41
   */
  public CDF97( ) {

    _name = "CDF 9/7"; // name of the wavelet

    _transformWavelength = 2; // minimal wavelength of input signal

    _motherWavelength = 9; // wavelength of mother wavelet

    //    double sqrt2 = Math.sqrt( 2. );

    _scalingDeCom = new double[ _motherWavelength ];
    _scalingDeCom[ 0 ] = 0.026748757411; //
    _scalingDeCom[ 1 ] = -0.016864118443; //
    _scalingDeCom[ 2 ] = -0.078223266529; //
    _scalingDeCom[ 3 ] = 0.266864118443; //
    _scalingDeCom[ 4 ] = 0.602949018236; //
    _scalingDeCom[ 5 ] = 0.266864118443; //
    _scalingDeCom[ 6 ] = -0.078223266529; //
    _scalingDeCom[ 7 ] = -0.016864118443; //
    _scalingDeCom[ 8 ] = 0.026748757411; //

    _waveletDeCom = new double[ _motherWavelength ];
    _waveletDeCom[ 0 ] = 0.; // 
    _waveletDeCom[ 1 ] = 0.091271763114; // 
    _waveletDeCom[ 2 ] = -0.057543526229; // 
    _waveletDeCom[ 3 ] = -0.591271763114; // 
    _waveletDeCom[ 4 ] = 1.11508705; // 
    _waveletDeCom[ 5 ] = -0.591271763114; // 
    _waveletDeCom[ 6 ] = -0.057543526229; // 
    _waveletDeCom[ 7 ] = 0.091271763114; // 
    _waveletDeCom[ 8 ] = 0.; // 

    // Copy to reconstruction filters due to orthogonality!
    _scalingReCon = new double[ _motherWavelength ];
    _waveletReCon = new double[ _motherWavelength ];
    for( int i = 0; i < _motherWavelength; i++ ) {
      _scalingReCon[ i ] = _scalingDeCom[ i ];
      _waveletReCon[ i ] = _waveletDeCom[ i ];
    } // i

  } // CDF97

} // CDF97
