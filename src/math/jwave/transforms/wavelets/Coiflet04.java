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
 * @date 23.05.2008 17:42:23
 * cscheiblich@gmail.com
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

    _scalingDeCom = new double[ _motherWavelength ];
    _scalingDeCom[ 0 ] = -1.7849850030882614e-06;
    _scalingDeCom[ 1 ] = -3.2596802368833675e-06;
    _scalingDeCom[ 2 ] = 3.1229875865345646e-05;
    _scalingDeCom[ 3 ] = 6.233903446100713e-05;
    _scalingDeCom[ 4 ] = -0.00025997455248771324;
    _scalingDeCom[ 5 ] = -0.0005890207562443383;
    _scalingDeCom[ 6 ] = 0.0012665619292989445;
    _scalingDeCom[ 7 ] = 0.003751436157278457;
    _scalingDeCom[ 8 ] = -0.00565828668661072;
    _scalingDeCom[ 9 ] = -0.015211731527946259;
    _scalingDeCom[ 10 ] = 0.025082261844864097;
    _scalingDeCom[ 11 ] = 0.03933442712333749;
    _scalingDeCom[ 12 ] = -0.09622044203398798;
    _scalingDeCom[ 13 ] = -0.06662747426342504;
    _scalingDeCom[ 14 ] = 0.4343860564914685;
    _scalingDeCom[ 15 ] = 0.782238930920499;
    _scalingDeCom[ 16 ] = 0.41530840703043026;
    _scalingDeCom[ 17 ] = -0.05607731331675481;
    _scalingDeCom[ 18 ] = -0.08126669968087875;
    _scalingDeCom[ 19 ] = 0.026682300156053072;
    _scalingDeCom[ 20 ] = 0.016068943964776348;
    _scalingDeCom[ 21 ] = -0.0073461663276420935;
    _scalingDeCom[ 22 ] = -0.0016294920126017326;
    _scalingDeCom[ 23 ] = 0.0008923136685823146;

    // building wavelet as orthogonal (orthonormal) space from
    // scaling coefficients (low pass filter). Have a look into
    // Alfred Haar's wavelet or the Daubechie Wavelet with 2
    // vanishing moments for understanding what is done here. ;-)
    _waveletDeCom = new double[ _motherWavelength ];
    for( int i = 0; i < _motherWavelength; i++ )
      if( i % 2 == 0 )
        _waveletDeCom[ i ] = _scalingDeCom[ ( _motherWavelength - 1 ) - i ];
      else
        _waveletDeCom[ i ] = -_scalingDeCom[ ( _motherWavelength - 1 ) - i ];

    // Copy to reconstruction filters due to orthogonality (orthonormality)!
    _scalingReCon = new double[ _motherWavelength ];
    _waveletReCon = new double[ _motherWavelength ];
    for( int i = 0; i < _motherWavelength; i++ ) {

      _scalingReCon[ i ] = _scalingDeCom[ i ];
      _waveletReCon[ i ] = _waveletDeCom[ i ];

    } // i

  } // Coiflet04

} // Coiflet04
