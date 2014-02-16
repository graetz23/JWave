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
 * Alfred Haar's orthonormal wavelet transform.
 * 
 * @date 08.02.2010 12:46:34
 * @author Christian Scheiblich
 */
public class Haar01 extends Wavelet {

  /**
   * Constructor setting up the orthonormal Haar wavelet coefficients and the
   * scaling coefficients; normed, due to ||*||_2 -- euclidean norm. See the
   * orthogonal version in class Haar01Orthogonal for more details.
   * 
   * @date 08.02.2010 12:46:34
   * @author Christian Scheiblich
   */
  public Haar01( ) {

    _transformWavelength = 2; // minimal wavelength of input signal

    _motherWavelength = 2; // wavelength of mother wavelet

    _coeffs = new double[ _motherWavelength ];

    _coeffs[ 0 ] = 1. / 1.4142135623730951; // w0 - normed by sqrt( 2 )
    _coeffs[ 1 ] = -1. / 1.4142135623730951; // w1 - normed by sqrt( 2 )

    _scales = new double[ _motherWavelength ];

    _scales[ 0 ] = -_coeffs[ 1 ]; // -w1
    _scales[ 1 ] = _coeffs[ 0 ]; // w0

  } // Haar01

  /**
   * The forward wavelet transform using the Alfred Haar's wavelet.
   * 
   * @date 10.02.2010 08:26:06
   * @author Christian Scheiblich
   * @see math.jwave.transforms.wavelets.Wavelet#forward(double[])
   */

  /**
   * The reverse wavelet transform using the Alfred Haar's wavelet. The arrHilb
   * array keeping coefficients of Hilbert domain should be of length 2 to the
   * power of p -- length = 2^p where p is a positive integer.
   * 
   * @date 10.02.2010 08:26:06
   * @author Christian Scheiblich
   * @see math.jwave.transforms.wavelets.Wavelet#reverse(double[])
   */

} // class
