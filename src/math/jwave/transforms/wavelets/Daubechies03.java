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
 * Ingrid Daubechies' orthonormal wavelet of six coefficients and the scales;
 * normed, due to ||*||2 - euclidean norm.
 * 
 * @date 15.02.2014 22:23:20
 * @author Christian Scheiblich
 */
public class Daubechies03 extends Wavelet {

  /**
   * Constructor setting up the orthonormal Daubechie6 wavelet coeffs and the
   * scales; normed, due to ||*||2 - euclidean norm.
   * 
   * @date 25.03.2010 14:03:20
   * @author Christian Scheiblich
   */
  public Daubechies03( ) {

    _transformWavelength = 2; // minimal wavelength of input signal
    
    _motherWavelength = 6; // wavelength of mother wavelet

    double sqrt02 = 1.4142135623730951; // Math.sqrt( 2. )
    double sqrt10 = Math.sqrt( 10. );
    double constA = Math.sqrt( 5. + 2. * sqrt10 );

    _scales = new double[ _motherWavelength ]; // can be done in static way also; faster?
    
    // taken from http://de.wikipedia.org/wiki/Daubechies-Wavelets
    //
    // _scales[ 0 ] = 0.47046721;
    // _scales[ 1 ] = 1.14111692;
    // _scales[ 2 ] = 0.650365;
    // _scales[ 3 ] = -0.19093442;
    // _scales[ 4 ] = -0.12083221;
    // _scales[ 5 ] = 0.0498175;

    _scales[ 0 ] = ( 1.0 + 1. * sqrt10 + 1. * constA ) / 16.; // h0 = 0.47046720778416373
    _scales[ 1 ] = ( 5.0 + 1. * sqrt10 + 3. * constA ) / 16.; // h1 = 1.1411169158314438
    _scales[ 2 ] = ( 10. - 2. * sqrt10 + 2. * constA ) / 16.; // h2 = 0.6503650005262325
    _scales[ 3 ] = ( 10. - 2. * sqrt10 - 2. * constA ) / 16.; // h3 = -0.1909344155683274
    _scales[ 4 ] = ( 5.0 + 1. * sqrt10 - 3. * constA ) / 16.; // h4 = -0.1208322083103962
    _scales[ 5 ] = ( 1.0 + 1. * sqrt10 - 1. * constA ) / 16.; // h5 = 0.049817499736883764

    // normalize orthogonal space => orthonormal space!!!  
    for( int i = 0; i < _motherWavelength; i++ )
      _scales[ i ] /= sqrt02;

    _coeffs = new double[ _motherWavelength ]; // can be done in static way also; faster?

    _coeffs[ 0 ] = _scales[ 5 ]; //    h5
    _coeffs[ 1 ] = -_scales[ 4 ]; //  -h4
    _coeffs[ 2 ] = _scales[ 3 ]; //    h3
    _coeffs[ 3 ] = -_scales[ 2 ]; //  -h2
    _coeffs[ 4 ] = _scales[ 1 ]; //    h1
    _coeffs[ 5 ] = -_scales[ 0 ]; //  -h0

  } // Daubechies03

} // class
