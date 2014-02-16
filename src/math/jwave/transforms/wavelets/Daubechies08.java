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
 * Ingrid Daubechies' orthonormal wavelet of 16 coefficients and the scales;
 * normed, due to ||*||2 - euclidean norm.
 * 
 * @author Christian Scheiblich
 * @date 16.02.2014 00:30:05
 */
public class Daubechies08 extends Wavelet {

  /**
   * Already orthonormal coefficients taken from Filip Wasilewski's webpage
   * http://wavelets.pybytes.com/wavelet/db8/ Thanks!
   * 
   * @author Christian Scheiblich
   * @date 16.02.2014 00:30:05
   */
  public Daubechies08( ) {

    _transformWavelength = 2; // minimal wavelength of input signal

    _motherWavelength = 16; // wavelength of mother wavelet

    _scalingDeCom = new double[ _motherWavelength ];
    _scalingDeCom[ 0 ] = -0.00011747678400228192;
    _scalingDeCom[ 1 ] = 0.0006754494059985568;
    _scalingDeCom[ 2 ] = -0.0003917403729959771;
    _scalingDeCom[ 3 ] = -0.00487035299301066;
    _scalingDeCom[ 4 ] = 0.008746094047015655;
    _scalingDeCom[ 5 ] = 0.013981027917015516;
    _scalingDeCom[ 6 ] = -0.04408825393106472;
    _scalingDeCom[ 7 ] = -0.01736930100202211;
    _scalingDeCom[ 8 ] = 0.128747426620186;
    _scalingDeCom[ 9 ] = 0.00047248457399797254;
    _scalingDeCom[ 10 ] = -0.2840155429624281;
    _scalingDeCom[ 11 ] = -0.015829105256023893;
    _scalingDeCom[ 12 ] = 0.5853546836548691;
    _scalingDeCom[ 13 ] = 0.6756307362980128;
    _scalingDeCom[ 14 ] = 0.3128715909144659;
    _scalingDeCom[ 15 ] = 0.05441584224308161;

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

  } // Daubechies08

} // Daubechies08
