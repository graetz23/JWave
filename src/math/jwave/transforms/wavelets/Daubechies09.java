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
 * Ingrid Daubechies' orthonormal wavelet of 18 coefficients and the scales;
 * normed, due to ||*||2 - euclidean norm.
 * 
 * @author Christian Scheiblich
 * @date 16.02.2014 00:34:30
 */
@Deprecated public class Daubechies09 extends Wavelet {

  /**
   * TODO: Recheck the values, due to those are not working fine in rounding
   * test doing 1000 and forward and reverse Fast Wavelet Transforms!
   * 
   * @author Christian Scheiblich
   * @date 16.02.2014 00:34:30
   */
  public Daubechies09( ) {

    _transformWavelength = 2; // minimal wavelength of input signal

    _motherWavelength = 18; // wavelength of mother wavelet

    // TODO Get analytical formulation, due to its precision; this is around 1.e-3 only
    // taken from http://de.wikipedia.org/wiki/Daubechies-Wavelets
    //
    _scales = new double[ _motherWavelength ];
    _scales[ 0 ] = 0.05385035;
    _scales[ 1 ] = 0.34483430;
    _scales[ 2 ] = 0.85534906;
    _scales[ 3 ] = 0.92954571;
    _scales[ 4 ] = 0.18836955;
    _scales[ 5 ] = -0.41475176;
    _scales[ 6 ] = -0.13695355;
    _scales[ 7 ] = 0.21006834;
    _scales[ 8 ] = 0.04345268;
    _scales[ 9 ] = -0.09564726;
    _scales[ 10 ] = 3.54892813e-4;
    _scales[ 11 ] = 0.03162417;
    _scales[ 12 ] = -6.67962023e-4;
    _scales[ 13 ] = -6.05496058e-3;
    _scales[ 14 ] = 2.61296728e-3;
    _scales[ 15 ] = 3.25814671e-4;
    _scales[ 16 ] = -3.56329759e-4;
    _scales[ 17 ] = -5.5645514e-5;

    // normalize orthogonal space => orthonormal space!!!  
    double sqrt02 = 1.4142135623730951;
    for( int i = 0; i < _motherWavelength; i++ )
      _scales[ i ] /= sqrt02;

    // building wavelet as orthogonal (orthonormal) space from
    // scaling coefficients. Have a look into Alfred Haar's
    // wavelet for understanding what is done. ;-)
    _coeffs = new double[ _motherWavelength ];
    for( int i = 0; i < _motherWavelength; i++ )
      if( i % 2 == 0 )
        _coeffs[ i ] = _scales[ ( _motherWavelength - 1 ) - i ];
      else
        _coeffs[ i ] = -_scales[ ( _motherWavelength - 1 ) - i ];

  } // Daubechies09

} // Daubechies09
