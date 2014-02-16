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
 * Constructor setting up the orthonormal Coiflet wavelet of 18 coefficients and
 * the scales; normed, due to ||*||2 - euclidean norm.
 * 
 * @author Christian Scheiblich
 * @date 15.02.2014 22:58:59
 */
public class Coiflet03 extends Wavelet {

  /**
   * Already orthonormal coefficients taken from Filip Wasilewski's webpage
   * http://wavelets.pybytes.com/wavelet/coif3/ Thanks!
   * 
   * @author Christian Scheiblich
   * @date 15.02.2014 22:58:59
   */
  public Coiflet03( ) {

    _transformWavelength = 2; // minimal wavelength of input signal

    _motherWavelength = 18; // wavelength of mother wavelet

    _scales = new double[ _motherWavelength ];
    _scales[ 0 ] = -3.459977283621256e-05;
    _scales[ 1 ] = -7.098330313814125e-05;
    _scales[ 2 ] = 0.0004662169601128863;
    _scales[ 3 ] = 0.0011175187708906016;
    _scales[ 4 ] = -0.0025745176887502236;
    _scales[ 5 ] = -0.00900797613666158;
    _scales[ 6 ] = 0.015880544863615904;
    _scales[ 7 ] = 0.03455502757306163;
    _scales[ 8 ] = -0.08230192710688598;
    _scales[ 9 ] = -0.07179982161931202;
    _scales[ 10 ] = 0.42848347637761874;
    _scales[ 11 ] = 0.7937772226256206;
    _scales[ 12 ] = 0.4051769024096169;
    _scales[ 13 ] = -0.06112339000267287;
    _scales[ 14 ] = -0.0657719112818555;
    _scales[ 15 ] = 0.023452696141836267;
    _scales[ 16 ] = 0.007782596427325418;
    _scales[ 17 ] = -0.003793512864491014;

    // building wavelet as orthogonal (orthonormal) space from
    // scaling coefficients. Have a look into Alfred Haar's
    // wavelet for understanding what is done. ;-)
    _coeffs = new double[ _motherWavelength ];
    for( int i = 0; i < _motherWavelength; i++ )
      if( i % 2 == 0 )
        _coeffs[ i ] = _scales[ ( _motherWavelength - 1 ) - i ];
      else
        _coeffs[ i ] = -_scales[ ( _motherWavelength - 1 ) - i ];

  } // Coiflet03

} // Coiflet03