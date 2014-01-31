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
 * This file TransformModeStepping.java is part of JWave.
 *
 * @author Christian Scheiblich
 * contact cscheiblich@gmail.com
 * date Feb 7, 2013 3:02:56 PM
 *
 */
package math.jwave.transforms.modes;

import math.jwave.exc.JWaveException;
import math.jwave.exc.JWaveFailure;

/**
 * Class for indicating that there will be a stepping from either the
 * base level of the normal space or taking the Hilbert space as a
 * base level to step away the given number of steps from each base.
 * 
 * @author Christian Scheiblich
 * date Feb 7, 2013 3:02:56 PM
 *
 */
public class TransformModeStepping extends TransformMode {
  
  /**
   * Setting an steps integer to allocated how many steps the transform
   * algorithm should do. Depending on the established object it either
   * goes from normal to Hilbert or from Hilbert to normal.
   * 
   * @author Christian Scheiblich
   * date Feb 7, 2013 1:53:17 PM
   *
   * @param steps a positive number telling how many levels the transform should do
   * @throws JWaveException throws a failure if steps ist smaller than one
   */
  public TransformModeStepping( int steps ) throws JWaveException {
    
    if( steps < 1 )
      throw new JWaveFailure( "given integer steps is smaller than 1 - no steps possible" );
    
    _steps = steps;
    
    _toLevel = -1; // not used
    _fromLevel = -1; // not used
    
  }
  
}
