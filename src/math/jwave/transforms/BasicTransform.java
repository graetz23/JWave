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

import math.jwave.datatypes.Complex;
import math.jwave.exceptions.JWaveError;
import math.jwave.exceptions.JWaveException;
import math.jwave.exceptions.JWaveFailure;
import math.jwave.transforms.wavelets.Wavelet;

/**
 * Basic Wave for transformations like Fast Fourier Transform (FFT), Fast
 * Wavelet Transform (FWT), Fast Wavelet Packet Transform (WPT), or Discrete
 * Wavelet Transform (DWT). Naming of this class due to en.wikipedia.org; to
 * write Fourier series in terms of the 'basic waves' of function: e^(2*pi*i*w).
 * 
 * @date 08.02.2010 11:11:59
 * @author Christian Scheiblich (cscheiblich@gmail.com)
 */
public abstract class BasicTransform {

  /**
   * String identifier of the current Transform object.
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 14.03.2015 14:25:56
   */
  protected String _name;

  /**
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 19.02.2014 18:38:21
   */
  public BasicTransform( ) {

    _name = null;

  } // BasicTransform

  /**
   * Returns String identifier of current type of BasicTransform Object.
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 14.03.2015 18:13:34
   * @return identifier as String
   */
  public String getName( ) {
    return _name;
  } // getName

  /**
   * Performs the forward transform from time domain to frequency or Hilbert
   * domain for a given array depending on the used transform algorithm by
   * inheritance.
   * 
   * @date 10.02.2010 08:23:24
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @param arrTime
   *          coefficients of 1-D time domain
   * @return coefficients of 1-D frequency or Hilbert domain
   * @throws JWaveException
   */
  public abstract double[ ] forward( double[ ] arrTime ) throws JWaveException;

  /**
   * Performs the reverse transform from frequency or Hilbert domain to time
   * domain for a given array depending on the used transform algorithm by
   * inheritance.
   * 
   * @date 10.02.2010 08:23:24
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @param arrFreq
   *          coefficients of 1-D frequency or Hilbert domain
   * @return matDeComp 2-D Hilbert spaces: [ 0 .. p ][ 0 .. N ] where p is the
   *         exponent of N=2^p
   * @throws JWaveException
   */
  public abstract double[ ] reverse( double[ ] arrFreq ) throws JWaveException;

  /**
   * Generates from a 2-D decomposition a 1-D time series.
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 17.08.2014 10:07:19
   * @param matDeComp
   *          2-D Hilbert spaces: [ 0 .. p ][ 0 .. N ] where p is the exponent
   *          of N=2^p
   * @return a 1-D time domain signal
   * @throws JWaveException
   */
  public double[ ][ ] decompose( double[ ] arrTime ) throws JWaveException {

    throw new JWaveError( "method is not working for this transform type!" );

  } // decompose

  public double[ ] recompose( double[ ][ ] matDeComp ) throws JWaveException {

    throw new JWaveError( "method is not working for this transform type!" );

  } // recompose
  
  public double[ ] recomposeFromLevel( double[ ][ ] matDeComp, int level ) throws JWaveException {

    throw new JWaveError( "method is not working for this transform type!" );

  } // recomposeFromLevel


  /**
   * Performs the forward transform from time domain to frequency or Hilbert
   * domain for a given array depending on the used transform algorithm by
   * inheritance.
   * 
   * @date 16.02.2014 14:42:57
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   *         (cscheiblich@gmail.com)
   * @param arrTime
   *          coefficients of 1-D time domain
   * @return coefficients of 1-D frequency or Hilbert domain
   * @throws JWaveException
   */
  public Complex[ ] forward( Complex[ ] arrTime ) throws JWaveException {

    double[ ] arrTimeBulk = new double[ 2 * arrTime.length ];

    for( int i = 0; i < arrTime.length; i++ ) {

      // TODO rehack complex number splitting this to: { r1, r2, r3, .., c1, c2, c3, .. }
      int k = i * 2;
      arrTimeBulk[ k ] = arrTime[ i ].getReal( );
      arrTimeBulk[ k + 1 ] = arrTime[ i ].getImag( );

    } // i blown to k = 2 * i

    double[ ] arrHilbBulk = forward( arrTimeBulk );

    Complex[ ] arrHilb = new Complex[ arrTime.length ];

    for( int i = 0; i < arrTime.length; i++ ) {

      int k = i * 2;
      arrHilb[ i ] = new Complex( arrHilbBulk[ k ], arrHilbBulk[ k + 1 ] );

    } // k = 2 * i shrink to i 

    return arrHilb;

  } // forward

