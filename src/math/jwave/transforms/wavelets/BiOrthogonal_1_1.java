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
 * BiOrthogonal Wavelet of type 1.3 - One vanishing moment in wavelet function
 * and one vanishing moment in scaling function
 * 
 * @author Christian Scheiblich
 * @date 16.02.2014 10:15:11
 */
public class BiOrthogonal_1_1 extends Wavelet {

  /**
   * Already orthonormal coefficients taken from Filip Wasilewski's webpage
   * http://wavelets.pybytes.com/wavelet/bior1.1/ Thanks!
   * 
   * @author Christian Scheiblich
   * @date 16.02.2014 10:15:11
   */
  public BiOrthogonal_1_1( ) {

    _transformWavelength = 2; // minimal wavelength of input signal

    _motherWavelength = 2; // wavelength of mother wavelet

    _scalingDeCom = new double[ _motherWavelength ];
    _scalingDeCom[ 0 ] = 0.7071067811865476;
    _scalingDeCom[ 1 ] = 0.7071067811865476;

    _waveletDeCom = new double[ _motherWavelength ];
    _waveletDeCom[ 0 ] = -0.7071067811865476;
    _waveletDeCom[ 1 ] = 0.7071067811865476;

    _scalingReCon = new double[ _motherWavelength ];
    _scalingReCon[ 0 ] = 0.7071067811865476;
    _scalingReCon[ 1 ] = 0.7071067811865476;

    _waveletReCon = new double[ _motherWavelength ];
    _waveletReCon[ 0 ] = 0.7071067811865476;
    _waveletReCon[ 1 ] = -0.7071067811865476;

  } // BiOrthogonal_1_1

} // BiOrthogonal_1_1