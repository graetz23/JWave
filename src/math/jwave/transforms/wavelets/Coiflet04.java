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
 * Constructor setting up the orthonormal Coiflet wavelet of 24 coefficients and
 * the scales; normed, due to ||*||2 - euclidean norm.
 * 
 * @author Christian Scheiblich
 * @date 16.02.2014 01:46:10
 */
public class Coiflet04 extends Wavelet {

  /**
   * Already orthonormal coefficients taken from Filip Wasilewski's webpage
   * http://wavelets.pybytes.com/wavelet/coif4/ Thanks!
   * 
   * @author Christian Scheiblich
   * @date 16.02.2014 01:46:11
   */
  public Coiflet04( ) {

    _transformWavelength = 2; // minimal wavelength of input signal

    _motherWavelength = 24; // wavelength of mother wavelet

    _scales = new double[ _motherWavelength ];
    _scales[ 0 ] = -1.7849850030882614e-06;
    _scales[ 1 ] = -3.2596802368833675e-06;
    _scales[ 2 ] = 3.1229875865345646e-05;
    _scales[ 3 ] = 6.233903446100713e-05;
    _scales[ 4 ] = -0.00025997455248771324;
    _scales[ 5 ] = -0.0005890207562443383;
    _scales[ 6 ] = 0.0012665619292989445;
    _scales[ 7 ] = 0.003751436157278457;
    _scales[ 8 ] = -0.00565828668661072;
    _scales[ 9 ] = -0.015211731527946259;
    _scales[ 10 ] = 0.025082261844864097;
    _scales[ 11 ] = 0.03933442712333749;
    _scales[ 12 ] = -0.09622044203398798;
    _scales[ 13 ] = -0.06662747426342504;
    _scales[ 14 ] = 0.4343860564914685;
    _scales[ 15 ] = 0.782238930920499;
    _scales[ 16 ] = 0.41530840703043026;
    _scales[ 17 ] = -0.05607731331675481;
    _scales[ 18 ] = -0.08126669968087875;
    _scales[ 19 ] = 0.026682300156053072;
    _scales[ 20 ] = 0.016068943964776348;
    _scales[ 21 ] = -0.0073461663276420935;
    _scales[ 22 ] = -0.0016294920126017326;
    _scales[ 23 ] = 0.0008923136685823146;

    // building wavelet as orthogonal (orthonormal) space from
    // scaling coefficients. Have a look into Alfred Haar's
    // wavelet for understanding what is done. ;-)
    _coeffs = new double[ _motherWavelength ];
    for( int i = 0; i < _motherWavelength; i++ )
      if( i % 2 == 0 )
        _coeffs[ i ] = _scales[ ( _motherWavelength - 1 ) - i ];
      else
        _coeffs[ i ] = -_scales[ ( _motherWavelength - 1 ) - i ];

  } // Coiflet04

} // Coiflet04