  /**
   * Performs the reverse transform from frequency or Hilbert domain to time
   * domain for a given array depending on the used transform algorithm by
   * inheritance.
   * 
   * @date 16.02.2014 14:42:57
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   *         (cscheiblich@gmail.com)
   * @param arrFreq
   *          coefficients of 1-D frequency or Hilbert domain
   * @return coefficients of 1-D time domain
   * @throws JWaveException
   */
  public Complex[ ] reverse( Complex[ ] arrHilb ) throws JWaveException {

    double[ ] arrHilbBulk = new double[ 2 * arrHilb.length ];

    for( int i = 0; i < arrHilb.length; i++ ) {

      int k = i * 2;
      arrHilbBulk[ k ] = arrHilb[ i ].getReal( );
      arrHilbBulk[ k + 1 ] = arrHilb[ i ].getImag( );

    } // i blown to k = 2 * i

    double[ ] arrTimeBulk = reverse( arrHilbBulk );

    Complex[ ] arrTime = new Complex[ arrHilb.length ];

    for( int i = 0; i < arrTime.length; i++ ) {

      int k = i * 2;
      arrTime[ i ] = new Complex( arrTimeBulk[ k ], arrTimeBulk[ k + 1 ] );

    } // k = 2 * i shrink to i 

    return arrTime;

  } // reverse

  /**
   * Performs the 2-D forward transform from time domain to frequency or Hilbert
   * domain for a given array depending on the used transform algorithm by
   * inheritance.
   * 
   * @date 10.02.2010 11:00:29
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @param matTime
   *          coefficients of 2-D time domain
   * @return coefficients of 2-D frequency or Hilbert domain
   * @throws JWaveException
   */
  public double[ ][ ] forward( double[ ][ ] matTime ) throws JWaveException {

    int noOfRows = matTime.length;
    int noOfCols = matTime[ 0 ].length;

    double[ ][ ] matHilb = new double[ noOfRows ][ noOfCols ];

    for( int i = 0; i < noOfRows; i++ ) {

      double[ ] arrTime = new double[ noOfCols ];

      for( int j = 0; j < noOfCols; j++ )
        arrTime[ j ] = matTime[ i ][ j ];

      double[ ] arrHilb = forward( arrTime );

      for( int j = 0; j < noOfCols; j++ )
        matHilb[ i ][ j ] = arrHilb[ j ];

    } // rows

    for( int j = 0; j < noOfCols; j++ ) {

      double[ ] arrTime = new double[ noOfRows ];

      for( int i = 0; i < noOfRows; i++ )
        arrTime[ i ] = matHilb[ i ][ j ];

      double[ ] arrHilb = forward( arrTime );

      for( int i = 0; i < noOfRows; i++ )
        matHilb[ i ][ j ] = arrHilb[ i ];

    } // cols

    return matHilb;
  } // forward

  /**
   * Performs the 2-D reverse transform from frequency or Hilbert or time domain
   * to time domain for a given array depending on the used transform algorithm
   * by inheritance.
   * 
   * @date 10.02.2010 11:01:38
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @param matFreq
   *          coefficients of 2-D frequency or Hilbert domain
   * @return coefficients of 2-D time domain
   * @throws JWaveException
   */
  public double[ ][ ] reverse( double[ ][ ] matFreq ) throws JWaveException {

    int noOfRows = matFreq.length;
    int noOfCols = matFreq[ 0 ].length;

    double[ ][ ] matTime = new double[ noOfRows ][ noOfCols ];

    for( int j = 0; j < noOfCols; j++ ) {

      double[ ] arrFreq = new double[ noOfRows ];

      for( int i = 0; i < noOfRows; i++ )
        arrFreq[ i ] = matFreq[ i ][ j ];

      double[ ] arrTime = reverse( arrFreq ); // AED 

      for( int i = 0; i < noOfRows; i++ )
        matTime[ i ][ j ] = arrTime[ i ];

    } // cols

    for( int i = 0; i < noOfRows; i++ ) {

      double[ ] arrFreq = new double[ noOfCols ];

      for( int j = 0; j < noOfCols; j++ )
        arrFreq[ j ] = matTime[ i ][ j ];

      double[ ] arrTime = reverse( arrFreq ); // AED 

      for( int j = 0; j < noOfCols; j++ )
        matTime[ i ][ j ] = arrTime[ j ];

    } // rows

    return matTime;
  } // reverse

