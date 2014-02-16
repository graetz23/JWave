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
 * The Battle 23 Wavelet from Mallat's book: "A Theory for Multiresolution
 * Signal Decomposition: The Wavelet Representation", IEEE PAMI, v. 11, no. 7,
 * 674-693, Table 1
 * 
 * @author Christian Scheiblich
 * @date 15.02.2014 23:19:07
 */
@Deprecated
public class Battle23 extends Wavelet {

  /**
   * @author Christian Scheiblich
   * @date 15.02.2014 23:23:23
   */
  public Battle23( ) {

    _transformWavelength = 8; // minimal wavelength of input signal
    
    _motherWavelength = 23; // wavelength of mother wavelet
    
    _scales = new double[ _motherWavelength ];
    _scales[ 0 ] =  -0.002;
    _scales[ 1 ] =  -0.003;
    _scales[ 2 ] =   0.006;
    _scales[ 3 ] =   0.006;
    _scales[ 4 ] =  -0.013;
    _scales[ 5 ] =  -0.012;
    _scales[ 6 ] =   0.030;
    _scales[ 7 ] =   0.023;
    _scales[ 8 ] =  -0.078;
    _scales[ 9 ] =  -0.035;
    _scales[ 10 ] =  0.307;
    _scales[ 11 ] =  0.542;
    _scales[ 12 ] =  0.307;
    _scales[ 13 ] = -0.035;
    _scales[ 14 ] = -0.078;
    _scales[ 15 ] =  0.023;
    _scales[ 16 ] =  0.030;
    _scales[ 17 ] = -0.012;
    _scales[ 18 ] = -0.013;
    _scales[ 19 ] =  0.006;
    _scales[ 20 ] =  0.006;
    _scales[ 21 ] = -0.003;
    _scales[ 22 ] = -0.002;
    
    // building wavelet as orthogonal (orthonormal) space from
    // scaling coefficients. Have a look into Alfred Haar's
    // wavelet for understanding what is done. ;-)
    _coeffs = new double[ _motherWavelength ];
    for( int i = 0; i < _motherWavelength; i++ )
      if( i % 2 == 0 )
        _coeffs[ i ] = _scales[ ( _motherWavelength - 1 ) - i ];
      else
        _coeffs[ i ] = -_scales[ ( _motherWavelength - 1 ) - i ];
    
  } // Battle23

} // Battle23
