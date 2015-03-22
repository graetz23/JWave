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

import math.jwave.exceptions.JWaveException;
import math.jwave.exceptions.JWaveFailure;
import math.jwave.tools.MathToolKit;
import math.jwave.transforms.wavelets.Wavelet;

/**
 * Base class for the forward and reverse Fast Wavelet Transform in 1-D, 2-D,
 * and 3-D using a specified Wavelet by inheriting class.
 * 
 * @date 10.02.2010 08:10:42
 * @author Christian Scheiblich (cscheiblich@gmail.com)
 */
/**
 * @author Christian Scheiblich 05.02.2014 22:12:45
 */
public class FastWaveletTransform extends WaveletTransform {

  /**
   * Constructor receiving a Wavelet object.
   * 
   * @date 10.02.2010 08:10:42
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @param wavelet
   *          object of type Wavelet; Haar1, Daubechies2, Coiflet1, ...
   */
  public FastWaveletTransform( Wavelet wavelet ) {

    super( wavelet );

    _name = "Fast Wavelet Transform";

  } // FastWaveletTransform

  /**
   * Performs the 1-D forward transform for arrays of dim N from time domain to
   * Hilbert domain for the given array using the Fast Wavelet Transform (FWT)
   * algorithm.
   * 
   * @date 10.02.2010 08:23:24
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @throws JWaveException
   * @see math.jwave.transforms.BasicTransform#forward(double[])
   */
  @Override public double[ ] forward( double[ ] arrTime ) throws JWaveException {

    if( !MathToolKit.isBinary( arrTime.length ) )
      throw new JWaveFailure(
          "given array length is not 2^p | p € N ... = 1, 2, 4, 8, 16, 32, .. "
              + "please use the Ancient Egyptian Decomposition for any other array length!" );

    double[ ] arrHilb = Arrays.copyOf( arrTime, arrTime.length );

    int h = arrHilb.length;
    int transformWavelength = _wavelet.getTransformWavelength( ); // 2, 4, 8, 16, 32, ...
    while( h >= transformWavelength ) {

      double[ ] arrTempPart = _wavelet.forward( arrHilb, h );
      System.arraycopy( arrTempPart, 0, arrHilb, 0, h );
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
   * @throws JWaveException
   * @see math.jwave.transforms.BasicTransform#reverse(double[])
   */
  @Override public double[ ] reverse( double[ ] arrHilb ) throws JWaveException {

    if( !MathToolKit.isBinary( arrHilb.length ) )
      throw new JWaveFailure(
          "given array length is not 2^p | p € N ... = 1, 2, 4, 8, 16, 32, .. "
              + "please use the Ancient Egyptian Decomposition for any other array length!" );

    double[ ] arrTime = Arrays.copyOf( arrHilb, arrHilb.length );

    int transformWavelength = _wavelet.getTransformWavelength( ); // 2, 4, 8, 16, 32, ...
    int h = transformWavelength;
    while( h <= arrTime.length && h >= transformWavelength ) {

      double[ ] arrTempPart = _wavelet.reverse( arrTime, h );
      System.arraycopy( arrTempPart, 0, arrTime, 0, h );
      h = h << 1;

    } // levels

    return arrTime;

  } // reverse

  /**
   * Performs the 1-D forward transform to a Hilbert space of a certain domain
   * (level) for arrays of dim 2^p | p€N of time domain, by using the Fast
   * Wavelet Transform (FWT) algorithm.
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 22.03.2015 11:58:37 (non-Javadoc)
   * @see math.jwave.transforms.BasicTransform#forward(double[], int)
   */
  @Override public double[ ] forward( double[ ] arrTime, int level )
      throws JWaveException {

    if( !MathToolKit.isBinary( arrTime.length ) )
      throw new JWaveFailure(
          "given array length is not 2^p | p € N ... = 1, 2, 4, 8, 16, 32, .. "
              + "please use the Ancient Egyptian Decomposition for any other array length!" );

    int noOfLevels = MathToolKit.getExponent( arrTime.length );
    if( level < 0 || level > noOfLevels )
      throw new JWaveFailure(
          "FastWaveletTransform#forward - given level is out of range for given array" );

    double[ ] arrHilb = Arrays.copyOf( arrTime, arrTime.length );

    int l = 0;
    int h = arrHilb.length;
    int transformWavelength = _wavelet.getTransformWavelength( ); // 2, 4, 8, 16, 32, ...
    while( h >= transformWavelength && l < level ) {

      double[ ] arrTempPart = _wavelet.forward( arrHilb, h );
      System.arraycopy( arrTempPart, 0, arrHilb, 0, h );
      h = h >> 1;
      l++;

    } // levels

    return arrHilb;

  } // forward

  /**
   * Performs the 1-D reverse transform from a certain domain of dim 2^p | p€N
   * from Hilbert domain to time domain using the Fast Wavelet Transform (FWT)
   * algorithm and the selected wavelet. The given level has to match the
   * supported coefficients of Hilbert space; energy & details of level!
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 22.03.2015 12:00:10 (non-Javadoc)
   * @see math.jwave.transforms.BasicTransform#reverse(double[], int)
   */
  @Override public double[ ] reverse( double[ ] arrHilb, int level )
      throws JWaveException {

    if( !MathToolKit.isBinary( arrHilb.length ) )
      throw new JWaveFailure(
          "given array length is not 2^p | p € N ... = 1, 2, 4, 8, 16, 32, .. "
              + "please use the Ancient Egyptian Decomposition for any other array length!" );

    int noOfLevels = MathToolKit.getExponent( arrHilb.length );
    if( level < 0 || level > noOfLevels )
      throw new JWaveFailure(
          "FastWaveletTransform#reverse - given level is out of range for given array" );

    double[ ] arrTime = Arrays.copyOf( arrHilb, arrHilb.length );

    int length = arrTime.length; // length of first Hilbert space

    int transformWavelength = _wavelet.getTransformWavelength( ); // 2, 4, 8, 16, 32, ...
    int h = transformWavelength;

    int steps = (int)MathToolKit.getExponent( length );
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
   * Generates from a 1-D signal a 2-D output, where the second dimension are
   * the levels of the wavelet transform. The first level is keeping the
   * original coefficients. All following levels keep each step of the
   * decomposition of the Fast Wavelet Transform.
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 17.08.2014 10:07:19
   * @param arrTime
   *          coefficients of time domain
   * @return matDeComp coefficients of frequency or Hilbert domain in 2-D
   *         spaces: [ 0 .. p ][ 0 .. N ] where p is the exponent of N=2^p
   */
  @Override public double[ ][ ] decompose( double[ ] arrTime ) {

    int levels = MathToolKit.getExponent( arrTime.length );
    double[ ] arrHilb = Arrays.copyOf( arrTime, arrTime.length );
    double[ ][ ] matDeComp = new double[ levels + 1 ][ arrTime.length ];
    for( int i = 0; i < arrTime.length; i++ )
      matDeComp[ 0 ][ i ] = arrTime[ i ]; // copy hilbert space of level 0; normal space 

    int l = 1; // start with level 1 cause level 0 is the normal space
    int h = arrHilb.length;
    int transformWavelength = _wavelet.getTransformWavelength( ); // 2, 4, 8, 16, 32, ...
    while( h >= transformWavelength ) {

      double[ ] arrTempPart = _wavelet.forward( arrHilb, h );
      System.arraycopy( arrTempPart, 0, arrHilb, 0, h );

      // each level is keepinh the energy and details of the one before!
      // So from each level there can be a reconstruction! 
      for( int i = 0; i < arrTime.length; i++ )
        if( i < h )
          matDeComp[ l ][ i ] = arrHilb[ i ];
        else
          matDeComp[ l ][ i ] = 0.;

      h = h >> 1;
      l++; // next level

    } // levels

    return matDeComp;

  } // decompose




  /**
   * Generates from a 1-D signal a 2-D output, where the second dimension are
   * the levels of the wavelet transform. The first level should keep the
   * original coefficients. All following levels should keep each step of the
   * decomposition of the Fast Wavelet Transform. However, each level of the
   * this decomposition matrix is having the full set, full energy and full
   * details, that are needed to do a full reconstruction. So one can select a
   * level filter it and then do reconstruction only from this single line!
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 17.08.2014 10:07:19
   * @see math.jwave.transforms.BasicTransform#recompose(double[][], int)
   * @param matDeComp
   *          2-D Hilbert spaces: [ 0 .. p ][ 0 .. N ] where p is the exponent
   *          of N=2^p
   * @return a 1-D time domain signal
   */
  public double[ ] recompose( double[ ][ ] matDeComp, int level )
      throws JWaveFailure {

    int noOfLevels = matDeComp.length;
    if( level < 0 || level >= noOfLevels )
      throw new JWaveFailure(
          "FastWaveletTransform#recomposeFromLevel - given level is out of range" );

    int length = matDeComp[ 0 ].length; // length of first Hilbert space

    double[ ] arrTime = new double[ length ];
    for( int i = 0; i < length; i++ )
      arrTime[ i ] = matDeComp[ level ][ i ]; // take selected level for reconstruction

    // go to selected level and perform matching reverse transform for selected level
    int transformWavelength = _wavelet.getTransformWavelength( ); // 2, 4, 8, 16, 32, ...
    int h = transformWavelength;
    int steps = (int)MathToolKit.getExponent( length );
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
