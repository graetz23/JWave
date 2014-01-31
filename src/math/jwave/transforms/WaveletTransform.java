/**
 * JWave
 *
 * Copyright 2009-2013 Christian Scheiblich
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
 * This file WaveletTransform.java is part of JWave.
 *
 * @author Christian Scheiblich
 * contact cscheiblich@gmail.com
 * date Feb 6, 2013 4:50:31 PM
 *
 */
package math.jwave.transforms;

import math.jwave.exc.JWaveError;
import math.jwave.exc.JWaveException;
import math.jwave.exc.JWaveFailure;
import math.jwave.transforms.modes.TransformMode;
import math.jwave.transforms.wavelets.Wavelet;

/**
 * @author Christian Scheiblich
 * date Feb 6, 2013 4:50:31 PM
 *
 */
public abstract class WaveletTransform extends BasicTransform {
  
  /**
   * The used wavelet for transforming
   * 
   * @author Christian Scheiblich
   * date Feb 6, 2013 4:51:51 PM
   *
   */
  protected Wavelet _wavelet;
  
  /**
   * Specifying how the transform should do its job.
   * 
   * @author Christian Scheiblich
   * date Feb 7, 2013 3:22:42 PM
   *
   */
  protected TransformMode _transformMode;
  
  
  
  /**
   * Set the initial level of the Hilbert space. 
   *
   * @author Christian Scheiblich
   * 31.01.2014 21:07:55
   */
  protected int _levelOfSpace;


  /**
   * The steps the Wavelet transform should do. If it is set to -1,
   * the transform algorithms perform the maximum number of steps.
   * 
   * @author Christian Scheiblich
   * date Feb 6, 2013 4:54:00 PM
   *
   */
  protected int _steps;
  
  /**
   * @author Christian Scheiblich
   * date Feb 6, 2013 4:51:01 PM
   *
   * @param wavelet
   */
  protected WaveletTransform( Wavelet wavelet ) {
    
    _wavelet = wavelet;
    
    _steps = -1; // allows for the maximum number of steps
    
  }
  
  /**
   * A Wavelet transform that is reduced to minor steps as given
   * by a positive number.
   * 
   * @author Christian Scheiblich
   * date Feb 6, 2013 4:54:43 PM
   *
   * @param wavelet
   * @param steps
   * @throws JWaveException
   */
  @Deprecated
  protected WaveletTransform( Wavelet wavelet, int steps ) {
    
    _wavelet = wavelet;
    
    _steps = steps;
    
  }
  
  /**
   * A wavelet transform that starts at a certain level of space and steps
   * forward or reverse.
   *
   * @author Christian Scheiblich
   * 31.01.2014 21:15:03
   *
   * @param wavelet
   * @param levelOfSpace
   * @param steps
   */
  protected WaveletTransform( Wavelet wavelet, int levelOfSpace, int steps ) { 
	  
    _wavelet = wavelet;
	    
	_levelOfSpace = levelOfSpace;
	    
	_steps = steps;
	    
  }
  
  /**
   * A Wavelet transform that is reduced to some minor steps depending on the
   * supported TransformMode object that handles how to treat the given data.
   * 
   * @author Christian Scheiblich
   * date Feb 7, 2013 3:25:01 PM
   *
   * @param wavelets
   * @param transformMode
   * @throws JWaveException
   */
  protected WaveletTransform( Wavelet wavelet, TransformMode transformMode ) {
    
    _wavelet = wavelet;
    
    _transformMode = transformMode;
    
    _steps = -1; // allows for the maximum number of steps
    
  }
  
  /**
   * The method checks the configuration of the Wavelet transform.
   * 
   * @author Christian Scheiblich
   * date Feb 13, 2013 1:05:41 PM
   *
   * @throws JWaveException
   */
  protected void checkConfig( ) throws JWaveException {
    
    if( _wavelet == null )
      throw new JWaveError( "WaveletTransfrom#checkConfig -- given object Wavelet is null" );
    
    if( _steps == 0 || _steps < -1 ) // allowed: {-1,1,2,3, ...}
      throw new JWaveFailure( "WaveletTransfrom#checkConfig -- given steps are not valid: " + _steps );
    
  }
  
}
