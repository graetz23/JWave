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
package math.jwave;

import math.jwave.datatypes.Complex;
import math.jwave.exceptions.JWaveFailure;
import math.jwave.transforms.BasicTransform;

/**
 * Base class for transforms like DiscreteFourierTransform, FastBasicTransform,
 * and WaveletPacketTransform.
 * 
 * @date 19.05.2009 09:43:40
 * @author Christian Scheiblich (cscheiblich@gmail.com)
 */
public class Transform {

  /**
   * Transform object of type base class
   */
  protected BasicTransform _transform;

  /**
   * Constructor; needs some object like DiscreteFourierTransform,
   * FastBasicTransform, WaveletPacketTransfom, ...
   * 
   * @date 19.05.2009 09:50:24
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @param transform
   *          Transform object
   */
  public Transform( BasicTransform transform ) {

    _transform = transform;

  } // Transform

  /**
   * Constructor; needs some object like DiscreteFourierTransform,
   * FastBasicTransform, WaveletPacketTransfom, ... It take also a number of
   * iteration for decomposition
   * 
   * @date 19.05.2009 09:50:24
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   */
  @Deprecated public Transform( BasicTransform transform, int iteration ) {
    if( transform instanceof BasicTransform ) {
      _transform = transform;

      // TODO realize the level transform in GOOD Software Engineering
      // style - after restructuring the code

      // ( (WaveletTransform)_transform ).set_iteration( iteration );

    } else {
      throw new IllegalArgumentException( "Can't use transform :"
          + transform.getClass( ) + " with a specific level decomposition ;"
          + " use Transform( TransformI transform ) constructor instead." );
    }
  } // Transform

  /**
   * Performs the forward transform of the specified BasicWave object.
   * 
   * @date 10.02.2010 09:41:01
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @param arrTime
   *          coefficients of time domain
   * @return coefficients of frequency or Hilbert domain
   * @throws JWaveFailure
   *           array is not of type 2^p
   */
  public double[ ] forward( double[ ] arrTime ) throws JWaveFailure {

    if( !isBinary( arrTime.length ) )
      throw new JWaveFailure(
          "given array length is not 2^p = 1, 2, 4, 8, 16, 32, .. "
              + "please use the Ancient Egyptian Decomposition for any other array length!" );

    return _transform.forward( arrTime );

  } // forward

  /**
   * Performs the reverse transform of the specified BasicWave object.
   * 
   * @date 10.02.2010 09:42:18
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @param arrFreq
   *          coefficients of frequency or Hilbert domain
   * @return coefficients of time domain
   * @throws JWaveFailureif
   *           array is not of type 2^p
   */
  public double[ ] reverse( double[ ] arrFreq ) throws JWaveFailure {

    if( !isBinary( arrFreq.length ) )
      throw new JWaveFailure(
          "given array length is not 2^p = 1, 2, 4, 8, 16, 32, .. "
              + "please use the Ancient Egyptian Decomposition for any other array length!" );

    return _transform.reverse( arrFreq );

  } // reverse

  /**
   * Performs the forward transform from time domain to frequency or Hilbert
   * domain for a given array depending on the used transform algorithm by
   * inheritance.
   * 
   * @date 23.11.2010 19:19:24
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @param arrTime
   *          coefficients of 1-D time domain
   * @return coefficients of 1-D frequency or Hilbert domain
   */
  public Complex[ ] forward( Complex[ ] arrTime ) {

    return ( (BasicTransform)_transform ).forward( arrTime );

  } // forward

  /**
   * Performs the reverse transform from frequency or Hilbert domain to time
   * domain for a given array depending on the used transform algorithm by
   * inheritance.
   * 
   * @date 23.11.2010 19:19:33
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @param arrFreq
   *          coefficients of 1-D frequency or Hilbert domain
   * @return coefficients of 1-D time domain
   */
  public Complex[ ] reverse( Complex[ ] arrFreq ) {

    return ( (BasicTransform)_transform ).reverse( arrFreq );

  } // reverse

  /**
   * Performs the 2-D forward transform of the specified BasicWave object.
   * 
   * @date 10.02.2010 10:58:54
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @param matrixTime
   *          coefficients of 2-D time domain; internal M(i),N(j)
   * @return coefficients of 2-D frequency or Hilbert domain
   * @throws JWaveFailure
   *           if matrix is not of type 2^p x 2^q
   */
  public double[ ][ ] forward( double[ ][ ] matrixTime ) throws JWaveFailure {

    int M = matrixTime.length;

    if( !isBinary( M ) )
      throw new JWaveFailure(
          "given matrix dimension "
              + M
              + " is not 2^p = 1, 2, 4, 8, 16, 32, .. "
              + "please use the Ancient Egyptian Decomposition for any other array length!" );

    for( int i = 0; i < M; i++ )
      if( !isBinary( matrixTime[ i ].length ) )
        throw new JWaveFailure(
            "given matrix dimension N(i)="
                + matrixTime[ i ].length
                + " is not 2^p = 1, 2, 4, 8, 16, 32, .. "
                + "please use the Ancient Egyptian Decomposition for any other array length!" );

    return _transform.forward( matrixTime );

  } // forward

