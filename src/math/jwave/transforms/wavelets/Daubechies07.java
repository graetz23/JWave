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
 * Ingrid Daubechies' orthonormal wavelet of 14 coefficients and the scales;
 * normed, due to ||*||2 - euclidean norm.
 * 
 * @author Christian Scheiblich
 * @date 16.02.2014 00:26:36
 */
public class Daubechies07 extends Wavelet {

  /**
   * @author Christian Scheiblich
   * @date 16.02.2014 00:26:36
   */
  public Daubechies07( ) {

    _transformWavelength = 2; // minimal wavelength of input signal

    _motherWavelength = 14; // wavelength of mother wavelet

    // TODO Get analytical formulation, due to its precision; this is around 1.e-3 only
    // taken from http://de.wikipedia.org/wiki/Daubechies-Wavelets
    //
    _scales = new double[ _motherWavelength ];
    _scales[ 0 ] = 0.11009943;
    _scales[ 1 ] = 0.56079128;
    _scales[ 2 ] = 1.03114849;
    _scales[ 3 ] = 0.66437248;
    _scales[ 4 ] = -0.20351382;
    _scales[ 5 ] = -0.31683501;
    _scales[ 6 ] = 0.1008467;
    _scales[ 7 ] = 0.11400345;
    _scales[ 8 ] = -0.05378245;
    _scales[ 9 ] = -0.02343994;
    _scales[ 10 ] = 0.01774979;
    _scales[ 11 ] = 6.07514995e-4;
    _scales[ 12 ] = -2.54790472e-3;
    _scales[ 13 ] = 5.00226853e-4;

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

  } // Daubechies07

} // Daubechies07
