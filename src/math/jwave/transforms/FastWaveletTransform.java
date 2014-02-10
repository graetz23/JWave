/**
 * JWave - Java implementation of wavelet transform algorithms Copyright
 * 2009-2014 Christian Scheiblich Licensed under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License. This file
 * FastBasicTransform.java is part of JWave.
 * 
 * @author Christian Scheiblich date 23.02.2010 05:42:23 contact
 *         cscheiblich@gmail.com
 */
package math.jwave.transforms;

import math.jwave.exc.JWaveException;
import math.jwave.exc.JWaveFailure;
import math.jwave.transforms.wavelets.Wavelet;

/**
 * Base class for the forward and reverse Fast Wavelet Transform in 1-D, 2-D,
 * and 3-D using a specified Wavelet by inheriting class.
 * 
 * @date 10.02.2010 08:10:42
 * @author Christian Scheiblich
 */
/**
 * @author tucker 05.02.2014 22:12:45
 */
public class FastWaveletTransform extends WaveletTransform {

  /**
   * Constructor receiving a Wavelet object.
   * 
   * @date 10.02.2010 08:10:42
   * @author Christian Scheiblich
   * @param wavelet
   *          object of type Wavelet; Haar01, Daubechie02, Coiflet06, ...
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
   * @author Christian Scheiblich
   * @see math.jwave.transforms.BasicTransform#forward(double[])
   */
  @Override public double[ ] forward( double[ ] arrTime ) {

    double[ ] arrHilb = new double[ arrTime.length ];
    for( int i = 0; i < arrTime.length; i++ )
      arrHilb[ i ] = arrTime[ i ];

    int level = 0;
    int h = arrHilb.length;
    int minWaveLength = _wavelet.getWaveLength( ); // 2, 4, 6, 8, 10, 12, ...
    if( h >= minWaveLength ) {

      while( h >= minWaveLength ) {

        double[ ] iBuf = new double[ h ];

        for( int i = 0; i < h; i++ )
          iBuf[ i ] = arrHilb[ i ];

        double[ ] oBuf = _wavelet.forward( iBuf );

        for( int i = 0; i < h; i++ )
          arrHilb[ i ] = oBuf[ i ];

        h = h >> 1;

        level++;

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
   * @author Christian Scheiblich
   * @see math.jwave.transforms.BasicTransform#reverse(double[])
   */
  @Override public double[ ] reverse( double[ ] arrHilb ) {

    double[ ] arrTime = new double[ arrHilb.length ];

    for( int i = 0; i < arrHilb.length; i++ )
      arrTime[ i ] = arrHilb[ i ];

    int level = 0;
    int minWaveLength = _wavelet.getWaveLength( ); // 2, 4, 6, 8, 10, 12, ...

    int h = minWaveLength;

    if( !isBinary( h ) ) h = h << 1; // 6 -> 8, 10 -> 16

    while( h <= arrTime.length && h >= minWaveLength ) {

      double[ ] iBuf = new double[ h ];

      for( int i = 0; i < h; i++ )
        iBuf[ i ] = arrTime[ i ];

      double[ ] oBuf = _wavelet.reverse( iBuf );

      for( int i = 0; i < h; i++ )
        arrTime[ i ] = oBuf[ i ];

      h = h << 1;

      level++;

    } // levels

    return arrTime;

  }// reverse

} // class
