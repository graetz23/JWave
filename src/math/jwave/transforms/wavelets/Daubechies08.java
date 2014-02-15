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
 * Ingrid Daubechies' orthonormal wavelet of 16 coefficients and the scales;
 * normed, due to ||*||2 - euclidean norm.
 * 
 * @author Christian Scheiblich
 * @date 16.02.2014 00:30:05
 */
public class Daubechies08 extends Wavelet {

  /**
   * @author Christian Scheiblich
   * @date 16.02.2014 00:30:05
   */
  public Daubechies08( ) {

    _transformWavelength = 2; // minimal wavelength of input signal

    _motherWavelength = 16; // wavelength of mother wavelet

    // taken from http://de.wikipedia.org/wiki/Daubechies-Wavelets
    _scales = new double[ _motherWavelength ];
    _scales[ 0 ] = 0.07695562;
    _scales[ 1 ] = 0.44246725;
    _scales[ 2 ] = 0.95548615;
    _scales[ 3 ] = 0.82781653;
    _scales[ 4 ] = -0.02238574;
    _scales[ 5 ] = -0.40165863;
    _scales[ 6 ] = 6.68194092e-4;
    _scales[ 7 ] = 0.18207636;
    _scales[ 8 ] = -0.02456390;
    _scales[ 9 ] = -0.06235021;
    _scales[ 10 ] = 0.01977216;
    _scales[ 11 ] = 0.01236884;
    _scales[ 12 ] = -6.88771926e-3;
    _scales[ 13 ] = -5.54004549e-4;
    _scales[ 14 ] = 9.55229711e-4;
    _scales[ 15 ] = -1.66137261e-4;

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

  } // Daubechies08

} // Daubechies08