  /**
   * Performs the 2-D reverse transform of the specified BasicWave object.
   * 
   * @date 10.02.2010 10:59:32
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @param matrixFreq
   *          coefficients of 2-D frequency or Hilbert domain; internal
   *          M(i),N(j)
   * @return coefficients of 2-D time domain
   * @throws JWaveFailure
   *           if matrix is not of type 2^p x 2^q
   */
  public double[ ][ ] reverse( double[ ][ ] matrixFreq ) throws JWaveFailure {

    int M = matrixFreq.length;

    if( !isBinary( M ) )
      throw new JWaveFailure(
          "given matrix dimension "
              + M
              + " is not 2^p = 1, 2, 4, 8, 16, 32, .. "
              + "please use the Ancient Egyptian Decomposition for any other array length!" );

    for( int i = 0; i < M; i++ )
      if( !isBinary( matrixFreq[ i ].length ) )
        throw new JWaveFailure(
            "given matrix dimension N(i)="
                + matrixFreq[ i ].length
                + " is not 2^p = 1, 2, 4, 8, 16, 32, .. "
                + "please use the Ancient Egyptian Decomposition for any other array length!" );

    return _transform.reverse( matrixFreq );

  } // reverse

  /**
   * Performs the 3-D forward transform of the specified BasicWave object.
   * 
   * @date 10.07.2010 18:15:22
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @param matrixTime
   *          coefficients of 2-D time domain; internal M(i),N(j),O(k)
   * @return coefficients of 2-D frequency or Hilbert domain
   * @throws JWaveFailure
   *           if space is not of type 2^p x 2^q x 2^r
   */
  public double[ ][ ][ ] forward( double[ ][ ][ ] spaceTime )
      throws JWaveFailure {

    int M = spaceTime.length;

    if( !isBinary( M ) )
      throw new JWaveFailure(
          "given space dimension "
              + M
              + " is not 2^p = 1, 2, 4, 8, 16, 32, .. "
              + "please use the Ancient Egyptian Decomposition for any other array length!" );

    for( int i = 0; i < M; i++ ) { // M(i)

      int N = spaceTime[ i ].length;

      if( !isBinary( N ) )
        throw new JWaveFailure(
            "given space dimension N(i)="
                + N
                + " is not 2^p = 1, 2, 4, 8, 16, 32, .. "
                + "please use the Ancient Egyptian Decomposition for any other array length!" );

      for( int j = 0; j < N; j++ )
        // // N(j)
        if( !isBinary( spaceTime[ i ][ j ].length ) )
          // O
          throw new JWaveFailure(
              "given space dimension M(j)="
                  + spaceTime[ i ][ j ].length
                  + " is not 2^p = 1, 2, 4, 8, 16, 32, .. "
                  + "please use the Ancient Egyptian Decomposition for any other array length!" );

    } // i

    return _transform.forward( spaceTime );

  } // forward

  /**
   * Performs the 3-D reverse transform of the specified BasicWave object.
   * 
   * @date 10.07.2010 18:15:33
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @param matrixFreq
   *          coefficients of 2-D frequency or Hilbert domain; internal
   *          M(i),N(j),O(k)
   * @return coefficients of 2-D time domain
   * @throws JWaveFailure
   *           if space is not of type 2^p x 2^q x 2^r
   */
  public double[ ][ ][ ] reverse( double[ ][ ][ ] spaceFreq )
      throws JWaveFailure {

    int M = spaceFreq.length;

    if( !isBinary( M ) )
      throw new JWaveFailure(
          "given space dimension "
              + M
              + " is not 2^p = 1, 2, 4, 8, 16, 32, .. "
              + "please use the Ancient Egyptian Decomposition for any other array length!" );

    for( int i = 0; i < M; i++ ) { // M(i)

      int N = spaceFreq[ i ].length;

      if( !isBinary( N ) )
        throw new JWaveFailure(
            "given space dimension N(i)="
                + N
                + " is not 2^p = 1, 2, 4, 8, 16, 32, .. "
                + "please use the Ancient Egyptian Decomposition for any other array length!" );

      for( int j = 0; j < N; j++ )
        // // N(j)
        if( !isBinary( spaceFreq[ i ][ j ].length ) )
          // O
          throw new JWaveFailure(
              "given space dimension M(j)="
                  + spaceFreq[ i ][ j ].length
                  + " is not 2^p = 1, 2, 4, 8, 16, 32, .. "
                  + "please use the Ancient Egyptian Decomposition for any other array length!" );

    } // i

    return _transform.reverse( spaceFreq );

  } // reverse

  /**
   * Checks if given number is of type 2^p = 1, 2, 4, 8, 18, 32, 64, .., 1024, ..
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com) 10.02.2014 20:18:26
   * @param number
   *          any positive integer
   * @return true if is 2^p else false
   */
  private boolean isBinary( int number ) {

    boolean isBinary = false;

    int power = (int)( Math.log( number ) / Math.log( 2. ) );

    double result = 1. * Math.pow( 2., power );

    if( result == number ) isBinary = true;

    return isBinary;

  } // isBinary

} // class
