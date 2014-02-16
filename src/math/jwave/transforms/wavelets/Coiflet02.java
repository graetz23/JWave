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
public class Coiflet02 extends Wavelet {

  /**
   * Already orthonormal coefficients taken from Filip Wasilewski's webpage
   * http://wavelets.pybytes.com/wavelet/coif2/ Thanks!
   * 
   * @author Christian Scheiblich
   * @date 15.02.2014 22:33:55
   */
  public Coiflet02( ) {

    _transformWavelength = 2; // minimal wavelength of input signal

    _motherWavelength = 12; // wavelength of mother wavelet

    _scales = new double[ _motherWavelength ];
    _scales[ 0 ] = -0.0007205494453645122;
    _scales[ 1 ] = -0.0018232088707029932;
    _scales[ 2 ] = 0.0056114348193944995;
    _scales[ 3 ] = 0.023680171946334084;
    _scales[ 4 ] = -0.0594344186464569;
    _scales[ 5 ] = -0.0764885990783064;
    _scales[ 6 ] = 0.41700518442169254;
    _scales[ 7 ] = 0.8127236354455423;
    _scales[ 8 ] = 0.3861100668211622;
    _scales[ 9 ] = -0.06737255472196302;
    _scales[ 10 ] = -0.04146493678175915;
    _scales[ 11 ] = 0.016387336463522112;

    // building wavelet as orthogonal (orthonormal) space from
    // scaling coefficients. Have a look into Alfred Haar's
    // wavelet for understanding what is done. ;-)
    _coeffs = new double[ _motherWavelength ];
    for( int i = 0; i < _motherWavelength; i++ )
      if( i % 2 == 0 )
        _coeffs[ i ] = _scales[ ( _motherWavelength - 1 ) - i ];
      else
        _coeffs[ i ] = -_scales[ ( _motherWavelength - 1 ) - i ];

  } // Coiflet02

} // Coiflet02
