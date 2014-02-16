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
 * Constructor setting up the orthonormal Coiflet wavelet of 30 coefficients and
 * the scales; normed, due to ||*||2 - euclidean norm.
 * 
 * @author Christian Scheiblich
 * @date 16.02.2014 01:49:39
 */
public class Coiflet05 extends Wavelet {

  /**
   * Already orthonormal coefficients taken from Filip Wasilewski's webpage
   * http://wavelets.pybytes.com/wavelet/coif5/ Thanks!
   * 
   * @author Christian Scheiblich
   * @date 16.02.2014 01:49:39
   */
  public Coiflet05( ) {

    _transformWavelength = 2; // minimal wavelength of input signal

    _motherWavelength = 30; // wavelength of mother wavelet

    _scales = new double[ _motherWavelength ];
    _scales[ 0 ] = -9.517657273819165e-08;
    _scales[ 1 ] = -1.6744288576823017e-07;
    _scales[ 2 ] = 2.0637618513646814e-06;
    _scales[ 3 ] = 3.7346551751414047e-06;
    _scales[ 4 ] = -2.1315026809955787e-05;
    _scales[ 5 ] = -4.134043227251251e-05;
    _scales[ 6 ] = 0.00014054114970203437;
    _scales[ 7 ] = 0.00030225958181306315;
    _scales[ 8 ] = -0.0006381313430451114;
    _scales[ 9 ] = -0.0016628637020130838;
    _scales[ 10 ] = 0.0024333732126576722;
    _scales[ 11 ] = 0.006764185448053083;
    _scales[ 12 ] = -0.009164231162481846;
    _scales[ 13 ] = -0.01976177894257264;
    _scales[ 14 ] = 0.03268357426711183;
    _scales[ 15 ] = 0.0412892087501817;
    _scales[ 16 ] = -0.10557420870333893;
    _scales[ 17 ] = -0.06203596396290357;
    _scales[ 18 ] = 0.4379916261718371;
    _scales[ 19 ] = 0.7742896036529562;
    _scales[ 20 ] = 0.4215662066908515;
    _scales[ 21 ] = -0.05204316317624377;
    _scales[ 22 ] = -0.09192001055969624;
    _scales[ 23 ] = 0.02816802897093635;
    _scales[ 24 ] = 0.023408156785839195;
    _scales[ 25 ] = -0.010131117519849788;
    _scales[ 26 ] = -0.004159358781386048;
    _scales[ 27 ] = 0.0021782363581090178;
    _scales[ 28 ] = 0.00035858968789573785;
    _scales[ 29 ] = -0.00021208083980379827;

    // building wavelet as orthogonal (orthonormal) space from
    // scaling coefficients. Have a look into Alfred Haar's
    // wavelet for understanding what is done. ;-)
    _coeffs = new double[ _motherWavelength ];
    for( int i = 0; i < _motherWavelength; i++ )
      if( i % 2 == 0 )
        _coeffs[ i ] = _scales[ ( _motherWavelength - 1 ) - i ];
      else
        _coeffs[ i ] = -_scales[ ( _motherWavelength - 1 ) - i ];

  } // Coiflet05

} // Coiflet05
