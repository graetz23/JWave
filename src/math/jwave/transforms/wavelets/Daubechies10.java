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
 * Ingrid Daubechies' orthonormal wavelet of 20 coefficients and the scales;
 * normed, due to ||*||2 - euclidean norm.
 * 
 * @author Christian Scheiblich
 * @date 16.02.2014 00:41:08
 */
public class Daubechies10 extends Wavelet {

  /**
   * Already orthonormal coefficients taken from Filip Wasilewski's webpage
   * http://wavelets.pybytes.com/wavelet/db10/ Thanks!
   * 
   * @author Christian Scheiblich
   * @date 16.02.2014 00:41:08
   */
  public Daubechies10( ) {

    _transformWavelength = 2; // minimal wavelength of input signal

    _motherWavelength = 20; // wavelength of mother wavelet

    _scales = new double[ _motherWavelength ];
    _scales[ 0 ] = -1.326420300235487e-05;
    _scales[ 1 ] = -9.358867000108985e-05;
    _scales[ 2 ] = -0.0001164668549943862;
    _scales[ 3 ] = 0.0006858566950046825;
    _scales[ 4 ] = 0.00199240529499085;
    _scales[ 5 ] = -0.0013953517469940798;
    _scales[ 6 ] = -0.010733175482979604;
    _scales[ 7 ] = -0.0036065535669883944;
    _scales[ 8 ] = 0.03321267405893324;
    _scales[ 9 ] = 0.02945753682194567;
    _scales[ 10 ] = -0.07139414716586077;
    _scales[ 11 ] = -0.09305736460380659;
    _scales[ 12 ] = 0.12736934033574265;
    _scales[ 13 ] = 0.19594627437659665;
    _scales[ 14 ] = -0.24984642432648865;
    _scales[ 15 ] = -0.2811723436604265;
    _scales[ 16 ] = 0.6884590394525921;
    _scales[ 17 ] = -0.5272011889309198;
    _scales[ 18 ] = 0.18817680007762133;
    _scales[ 19 ] = -0.026670057900950818;

    // building wavelet as orthogonal (orthonormal) space from
    // scaling coefficients. Have a look into Alfred Haar's
    // wavelet for understanding what is done. ;-)
    _coeffs = new double[ _motherWavelength ];
    for( int i = 0; i < _motherWavelength; i++ )
      if( i % 2 == 0 )
        _coeffs[ i ] = _scales[ ( _motherWavelength - 1 ) - i ];
      else
        _coeffs[ i ] = -_scales[ ( _motherWavelength - 1 ) - i ];

  } // Daubechies10

} // Daubechies10
