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
 * Ingrid Daubechies' orthonormal wavelet of eight coefficients and the scales;
 * normed, due to ||*||2 - euclidean norm.
 * 
 * @date 26.03.2010 07:35:31
 * @author Christian Scheiblich
 */
@Deprecated
public class Daubechie04 extends Wavelet {
  
  /**
   * Constructor setting up the orthonormal Daubechie6 wavelet coeffs and the
   * scales; normed, due to ||*||2 - euclidean norm.
   * 
   * @date 26.03.2010 07:35:31
   * @author Christian Scheiblich
   */
  @Deprecated
  public Daubechie04( ) {
    
    _transformWavelength = 2; // minimal wavelength of input signal
    
    _motherWavelength = 8; // wavelength of mother wavelet
    
    _scales = new double[ _motherWavelength ];
    
    double sqrt02 = 1.4142135623730951;
    
    // TODO Get analytical formulation, due to its precision; this is around 1.e-3 only
    // taken from http://de.wikipedia.org/wiki/Daubechies-Wavelets
    //
    _scales[ 0 ] = 0.32580343; //  0.32580343
    _scales[ 1 ] = 1.0109458; //  1.01094572
    _scales[ 2 ] = 0.8922014; //  0.8922014
    _scales[ 3 ] = -0.0396750; // -0.03967503
    _scales[ 4 ] = -0.2645071; // -0.2645071
    _scales[ 5 ] = 0.0436163; //  0.0436163
    _scales[ 6 ] = 0.0465036; //  0.0465036
    _scales[ 7 ] = -0.01498699; // -0.01498699
    
    // normalize orthogonal space => orthonormal space!!!  
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
    
  } // Daubechie04
  
} // class
