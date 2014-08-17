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

    while( h >= transformWavelength ) {

      double[ ] arrTempPart = _wavelet.forward( arrHilb, h );

      for( int i = 0; i < h; i++ )
        arrHilb[ i ] = arrTempPart[ i ];

      h = h >> 1;

    } // levels

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

    //    if( !_mathToolKit.isBinary( h ) )
    //      for( h = 2; h <= transformWavelength; h *= 2 ) {}
    // fixed h = h << 1; // 6 -> 8, 10 -> 16

    while( h <= arrTime.length && h >= transformWavelength ) {

      double[ ] arrTempPart = _wavelet.reverse( arrTime, h );

      for( int i = 0; i < h; i++ )
        arrTime[ i ] = arrTempPart[ i ];

      h = h << 1;

    } // levels

    return arrTime;

  } // reverse

  /**
   * Method splits a 1-D time domain signal into each computed hilbert space and
   * stored them in a 2-D space.
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 27.02.2014 21:31:08
   * @param arrTime
   *          1-D time domain signal
   * @return a 2-D hilbert space: [ 0 .. p ][ 0 .. N ] where p is the exponent
   *         of N=2^p
   */
  public double[ ][ ] forwardSplit( double[ ] arrTime ) {

    int l = arrTime.length;

    double[ ] arrHilb = new double[ l ];
    for( int i = 0; i < l; i++ )
      arrHilb[ i ] = arrTime[ i ];

    int levels = _mathToolKit.getExponent( l );

    double[ ][ ] arrHilbSplit = new double[ levels ][ l ];

    int transformWavelength = _wavelet.getTransformWavelength( ); // 2, 4, 8, 16, 32, ...

    int lvl = 0; // the levels of the split space
    int h = l; // begin with full length

    while( h >= transformWavelength ) {

      double[ ] arrTempPart = _wavelet.forward( arrHilb, h );

      for( int i = 0; i < l; i++ ) {

        if( i < h )
          arrHilb[ i ] = arrHilbSplit[ lvl ][ i ] = arrTempPart[ i ];
        else
          arrHilbSplit[ lvl ][ i ] = 0.;

      } // i - full length l instead of h

      h = h >> 1; // .. -> 8 -> 4 -> 2 

      lvl++; // 0, 1, 2, ..

    } // levels

    return arrHilbSplit;

  } // forward

  /**
   * Input 2-D Hilbert spaces which where constructed from a 1-D time domain
   * signal and rebuild it.
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 27.02.2014 21:47:08
   * @param arrHilbSplit
   *          2-D Hilbert spaces: [ 0 .. p ][ 0 .. N ] where p is the exponent
   *          of N=2^p
   * @return a 1-D time domain signal
   */
  public double[ ] reverseSplit( double[ ][ ] arrHilbSplit ) {

    int l = arrHilbSplit[ 0 ].length; // length of first Hilbert space

    double[ ] arrTime = new double[ l ];

    for( int lvl = 0; lvl < l; lvl++ )
      for( int i = 0; i < l; i++ )
        arrTime[ i ] += arrHilbSplit[ lvl ][ i ]; // add them together

    int transformWavelength = _wavelet.getTransformWavelength( ); // 2, 4, 8, 16, 32, ...

    int h = transformWavelength;

    while( h <= l && h >= transformWavelength ) {

      double[ ] arrTempPart = _wavelet.reverse( arrTime, h );

      for( int i = 0; i < h; i++ )
        arrTime[ i ] = arrTempPart[ i ];

      h = h << 1;

    } // levels

    return arrTime;

  } // reverse

  @Override public double[ ][ ] decompose( double[ ] arrTime ) {

    int levels = _mathToolKit.getExponent( arrTime.length );

    double[ ][ ] deComp = new double[ levels ][ arrTime.length ];

    double[ ] arrHilb = new double[ arrTime.length ];

    for( int i = 0; i < arrTime.length; i++ )
      arrHilb[ i ] = arrTime[ i ];

    int l = 0;
    int h = arrHilb.length;
    int transformWavelength = _wavelet.getTransformWavelength( ); // 2, 4, 8, 16, 32, ...

    while( h >= transformWavelength ) {

      double[ ] arrTempPart = _wavelet.forward( arrHilb, h );

      for( int i = 0; i < h; i++ )
        arrHilb[ i ] = arrTempPart[ i ];

      for( int i = 0; i < arrTime.length; i++ )
        if( i < h )
          deComp[ l ][ i ] = arrHilb[ i ];
        else
          deComp[ l ][ i ] = 0.;

      h = h >> 1;

      l++; // next level

    } // levels

    return deComp;

  } // decompose

} // FastWaveletTransfromSplit