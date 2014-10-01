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
import math.jwave.exceptions.JWaveError;
import math.jwave.exceptions.JWaveException;
import math.jwave.exceptions.JWaveFailure;
import math.jwave.tools.MathToolKit;
import math.jwave.transforms.BasicTransform;
import math.jwave.transforms.wavelets.Wavelet;

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
   * Supplying a various number of little mathematical methods.
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com) 19.02.2014 18:34:34
   */
  protected MathToolKit _mathToolKit;

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

    try {

      if( _transform == null )
        throw new JWaveFailure( "given object is null!" );

      if( !( _transform instanceof BasicTransform ) )
        throw new JWaveFailure( "given object is not of type Wavelet" );

    } catch( JWaveFailure e ) {

      e.printStackTrace( );

    } // try

    _mathToolKit = new MathToolKit( );

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

      try { // always break down these methods
        throw new JWaveError( "THE ITERATION METHODS ARE BORKEN AT MOMENT" );
      } catch( JWaveError e ) {
        e.printStackTrace( );
      } // try

    } else {
      throw new IllegalArgumentException( "Can't use transform :"
          + transform.getClass( ) + " with a specific level decomposition ;"
          + " use Transform( TransformI transform ) constructor instead." );
    }

    _mathToolKit = new MathToolKit( );

  } // Transform

  /**
   * Performs the forward transform of the specified BasicWave object.
   * 
   * @date 10.02.2010 09:41:01
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @param arrTime
   *          coefficients of time domain
   * @return coefficients of frequency or Hilbert domain
   */
  public double[ ] forward( double[ ] arrTime ) {

    double[ ] arrHilb = null;

    try {

      arrHilb = _transform.forward( arrTime );

    } catch( JWaveException e ) {

      e.printStackTrace( );

    } // try

    return arrHilb;

  } // forward

  /**
   * Performs the reverse transform of the specified BasicWave object.
   * 
   * @date 10.02.2010 09:42:18
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @param arrHilb
   *          coefficients of frequency or Hilbert domain
   * @return coefficients of time domain
   */
  public double[ ] reverse( double[ ] arrHilb ) {

    double[ ] arrTime = null;

    try {

      arrTime = _transform.reverse( arrHilb );

    } catch( JWaveException e ) {

      e.printStackTrace( );

    } // try

    return arrTime;

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

    Complex[ ] arrFreq = null;

    try {

      arrFreq = ( (BasicTransform)_transform ).forward( arrTime );

    } catch( JWaveException e ) {

      e.printStackTrace( );

    } // try

    return arrFreq;

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

    Complex[ ] arrTime = null;

    try {

      arrTime = ( (BasicTransform)_transform ).reverse( arrFreq );

    } catch( JWaveException e ) {

      e.printStackTrace( );

    } // try

    return arrTime;

  } // reverse

  /**
   * Performs the 2-D forward transform of the specified BasicWave object.
   * 
   * @date 10.02.2010 10:58:54
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @param matrixTime
   *          coefficients of 2-D time domain; internal M(i),N(j)
   * @return coefficients of 2-D frequency or Hilbert domain
   */
  public double[ ][ ] forward( double[ ][ ] matrixTime ) {

    double[ ][ ] matrixHilb = null;

    try {

      matrixHilb = _transform.forward( matrixTime );

    } catch( JWaveException e ) {

      e.printStackTrace( );

    } // try

    return matrixHilb;

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
   */
  public double[ ][ ] reverse( double[ ][ ] matrixHilb ) {

    double[ ][ ] matrixTime = null;

    try {

      matrixTime = _transform.reverse( matrixHilb );

    } catch( JWaveException e ) {

      e.printStackTrace( );

    } // try

    return matrixTime;

  } // reverse

  /**
   * Performs the 3-D forward transform of the specified BasicWave object.
   * 
   * @date 10.07.2010 18:15:22
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @param matrixTime
   *          coefficients of 2-D time domain; internal M(i),N(j),O(k)
   * @return coefficients of 2-D frequency or Hilbert domain
   */
  public double[ ][ ][ ] forward( double[ ][ ][ ] spaceTime ) {

    double[ ][ ][ ] spaceHilb = null;

    try {

      spaceHilb = _transform.forward( spaceTime );

    } catch( JWaveException e ) {

      e.printStackTrace( );

    } // try

    return spaceHilb;

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
   */
  public double[ ][ ][ ] reverse( double[ ][ ][ ] spaceHilb ) {

    double[ ][ ][ ] spaceTime = null;

    try {

      spaceTime = _transform.reverse( spaceHilb );

    } catch( JWaveException e ) {

      e.printStackTrace( );

    } // try

    return spaceTime;

  } // reverse

  /**
   * Generates from a 1D signal a 2D output, where the second dimension are the
   * levels of the wavelet transform.
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 17.08.2014 10:07:19
   * @param arrTime
   *          coefficients of time domain
   * @return matDeComp 2-D Hilbert spaces: [ 0 .. p ][ 0 .. N ] where p is the
   *         exponent of N=2^p
   */
  public double[ ][ ] decompose( double[ ] arrTime ) {

    double[ ][ ] matDeComp = null;

    try {

      matDeComp = _transform.decompose( arrTime );

    } catch( JWaveException e ) {

      e.printStackTrace( );

    } // try

    return matDeComp;

  } // decompose

  /**
   * Generates from a 2-D decomposition a 1-D time series.
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 17.08.2014 10:07:19
   * @param matDeComp
   *          2-D Hilbert spaces: [ 0 .. p ][ 0 .. N ] where p is the exponent
   *          of N=2^p
   * @return a 1-D time domain signal
   */
  public double[ ] recompose( double[ ][ ] matDeComp ) {

    double[ ] arrTime = null;

    try {

      arrTime = _transform.recompose( matDeComp );

    } catch( JWaveException e ) {

      e.printStackTrace( );

    } // try

    return arrTime;

  } // recompose

} // class
