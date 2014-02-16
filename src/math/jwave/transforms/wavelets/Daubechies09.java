/**
 * JWave - Java implementation of wavelet transform algorithms
 *
 * Copyright 2008-2014 Christian Scheiblich
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at 
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 *
 * This file is part of JWave.
 *
 * @author Christian Scheiblich
 * @date 23.05.2008 17:42:23
 * cscheiblich@gmail.com
 */
package math.jwave.transforms.wavelets;

/**
 * Ingrid Daubechies' orthonormal wavelet of 18 coefficients and the scales;
 * normed, due to ||*||2 - euclidean norm.
 * 
 * @author Christian Scheiblich
 * @date 16.02.2014 00:34:30
 */
public class Daubechies09 extends Wavelet {

  /**
   * Already orthonormal coefficients taken from Filip Wasilewski's webpage
   * http://wavelets.pybytes.com/wavelet/db9/ Thanks!
   * 
   * @author Christian Scheiblich
   * @date 16.02.2014 00:34:30
   */
  public Daubechies09( ) {

    _transformWavelength = 2; // minimal wavelength of input signal

    _motherWavelength = 18; // wavelength of mother wavelet

    _scales = new double[ _motherWavelength ];
    _scales[ 0 ] = 3.9347319995026124e-05;
    _scales[ 1 ] = -0.0002519631889981789;
    _scales[ 2 ] = 0.00023038576399541288;
    _scales[ 3 ] = 0.0018476468829611268;
    _scales[ 4 ] = -0.004281503681904723;
    _scales[ 5 ] = -0.004723204757894831;
    _scales[ 6 ] = 0.022361662123515244;
    _scales[ 7 ] = 0.00025094711499193845;
    _scales[ 8 ] = -0.06763282905952399;
    _scales[ 9 ] = 0.030725681478322865;
    _scales[ 10 ] = 0.14854074933476008;
    _scales[ 11 ] = -0.09684078322087904;
    _scales[ 12 ] = -0.29327378327258685;
    _scales[ 13 ] = 0.13319738582208895;
    _scales[ 14 ] = 0.6572880780366389;
    _scales[ 15 ] = 0.6048231236767786;
    _scales[ 16 ] = 0.24383467463766728;
    _scales[ 17 ] = 0.03807794736316728;

    // building wavelet as orthogonal (orthonormal) space from
    // scaling coefficients. Have a look into Alfred Haar's
    // wavelet for understanding what is done. ;-)
    _coeffs = new double[ _motherWavelength ];
    for( int i = 0; i < _motherWavelength; i++ )
      if( i % 2 == 0 )
        _coeffs[ i ] = _scales[ ( _motherWavelength - 1 ) - i ];
      else
        _coeffs[ i ] = -_scales[ ( _motherWavelength - 1 ) - i ];

  } // Daubechies09

} // Daubechies09
