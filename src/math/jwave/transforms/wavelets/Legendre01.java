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
 * This file Legendre01.java is part of JWave.
 *
 * @author itechsch
 * date 08.06.2010 09:32:08
 * contact cscheiblich@gmail.com
 */
package math.jwave.transforms.wavelets;

/**
 * Orthonormal Legendre wavelet transform of 2 coefficients based on the
 * Legendre polynomial. But, actually for the smallest Legendre wavelet, the
 * wavelet is the mirrored Haar Wavelet.
 * 
 * @date 08.06.2010 09:32:08
 * @author Christian Scheiblich
 */
public class Legendre01 extends Wavelet {
  
  /**
   * Constructor setting up the orthonormal Legendre 2 wavelet coeffs and the
   * scales; normed, due to ||*||_2 -- euclidean norm. Actually these
   * coefficients are the mirrored ones of Alfred Haar's wavelet -- see class
   * Haar01 and Haar01Orthogonal.
   * 
   * @date 08.06.2010 09:32:08
   * @author Christian Scheiblich
   */
  public Legendre01( ) {
    
    _waveLength = 2;
    
    _coeffs = new double[ _waveLength ];
    
    _coeffs[ 0 ] = -1. / 1.4142135623730951; // w0 - normed by sqrt( 2 )
    _coeffs[ 1 ] = 1. / 1.4142135623730951; // w1 - normed by sqrt( 2 )
    
    _scales = new double[ _waveLength ];
    
    _scales[ 0 ] = -_coeffs[ 1 ]; // -w1 -> -1. / sqrt(2.)
    _scales[ 1 ] = _coeffs[ 0 ]; // w0   -> -1. / sqrt(2.)
    
  } // Legendre01
  
} // class
