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
 * Legendre's orthonormal wavelet of four coefficients and the scales; normed,
 * due to ||*||2 - euclidean norm.
 * 
 * @date 03.06.2010 21:19:04
 * @author Christian Scheiblich
 */
public class Legendre02 extends Wavelet {

  /**
   * Constructor setting up the orthonormal Legendre4 wavelet coeffs and the
   * scales; normed, due to ||*||2 - euclidean norm.
   * 
   * @date 03.06.2010 21:19:04
   * @author Christian Scheiblich
   */
  public Legendre02( ) {

    _transformWavelength = 4; // minimal wavelength of input signal - TODO: test 2 ! 

    _motherWavelength = 4; // wavelength of mother wavelet
    
    _scales = new double[ _motherWavelength ]; // can be done in static way also; faster?
    _scales[ 0 ] = -5. / 8.; // h0
    _scales[ 1 ] = -3. / 8.; // h1
    _scales[ 2 ] = -3. / 8.; // h2
    _scales[ 3 ] = -5. / 8.; // h3
    
    // normalize orthogonal space => orthonormal space!!!  
    double sqrt02 = 1.4142135623730951; // Math.sqrt( 2. )    
    for( int i = 0; i < _motherWavelength; i++ )
      _scales[ i ] /= sqrt02;
    
    _coeffs = new double[ _motherWavelength ]; // can be done in static way also; faster?
    _coeffs[ 0 ] = _scales[ 3 ]; //    h3
    _coeffs[ 1 ] = -_scales[ 2 ]; //  -h2
    _coeffs[ 2 ] = _scales[ 1 ]; //    h1
    _coeffs[ 3 ] = -_scales[ 0 ]; //  -h0

  } // Legendre02

} // class
