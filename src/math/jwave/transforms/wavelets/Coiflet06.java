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
 * Constructor setting up the orthonormal Coiflet wavelet of 12 coefficients and
 * the scales; normed, due to ||*||2 - euclidean norm.
 * 
 * @author Christian Scheiblich
 * @date 15.02.2014 22:33:55
 */
public class Coiflet06 extends Wavelet {

  /**
   * @author Christian Scheiblich
   * @date 15.02.2014 22:33:55
   */
  public Coiflet06( ) {

    _transformWavelength = 2; // minimal wavelength of input signal

    _motherWavelength = 12; // wavelength of mother wavelet

    // these coefficients are already orthonormal
    _scales = new double[ _motherWavelength ];
    _scales[ 0 ] = 0.0011945726958388; // h0
    _scales[ 1 ] = -0.01284557955324; // h1
    _scales[ 2 ] = 0.024804330519353; // h2
    _scales[ 3 ] = 0.050023519962135; // h3
    _scales[ 4 ] = -0.15535722285996; // h4
    _scales[ 5 ] = -0.071638282295294; // h5
    _scales[ 6 ] = 0.57046500145033; // h6
    _scales[ 7 ] = 0.75033630585287; // h7
    _scales[ 8 ] = 0.28061165190244; // h8
    _scales[ 9 ] = -0.0074103835186718; // h9
    _scales[ 10 ] = -0.014611552521451; // h19
    _scales[ 11 ] = -0.0013587990591632; // h11
    
    // building wavelet as orthogonal (orthonormal) space from
    // scaling coefficients. Have a look into Alfred Haar's
    // wavelet for understanding what is done. ;-)
    _coeffs = new double[ _motherWavelength ];
    for( int i = 0; i < _motherWavelength; i++ ) 
      if( i % 2 == 0 )
        _coeffs[ i ] = _scales[ ( _motherWavelength - 1 ) - i ];
      else
        _coeffs[ i ] = -_scales[ ( _motherWavelength - 1 ) - i ];

  } // Coiflet06

} // Coiflet06
