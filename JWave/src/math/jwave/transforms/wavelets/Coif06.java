/**
 * JWave - Java implementation of wavelet transform algorithms
 *
 * Copyright 2010-2014 Christian Scheiblich
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
 * This file Coif06.java is part of JWave.
 *
 * @author Christian Scheiblich
 * date 23.02.2010 05:42:23
 * contact cscheiblich@gmail.com
 */
package math.jwave.transforms.wavelets;

/**
 * Ingrid Daubechies' orthonormal Coiflet wavelet of six coefficients and the
 * scales; normed, due to ||*||2 - euclidean norm.
 * 
 * @date 10.02.2010 16:32:38
 * @author Christian Scheiblich
 */
@Deprecated
public class Coif06 extends Wavelet {
  
  /**
   * Constructor setting up the orthonormal Coiflet6 wavelet coeffs and the
   * scales; normed, due to ||*||2 - euclidean norm.
   * 
   * @date 10.02.2010 16:32:38
   * @author Christian Scheiblich
   */
  public Coif06( ) {
    
    _waveLength = 6; // minimal array size for transform
    
    double sqrt15 = Math.sqrt( 15. );
    
    _scales = new double[ _waveLength ]; // can be done in static way also; faster?
    
    _scales[ 0 ] = 1.4142135623730951 * ( sqrt15 - 3. ) / 32.;
    _scales[ 1 ] = 1.4142135623730951 * ( 1. - sqrt15 ) / 32.;
    _scales[ 2 ] = 1.4142135623730951 * ( 6. - 2 * sqrt15 ) / 32.;
    _scales[ 3 ] = 1.4142135623730951 * ( 2. * sqrt15 + 6. ) / 32.;
    _scales[ 4 ] = 1.4142135623730951 * ( sqrt15 + 13. ) / 32.;
    _scales[ 5 ] = 1.4142135623730951 * ( 9. - sqrt15 ) / 32.;
    
    _coeffs = new double[ _waveLength ]; // can be done in static way also; faster?
    
    _coeffs[ 0 ] = _scales[ 5 ]; //    h5
    _coeffs[ 1 ] = -_scales[ 4 ]; //  -h4
    _coeffs[ 2 ] = _scales[ 3 ]; //    h3
    _coeffs[ 3 ] = -_scales[ 2 ]; //  -h2
    _coeffs[ 4 ] = _scales[ 1 ]; //    h1
    _coeffs[ 5 ] = -_scales[ 0 ]; //  -h0
    
  } // Coif06
  
} // class