  /**
   * Performs the 3-D forward transform from time domain to frequency or Hilbert
   * domain for a given array depending on the used transform algorithm by
   * inheritance.
   * 
   * @date 10.07.2010 18:08:17
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @param spcTime
   *          coefficients of 3-D time domain domain
   * @return coefficients of 3-D frequency or Hilbert domain
   * @throws JWaveException
   */
  public double[ ][ ][ ] forward( double[ ][ ][ ] spcTime )
      throws JWaveException {

    int noOfRows = spcTime.length; // first dimension
    int noOfCols = spcTime[ 0 ].length; // second dimension
    int noOfHigh = spcTime[ 0 ][ 0 ].length; // third dimension

    double[ ][ ][ ] spcHilb = new double[ noOfRows ][ noOfCols ][ noOfHigh ];

    for( int i = 0; i < noOfRows; i++ ) {

      double[ ][ ] matTime = new double[ noOfCols ][ noOfHigh ];

      for( int j = 0; j < noOfCols; j++ ) {

        for( int k = 0; k < noOfHigh; k++ ) {

          matTime[ j ][ k ] = spcTime[ i ][ j ][ k ];

        } // high

      } // cols      

      double[ ][ ] matHilb = forward( matTime ); // 2-D forward

      for( int j = 0; j < noOfCols; j++ ) {

        for( int k = 0; k < noOfHigh; k++ ) {

          spcHilb[ i ][ j ][ k ] = matHilb[ j ][ k ];

        } // high

      } // cols

    } // rows  

    for( int j = 0; j < noOfCols; j++ ) {

      for( int k = 0; k < noOfHigh; k++ ) {

        double[ ] arrTime = new double[ noOfRows ];

        for( int i = 0; i < noOfRows; i++ )
          arrTime[ i ] = spcHilb[ i ][ j ][ k ];

        double[ ] arrHilb = forward( arrTime ); // 1-D forward

        for( int i = 0; i < noOfRows; i++ )
          spcHilb[ i ][ j ][ k ] = arrHilb[ i ];

      } // high

    } // cols

    return spcHilb;

  } // forward

  /**
   * Performs the 3-D reverse transform from frequency or Hilbert domain to time
   * domain for a given array depending on the used transform algorithm by
   * inheritance.
   * 
   * @date 10.07.2010 18:09:54
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @param spcHilb
   *          coefficients of 3-D frequency or Hilbert domain
   * @return coefficients of 3-D time domain
   * @throws JWaveException
   */
  public double[ ][ ][ ] reverse( double[ ][ ][ ] spcHilb )
      throws JWaveException {

    int noOfRows = spcHilb.length; // first dimension
    int noOfCols = spcHilb[ 0 ].length; // second dimension
    int noOfHigh = spcHilb[ 0 ][ 0 ].length; // third dimension

    double[ ][ ][ ] spcTime = new double[ noOfRows ][ noOfCols ][ noOfHigh ];

    for( int i = 0; i < noOfRows; i++ ) {

      double[ ][ ] matHilb = new double[ noOfCols ][ noOfHigh ];

      for( int j = 0; j < noOfCols; j++ ) {

        for( int k = 0; k < noOfHigh; k++ ) {

          matHilb[ j ][ k ] = spcHilb[ i ][ j ][ k ];

        } // high

      } // cols      

      double[ ][ ] matTime = reverse( matHilb ); // 2-D reverse

      for( int j = 0; j < noOfCols; j++ ) {

        for( int k = 0; k < noOfHigh; k++ ) {

          spcTime[ i ][ j ][ k ] = matTime[ j ][ k ];

        } // high

      } // cols

    } // rows  

    for( int j = 0; j < noOfCols; j++ ) {

      for( int k = 0; k < noOfHigh; k++ ) {

        double[ ] arrHilb = new double[ noOfRows ];

        for( int i = 0; i < noOfRows; i++ )
          arrHilb[ i ] = spcTime[ i ][ j ][ k ];

        double[ ] arrTime = reverse( arrHilb ); // 1-D reverse

        for( int i = 0; i < noOfRows; i++ )
          spcTime[ i ][ j ][ k ] = arrTime[ i ];

      } // high

    } // cols

    return spcTime;

  } // reverse

  /**
   * Returns the stored Wavelet object or null pointer.
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 14.03.2015 18:26:44
   * @return object of type Wavelet of null pointer
   * @throws JWaveFailure
   *           if Wavelet object is not available
   */
  public abstract Wavelet getWavelet( ) throws JWaveFailure;

} // BasicTransform