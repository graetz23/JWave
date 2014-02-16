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
package math.jwave.transforms;

import math.jwave.exceptions.JWaveException;
import math.jwave.exceptions.JWaveFailure;
import math.jwave.transforms.wavelets.Wavelet;

/**
 * Base class for the forward and reverse Fast Wavelet Transform in 1-D, 2-D,
 * and 3-D using a specified Wavelet by inheriting class.
 * 
 * @date 10.02.2010 08:10:42
 * @author Christian Scheiblich (cscheiblich@gmail.com)
 */
/**
 * @author tucker 05.02.2014 22:12:45
 */
public class FastWaveletTransform extends WaveletTransform {

  /**
   * Constructor receiving a Wavelet object.
   * 
   * @date 10.02.2010 08:10:42
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @param wavelet
   *          object of type Wavelet; Haar1, Daubechies2, Coiflet1, ...
   * @throws JWaveFailure
   *           if object is null or not of type wavelet
   * @throws JWaveException
   */
  public FastWaveletTransform( Wavelet wavelet ) throws JWaveFailure {

    super( wavelet );

  } // FastWaveletTransform

  /**
   * Performs the 1-D forward transform for arrays of dim N from time domain to
   * Hilbert domain for the given array using the Fast Wavelet Transform (FWT)
   * algorithm.
   * 
   * @date 10.02.2010 08:23:24
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @see math.jwave.transforms.BasicTransform#forward(double[])
   */
  @Override public double[ ] forward( double[ ] arrTime ) {

    double[ ] arrHilb = new double[ arrTime.length ];
    for( int i = 0; i < arrTime.length; i++ )
      arrHilb[ i ] = arrTime[ i ];

    int h = arrHilb.length;
    int transformWavelength = _wavelet.getTransformWavelength( ); // 2, 4, 8, 16, 32, ...
    if( h >= transformWavelength ) {

      while( h >= transformWavelength ) {

        double[ ] arrTempPart = _wavelet.forward( arrHilb, h );

        for( int i = 0; i < h; i++ )
          arrHilb[ i ] = arrTempPart[ i ];

        h = h >> 1;

      } // levels

    } // if

    return arrHilb;

  } // forward

  /**
   * Performs the 1-D reverse transform for arrays of dim N from Hilbert domain
   * to time domain for the given array using the Fast Wavelet Transform (FWT)
   * algorithm and the selected wavelet.
   * 
   * @date 10.02.2010 08:23:24
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @see math.jwave.transforms.BasicTransform#reverse(double[])
   */
  @Override public double[ ] reverse( double[ ] arrHilb ) {

    double[ ] arrTime = new double[ arrHilb.length ];

    for( int i = 0; i < arrHilb.length; i++ )
      arrTime[ i ] = arrHilb[ i ];

    int transformWavelength = _wavelet.getTransformWavelength( ); // 2, 4, 8, 16, 32, ...

    int h = transformWavelength;

    if( !isBinary( h ) )
      h = h << 1; // 6 -> 8, 10 -> 16

    while( h <= arrTime.length && h >= transformWavelength ) {

      double[ ] arrTempPart = _wavelet.reverse( arrTime, h );

      for( int i = 0; i < h; i++ )
        arrTime[ i ] = arrTempPart[ i ];

      h = h << 1;

    } // levels

    return arrTime;

  }// reverse

} // class
