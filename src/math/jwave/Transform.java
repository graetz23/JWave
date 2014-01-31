/**
 * JWave - Java implementation of wavelet transform algorithms
 *
 * Copyright 2009-2014 Christian Scheiblich
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
 * This file Transform.java is part of JWave.
 *
 * @author Christian Scheiblich
 * date 23.02.2010 05:42:23
 * contact cscheiblich@gmail.com
 */
package math.jwave;

import math.jwave.datatypes.Complex;
import math.jwave.transforms.BasicTransform;

/**
 * Base class for transforms like DiscreteFourierTransform,
 * FastBasicTransform, and WaveletPacketTransform.
 * 
 * @date 19.05.2009 09:43:40
 * @author Christian Scheiblich
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
   * @author Christian Scheiblich
   * @param transform Transform object
   */
  public Transform( BasicTransform transform ) {
	  
    _transform = transform;
    
  } // Transform
  
  /**
   * Constructor; needs some object like DiscreteFourierTransform,
   * FastBasicTransform, WaveletPacketTransfom, ... It take also a number of iteration for decomposition
   * 
   * @date 19.05.2009 09:50:24
   * @author Christian Scheiblich
   */
  @Deprecated
  public Transform( BasicTransform transform, int iteration ) {
    if( transform instanceof BasicTransform ) {
      _transform = transform;
      
      // TODO realize the level transform in GOOD Software Engineering style
      
      //      ( (WaveletTransform)_transform ).set_iteration( iteration );
      
    } else {
      throw new IllegalArgumentException( "Can't use transform :" + transform.getClass( ) + " with a specific level decomposition ;" + " use Transform( TransformI transform ) constructor instead." );
    }
  } // Transform
  
  /**
   * Performs the forward transform of the specified BasicWave object.
   * 
   * @date 10.02.2010 09:41:01
   * @author Christian Scheiblich
   * @param arrTime
   *          coefficients of time domain
   * @return coefficients of frequency or Hilbert domain
   */
  public double[ ] forward( double[ ] arrTime ) {
    return _transform.forward( arrTime );
  } // forward
  
  /**
   * Performs the reverse transform of the specified BasicWave object.
   * 
   * @date 10.02.2010 09:42:18
   * @author Christian Scheiblich
   * @param arrFreq
   *          coefficients of frequency or Hilbert domain
   * @return coefficients of time domain
   */
  public double[ ] reverse( double[ ] arrFreq ) {
    return _transform.reverse( arrFreq );
  } // reverse
  
  /**
   * Performs the forward transform from time domain to frequency or Hilbert
   * domain for a given array depending on the used transform algorithm by
   * inheritance.
   * 
   * @date 23.11.2010 19:19:24
   * @author Christian Scheiblich
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
   * @author Christian Scheiblich
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
   * @author Christian Scheiblich
   * @param matrixTime
   *          coefficients of 2-D time domain
   * @return coefficients of 2-D frequency or Hilbert domain
   */
  public double[ ][ ] forward( double[ ][ ] matrixTime ) {
    return _transform.forward( matrixTime );
  } // forward
  
  /**
   * Performs the 2-D reverse transform of the specified BasicWave object.
   * 
   * @date 10.02.2010 10:59:32
   * @author Christian Scheiblich
   * @param matrixFreq
   *          coefficients of 2-D frequency or Hilbert domain
   * @return coefficients of 2-D time domain
   */
  public double[ ][ ] reverse( double[ ][ ] matrixFreq ) {
    return _transform.reverse( matrixFreq );
  } // reverse
  
  /**
   * Performs the 3-D forward transform of the specified BasicWave object.
   * 
   * @date 10.07.2010 18:15:22
   * @author Christian Scheiblich
   * @param matrixTime
   *          coefficients of 2-D time domain
   * @return coefficients of 2-D frequency or Hilbert domain
   */
  public double[ ][ ][ ] forward( double[ ][ ][ ] spaceTime ) {
    return _transform.forward( spaceTime );
  } // forward
  
  /**
   * Performs the 3-D reverse transform of the specified BasicWave object.
   * 
   * @date 10.07.2010 18:15:33
   * @author Christian Scheiblich
   * @param matrixFreq
   *          coefficients of 2-D frequency or Hilbert domain
   * @return coefficients of 2-D time domain
   */
  public double[ ][ ][ ] reverse( double[ ][ ][ ] spaceFreq ) {
    return _transform.reverse( spaceFreq );
  } // reverse
  
} // class
