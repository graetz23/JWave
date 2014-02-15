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
 * Constructor setting up the orthonormal Coiflet wavelet of 18 coefficients and
 * the scales; normed, due to ||*||2 - euclidean norm.
 * 
 * @author Christian Scheiblich
 * @date 15.02.2014 22:58:59
 */
public class Coiflet09 extends Wavelet {

  /**
   * @author Christian Scheiblich
   * @date 15.02.2014 22:58:59
   */
  public Coiflet09( ) {

    _transformWavelength = 2; // minimal wavelength of input signal

    _motherWavelength = 18; // wavelength of mother wavelet

    // these coefficients are already orthonormal
    _scales = new double[ _motherWavelength ];
    _scales[ 0 ] = -0.0016918510194918; // h0
    _scales[ 1 ] = -0.00348787621998426; // h1
    _scales[ 2 ] = 0.019191160680044; // h2
    _scales[ 3 ] = 0.021671094636352; // h3
    _scales[ 4 ] = -0.098507213321468; // h4
    _scales[ 5 ] = -0.056997424478478; // h5
    _scales[ 6 ] = 0.45678712217269; // h6
    _scales[ 7 ] = 0.78931940900416; // h7
    _scales[ 8 ] = 0.38055713085151; // h8
    _scales[ 9 ] = -0.070438748794943; // h09
    _scales[ 10 ] = -0.056514193868065; // h10
    _scales[ 11 ] = 0.036409962612716; // h11
    _scales[ 12 ] = 0.0087601307091635; // h12
    _scales[ 13 ] = -0.011194759273835; // h13
    _scales[ 14 ] = -0.0019213354141368; // h14
    _scales[ 15 ] = 0.0020413809772660; // h15
    _scales[ 16 ] = 0.00044583039753204; // h16
    _scales[ 17 ] = -0.00021625727664696; // h17

    // building wavelet as orthogonal (orthonormal) space from
    // scaling coefficients. Have a look into Alfred Haar's
    // wavelet for understanding what is done. ;-)
    _coeffs = new double[ _motherWavelength ];
    for( int i = 0; i < _motherWavelength; i++ )
      if( i % 2 == 0 )
        _coeffs[ i ] = _scales[ ( _motherWavelength - 1 ) - i ];
      else
        _coeffs[ i ] = -_scales[ ( _motherWavelength - 1 ) - i ];

  } // Coiflet09

} // Coiflet09