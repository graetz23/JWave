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
 * Ingrid Daubechies' orthonormal Coiflet wavelet of six coefficients and the
 * scales; normed, due to ||*||2 - euclidean norm.
 * 
 * @author Christian Scheiblich
 * @date 15.02.2014 22:27:55
 */
public class Coiflet03 extends Wavelet {

  /**
   * Constructor setting up the orthonormal Coiflet wavelet of 6 coefficients
   * and the scales; normed, due to ||*||2 - euclidean norm.
   * 
   * @author Christian Scheiblich
   * @date 15.02.2014 22:27:55
   */
  public Coiflet03( ) {

    _transformWavelength = 2; // minimal wavelength of input signal

    _motherWavelength = 6; // wavelength of mother wavelet

    double sqrt02 = 1.4142135623730951;
    double sqrt15 = Math.sqrt( 15. );

    // these coefficients are already orthonormal
    _scales = new double[ _motherWavelength ];
    _scales[ 0 ] = sqrt02 * ( sqrt15 - 3. ) / 32.;
    _scales[ 1 ] = sqrt02 * ( 1. - sqrt15 ) / 32.;
    _scales[ 2 ] = sqrt02 * ( 6. - 2 * sqrt15 ) / 32.;
    _scales[ 3 ] = sqrt02 * ( 2. * sqrt15 + 6. ) / 32.;
    _scales[ 4 ] = sqrt02 * ( sqrt15 + 13. ) / 32.;
    _scales[ 5 ] = sqrt02 * ( 9. - sqrt15 ) / 32.;

    _coeffs = new double[ _motherWavelength ];
    _coeffs[ 0 ] = _scales[ 5 ]; //    h5
    _coeffs[ 1 ] = -_scales[ 4 ]; //  -h4
    _coeffs[ 2 ] = _scales[ 3 ]; //    h3
    _coeffs[ 3 ] = -_scales[ 2 ]; //  -h2
    _coeffs[ 4 ] = _scales[ 1 ]; //    h1
    _coeffs[ 5 ] = -_scales[ 0 ]; //  -h0

  } // Coiflet03

} // class
