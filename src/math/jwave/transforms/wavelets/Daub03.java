/**
 * JWave - Java implementation of wavelet transform algorithms
 *
 * Copyright 2009-2014 Christian Scheiblich
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
 * This file Daub03.java is part of JWave.
 *
 * @author Christian Scheiblich
 * date 25.03.2010 14:03:20
 * contact cscheiblich@gmail.com
 */
package math.jwave.transforms.wavelets;

/**
 * Ingrid Daubechies' orthonormal wavelet of six coefficients and the scales;
 * normed, due to ||*||2 - euclidean norm.
 * 
 * @date 25.03.2010 14:03:20
 * @author Christian Scheiblich
 */
@Deprecated
public class Daub03 extends Wavelet {
  
  /**
   * Constructor setting up the orthonormal Daubechie6 wavelet coeffs and the
   * scales; normed, due to ||*||2 - euclidean norm.
   * 
   * @date 25.03.2010 14:03:20
   * @author Christian Scheiblich
   */
  public Daub03( ) {
    
    _waveLength = 6;
    
    double sqrt02 = 1.4142135623730951; // Math.sqrt( 2. )
    double sqrt10 = Math.sqrt( 10. );
    double constA = Math.sqrt( 5. + 2. * sqrt10 );
    
    _scales = new double[ _waveLength ]; // can be done in static way also; faster?
    
    _scales[ 0 ] = ( 1. + 1. * sqrt10 + 1. * constA ) / 16. / sqrt02; // h0
    _scales[ 1 ] = ( 5. + 1. * sqrt10 + 3. * constA ) / 16. / sqrt02; // h1
    _scales[ 2 ] = ( 10. - 2. * sqrt10 + 2. * constA ) / 16. / sqrt02; // h2
    _scales[ 3 ] = ( 10. - 2. * sqrt10 - 2. * constA ) / 16. / sqrt02; // h3
    _scales[ 4 ] = ( 5. + 1. * sqrt10 - 3. * constA ) / 16. / sqrt02; // h4
    _scales[ 5 ] = ( 1. + 1. * sqrt10 - 1. * constA ) / 16. / sqrt02; // h5
    
    _coeffs = new double[ _waveLength ]; // can be done in static way also; faster?
    
    _coeffs[ 0 ] = _scales[ 5 ]; //    h5
    _coeffs[ 1 ] = -_scales[ 4 ]; //  -h4
    _coeffs[ 2 ] = _scales[ 3 ]; //    h3
    _coeffs[ 3 ] = -_scales[ 2 ]; //  -h2
    _coeffs[ 4 ] = _scales[ 1 ]; //    h1
    _coeffs[ 5 ] = -_scales[ 0 ]; //  -h0
    
  } // Daub03
  
} // class
