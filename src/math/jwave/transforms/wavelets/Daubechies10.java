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
   * @author Christian Scheiblich
   * @date 16.02.2014 00:41:08
   */
  public Daubechies10( ) {

    _transformWavelength = 2; // minimal wavelength of input signal

    _motherWavelength = 20; // wavelength of mother wavelet

    // taken from http://de.wikipedia.org/wiki/Daubechies-Wavelets
    _scales = new double[ _motherWavelength ];
    _scales[ 0 ] =  0.03771716;
    _scales[ 1 ] =  0.26612218;
    _scales[ 2 ] =  0.74557507;
    _scales[ 3 ] =  0.97362811;
    _scales[ 4 ] =  0.39763774;
    _scales[ 5 ] = -0.35333620;
    _scales[ 6 ] = -0.27710988;
    _scales[ 7 ] =  0.18012745;
    _scales[ 8 ] =  0.13160299;
    _scales[ 9 ] = -0.10096657;
    _scales[ 10 ] = -0.04165925;
    _scales[ 11 ] =  0.04696981;
    _scales[ 12 ] =  5.10043697e-3;
    _scales[ 13 ] = -0.01517900;
    _scales[ 14 ] =  1.97332536e-3;
    _scales[ 15 ] =  2.81768659e-3;
    _scales[ 16 ] = -9.69947840e-4;
    _scales[ 17 ] = -1.64709006e-4;
    _scales[ 18 ] =  1.32354367e-4;
    _scales[ 19 ] = -1.875841e-5;

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
    
  } // Daubechies10

} // Daubechies10
