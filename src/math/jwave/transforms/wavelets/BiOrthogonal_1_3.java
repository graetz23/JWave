/**
 * JWave - Java implementation of wavelet transform algorithms
 *
 * Copyright 2008-2014 Christian Scheiblich (cscheiblich@gmail.com)
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
 * @author Christian Scheiblich (cscheiblich@gmail.com)
 * @date 23.05.2008 17:42:23
 *
 */
package math.jwave.transforms.wavelets;

/**
 * BiOrthogonal Wavelet of type 1.3 - One vanishing moment in wavelet function
 * and three vanishing moments in scaling function.
 * 
 * @author Christian Scheiblich (cscheiblich@gmail.com)
 * @date 16.02.2014 10:31:33
 */
public class BiOrthogonal_1_3 extends Wavelet {

  /**
   * Already orthonormal coefficients taken from Filip Wasilewski's webpage
   * http://wavelets.pybytes.com/wavelet/bior1.3/ Thanks!
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 16.02.2014 10:31:33
   */
  public BiOrthogonal_1_3( ) {

    _transformWavelength = 2; // minimal wavelength of input signal

    _motherWavelength = 6; // wavelength of mother wavelet

    _scalingDeCom = new double[ _motherWavelength ];
    _scalingDeCom[ 0 ] = -0.08838834764831845;
    _scalingDeCom[ 1 ] = 0.08838834764831845;
    _scalingDeCom[ 2 ] = 0.7071067811865476;
    _scalingDeCom[ 3 ] = 0.7071067811865476;
    _scalingDeCom[ 4 ] = 0.08838834764831845;
    _scalingDeCom[ 5 ] = -0.08838834764831845;

    _waveletDeCom = new double[ _motherWavelength ];
    _waveletDeCom[ 0 ] = 0.;
    _waveletDeCom[ 1 ] = 0.;
    _waveletDeCom[ 2 ] = -0.7071067811865476;
    _waveletDeCom[ 3 ] = 0.7071067811865476;
    _waveletDeCom[ 4 ] = 0.;
    _waveletDeCom[ 5 ] = 0.;

    _scalingReCon = new double[ _motherWavelength ];
    _scalingReCon[ 0 ] = 0.;
    _scalingReCon[ 1 ] = 0.;
    _scalingReCon[ 2 ] = 0.7071067811865476;
    _scalingReCon[ 3 ] = 0.7071067811865476;
    _scalingReCon[ 4 ] = 0.;
    _scalingReCon[ 5 ] = 0.;

    _waveletReCon = new double[ _motherWavelength ];
    _waveletReCon[ 0 ] = -0.08838834764831845;
    _waveletReCon[ 1 ] = -0.08838834764831845;
    _waveletReCon[ 2 ] = 0.7071067811865476;
    _waveletReCon[ 3 ] = -0.7071067811865476;
    _waveletReCon[ 4 ] = 0.08838834764831845;
    _waveletReCon[ 5 ] = 0.08838834764831845;

  } // BiOrthogonal_1_3

} // BiOrthogonal_1_3