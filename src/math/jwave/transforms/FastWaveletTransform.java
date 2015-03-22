/**
 * JWave is distributed under the MIT License (MIT); this file is part of.
 *
 * Copyright (c) 2008-2015 Christian Scheiblich (cscheiblich@gmail.com)
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package math.jwave.transforms;

import java.util.Arrays;

import math.jwave.transforms.wavelets.Wavelet;
import math.jwave.exceptions.JWaveException;
import math.jwave.exceptions.JWaveFailure;

/**
 * Base class for - stepping - forward and reverse Fast Wavelet Transform in
 * 1-D, 2-D, and 3-D using a specific Wavelet.
 * 
 * @date 10.02.2010 08:10:42
 * @author Christian Scheiblich (cscheiblich@gmail.com)
 */
public class FastWaveletTransform extends WaveletTransform {

  /**
   * Constructor receiving a Wavelet object and setting identifier of transform.
   * 
   * @date 10.02.2010 08:10:42
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @param wavelet
   *          object of type Wavelet
   */
  public FastWaveletTransform( Wavelet wavelet ) {

    super( wavelet );
    _name = "Fast Wavelet Transform"; // set identifier of transform; keep constant

  } // FastWaveletTransform

  /**
   * Performs a 1-D forward transform from time domain to Hilbert domain using
   * one kind of a Fast Wavelet Transform (FWT) algorithm for a given array of
   * dimension (length) 2^p | p€N; N = 2, 4, 8, 16, 32, 64, 128, .., and so on.
   * 
   * @date 10.02.2010 08:23:24
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @throws JWaveException
   *           if given array is not of length 2^p | p€N
   * @see math.jwave.transforms.BasicTransform#forward(double[])
   */
  @Override public double[ ] forward( double[ ] arrTime ) throws JWaveException {

    if( !isBinary( arrTime.length ) )
      throw new JWaveFailure(
          "FastWaveletTransform#forward - "
              + "given array length is not 2^p | p € N ... = 1, 2, 4, 8, 16, 32, .. "
              + "please use the Ancient Egyptian Decomposition for any other array length!" );

    int max = calcExponent( arrTime.length );
    return forward( arrTime, max ); // forward by maximal steps

    //    double[ ] arrHilb = Arrays.copyOf( arrTime, arrTime.length );
    //    int h = arrHilb.length;
    //    int transformWavelength = _wavelet.getTransformWavelength( ); // normally 2
    //    while( h >= transformWavelength ) {
    //      double[ ] arrTempPart = _wavelet.forward( arrHilb, h );
    //      System.arraycopy( arrTempPart, 0, arrHilb, 0, h );
    //      h = h >> 1;
    //    } // levels
    //    return arrHilb;

  } // forward

  /**
   * Performs a 1-D forward transform from time domain to Hilbert domain using
   * one kind of a Fast Wavelet Transform (FWT) algorithm for a given array of
   * dimension (length) 2^p | p€N; N = 2, 4, 8, 16, 32, 64, 128, .., and so on.
   * However, the algorithms stops for a supported level that has be in the
   * range 0, .., p of the dimension of the input array; 0 is the time series
   * itself and p is the maximal number of possible levels.
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 22.03.2015 11:58:37
   * @throws JWaveException
   *           if given array is not of length 2^p | p€N or given level does not
   *           match the supported domain (array)
   * @see math.jwave.transforms.BasicTransform#forward(double[], int)
   */
  @Override public double[ ] forward( double[ ] arrTime, int level )
      throws JWaveException {

    if( !isBinary( arrTime.length ) )
      throw new JWaveFailure(
          "FastWaveletTransform#forward - "
              + "given array length is not 2^p | p € N ... = 1, 2, 4, 8, 16, 32, .. "
              + "please use the Ancient Egyptian Decomposition for any other array length!" );

    int noOfLevels = calcExponent( arrTime.length );
    if( level < 0 || level > noOfLevels )
      throw new JWaveFailure( "FastWaveletTransform#forward - "
          + "given level is out of range for given array" );

    double[ ] arrHilb = Arrays.copyOf( arrTime, arrTime.length );

    int l = 0;
    int h = arrHilb.length;
    int transformWavelength = _wavelet.getTransformWavelength( ); // normally 2
    while( h >= transformWavelength && l < level ) {

      double[ ] arrTempPart = _wavelet.forward( arrHilb, h );
      System.arraycopy( arrTempPart, 0, arrHilb, 0, h );
      h = h >> 1;
      l++;

    } // levels

    return arrHilb;

  } // forward

