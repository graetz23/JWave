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
 * date 23.02.2008 17:42:23
 * contact cscheiblich@gmail.com
 */
package math.jwave.transforms.wavelets;

/**
 * Ingrid Daubechies' orthonormal wavelet of 12 coefficients and the scales;
 * normed, due to ||*||2 - euclidean norm.
 * 
 * @author Christian Scheiblich
 * @date 16.02.2014 00:21:31
 */
public class Daubechies06 extends Wavelet {

  /**
   * @author Christian Scheiblich
   * @date 16.02.2014 00:21:31
   */
  public Daubechies06( ) {

    _transformWavelength = 2; // minimal wavelength of input signal

    _motherWavelength = 12; // wavelength of mother wavelet

    // TODO Get analytical formulation, due to its precision; this is around 1.e-3 only
    // taken from http://de.wikipedia.org/wiki/Daubechies-Wavelets
    //
    _scales = new double[ _motherWavelength ];
    _scales[ 0 ] = 0.15774243;
    _scales[ 1 ] = 0.69950381;
    _scales[ 2 ] = 1.06226376;
    _scales[ 3 ] = 0.44583132;
    _scales[ 4 ] = -0.31998660;
    _scales[ 5 ] = -0.18351806;
    _scales[ 6 ] = 0.13788809;
    _scales[ 7 ] = 0.03892321;
    _scales[ 8 ] = -0.04466375;
    _scales[ 9 ] = 7.883251152e-4; //   7.83251152e-4
    _scales[ 10 ] = 6.75606236e-3;  //   6.75606236e-3
    _scales[ 11 ] = -1.52353381e-3;  //  -1.52353381e-3  

    // normalize orthogonal space => orthonormal space!!!  
    double sqrt02 = 1.4142135623730951;
    for( int i = 0; i < _motherWavelength; i++ )
      _scales[ i ] /= sqrt02;

    // building wavelet as orthogonal (orthonormal) space from
    // scaling coefficients. Have a look into Alfred Haar's
    // wavelet for understanding what is done. ;-)
    _coeffs = new double[ _motherWavelength ];
    for( int i = 0; i < _motherWavelength; i++ )
      if( i % 2 == 0 )
        _coeffs[ i ] = _scales[ ( _motherWavelength - 1 ) - i ];
      else
        _coeffs[ i ] = -_scales[ ( _motherWavelength - 1 ) - i ];

  } // Daubechies06

} // Daubechies06