  /**
   * Performs a 1-D reverse transform from Hilbert domain to time domain using
   * one kind of a Fast Wavelet Transform (FWT) algorithm for a given array of
   * dimension (length) 2^p | p€N; N = 2, 4, 8, 16, 32, 64, 128, .., and so on.
   * s *
   * 
   * @date 10.02.2010 08:23:24
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @throws JWaveException
   *           if given array is not of length 2^p | p€N
   * @see math.jwave.transforms.BasicTransform#reverse(double[])
   */
  @Override public double[ ] reverse( double[ ] arrHilb ) throws JWaveException {

    if( !isBinary( arrHilb.length ) )
      throw new JWaveFailure(
          "FastWaveletTransform#reverse - "
              + "given array length is not 2^p | p € N ... = 1, 2, 4, 8, 16, 32, .. "
              + "please use the Ancient Egyptian Decomposition for any other array length!" );

    int max = calcExponent( arrHilb.length );
    return reverse( arrHilb, max );

    //    double[ ] arrTime = Arrays.copyOf( arrHilb, arrHilb.length );
    //    int transformWavelength = _wavelet.getTransformWavelength( ); // normally 2
    //    int h = transformWavelength;
    //    while( h <= arrTime.length && h >= transformWavelength ) {
    //      double[ ] arrTempPart = _wavelet.reverse( arrTime, h );
    //      System.arraycopy( arrTempPart, 0, arrTime, 0, h );
    //      h = h << 1;
    //    } // levels
    //    return arrTime;

  } // reverse

  /**
   * Performs a 1-D reverse transform from Hilbert domain to time domain using
   * one kind of a Fast Wavelet Transform (FWT) algorithm for a given array of
   * dimension (length) 2^p | p€N; N = 2, 4, 8, 16, 32, 64, 128, .., and so on.
   * However, the algorithms starts for at a supported level that has be in the
   * range 0, .., p of the dimension of the input array; 0 is the time series
   * itself and p is the maximal number of possible levels. The coefficients of
   * the input array have to match to the supported level.
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 22.03.2015 12:00:10
   * @throws JWaveException
   *           if given array is not of length 2^p | p€N or given level does not
   *           match the supported domain (array)
   * @see math.jwave.transforms.BasicTransform#reverse(double[], int)
   */
  @Override public double[ ] reverse( double[ ] arrHilb, int level )
      throws JWaveException {

    if( !isBinary( arrHilb.length ) )
      throw new JWaveFailure(
          "FastWaveletTransform#reverse - "
              + "given array length is not 2^p | p € N ... = 1, 2, 4, 8, 16, 32, .. "
              + "please use the Ancient Egyptian Decomposition for any other array length!" );

    int noOfLevels = calcExponent( arrHilb.length );
    if( level < 0 || level > noOfLevels )
      throw new JWaveFailure( "FastWaveletTransform#reverse - "
          + "given level is out of range for given array" );

    int length = arrHilb.length; // length of first Hilbert space
    double[ ] arrTime = Arrays.copyOf( arrHilb, length );

    int transformWavelength = _wavelet.getTransformWavelength( ); // normally 2
    int h = transformWavelength;

    int steps = calcExponent( length );
    for( int l = level; l < steps; l++ )
      h = h << 1; // begin reverse transform at certain - matching - level of hilbert space

    while( h <= arrTime.length && h >= transformWavelength ) {

      double[ ] arrTempPart = _wavelet.reverse( arrTime, h );
      System.arraycopy( arrTempPart, 0, arrTime, 0, h );
      h = h << 1;

    } // levels

    return arrTime;

  } // reverse

  /**
   * Performs several 1-D forward transforms from time domain to all possible
   * Hilbert domains using one kind of a Fast Wavelet Transform (FWT) algorithm
   * for a given array of dimension (length) 2^p | p€N; N = 2, 4, 8, 16, 32, 64,
   * 128, .., and so on. However, the algorithm stores all levels in a matrix
   * that has in first dimension the range of 0, .., p and in second dimension
   * the coefficients (energy & details) of a certain level. From any level a
   * full reconstruction can be performed. The first dimension is keeping the
   * time series, due to being the Hilbert space of level 0. All following
   * dimensions are keeping the next higher Hilbert spaces, so the next step in
   * wavelet filtering for the FWT.
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 17.08.2014 10:07:19
   * @param arrTime
   *          coefficients of time domain
   * @return matDeComp coefficients of frequency or Hilbert domain in 2-D
   *         spaces: [ 0 .. p ][ 0 .. M ] where p is the exponent of M=2^p | p€N
   * @throws JWaveException
   *           if something does not match upon the criteria of input
   */
  @Override public double[ ][ ] decompose( double[ ] arrTime )
      throws JWaveException {

    int length = arrTime.length;
    int levels = calcExponent( length );
    double[ ][ ] matDeComp = new double[ levels + 1 ][ length ];
    for( int p = 0; p <= levels; p++ )
      System.arraycopy( forward( arrTime, p ), 0, matDeComp[ p ], 0, length );

    //    for( int i = 0; i < arrTime.length; i++ )
    //      matDeComp[ 0 ][ i ] = arrTime[ i ]; // copy hilbert space of level 0; normal space 
    //    int l = 1; // start with level 1 cause level 0 is the normal space
    //    int h = arrHilb.length;
    //    int transformWavelength = _wavelet.getTransformWavelength( ); // normally 2
    //    while( h >= transformWavelength ) {
    //      double[ ] arrTempPart = _wavelet.forward( arrHilb, h );
    //      System.arraycopy( arrTempPart, 0, arrHilb, 0, h );
    //      // each level is keeping the energy and details of the one before!
    //      // So from each level there can be a reconstruction! 
    //      for( int i = 0; i < arrTime.length; i++ )
    //        if( i < h )
    //          matDeComp[ l ][ i ] = arrHilb[ i ];
    //        else
    //          matDeComp[ l ][ i ] = 0.;
    //      h = h >> 1;
    //      l++; // next level
    //    } // levels

    return matDeComp;

  } // decompose

  /**
   * Performs one 1-D reverse transform from Hilbert domain to time domain using
   * one kind of a Fast Wavelet Transform (FWT) algorithm for a given array of
   * dimension (length) 2^p | p€N; N = 2, 4, 8, 16, 32, 64, 128, .., and so on.
   * However, the algorithm uses on of level in a matrix that has in first
   * dimension the range of 0, .., p and in second dimension the coefficients
   * (energy & details) the level. From any level a full a reconstruction can be
   * performed; so from the selected by "level". Anyway, the first dimension is
   * keeping the time series, due to being the Hilbert space of level 0. All
   * following dimensions are keeping the next higher Hilbert spaces, so the
   * next step in wavelet filtering for the FWT. If one want to denoise each
   * level in the same way and compare results after reverse transform, this is
   * the best input for it.
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 17.08.2014 10:07:19
   * @see math.jwave.transforms.BasicTransform#recompose(double[][], int)
   * @param matDeComp
   *          2-D Hilbert spaces: [ 0 .. p ][ 0 .. N ] where p is the exponent
   *          of N=2^p
   * @throws JWaveException
   *           if something does not match upon the criteria of input
   * @return a 1-D time domain signal
   */
  public double[ ] recompose( double[ ][ ] matDeComp, int level )
      throws JWaveException {

    int noOfLevels = matDeComp.length;
    if( level < 0 || level >= noOfLevels )
      throw new JWaveFailure( "FastWaveletTransform#recomposeFromLevel - "
          + "given level is out of range" );

    int length = matDeComp[ 0 ].length; // length of first Hilbert space

    double[ ] arrTime = new double[ length ];
    for( int i = 0; i < length; i++ )
      arrTime[ i ] = matDeComp[ level ][ i ]; // take selected level for reconstruction

    // go to selected level and perform matching reverse transform for selected level
    int transformWavelength = _wavelet.getTransformWavelength( ); // normally 2
    int h = transformWavelength;
    int steps = calcExponent( length );
    for( int l = level; l < steps; l++ )
      h = h << 1; // begin reverse transform at certain - matching - level of hilbert space

    while( h <= arrTime.length && h >= transformWavelength ) {

      double[ ] arrTempPart = _wavelet.reverse( arrTime, h );
      System.arraycopy( arrTempPart, 0, arrTime, 0, h );
      h = h << 1;

    } // levels

    return arrTime;

  } // recompose

} // FastWaveletTransfromSplit
