/**
 * JWave
 *
 * Copyright 2010-2013 Christian Scheiblich
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
 * This file TransformModeLeveling.java is part of JWave.
 *
 * @author Christian Scheiblich
 * contact cscheiblich@gmail.com
 * date Feb 7, 2013 3:04:09 PM
 *
 */
package math.jwave.transforms.modes;

import math.jwave.exc.JWaveException;
import math.jwave.exc.JWaveFailure;

/**
 * Class for indicating that there will be a starting level of transform
 * and an end level of the transform, which allows for specialized
 * filtering by the transform.
 * 
 * @author Christian Scheiblich
 * date Feb 7, 2013 3:04:09 PM
 *
 */
public class TransformModeLeveling extends TransformMode {
  
  /**
   * Hand to integer to allow for going from a certain start level to a certain
   * end level. This somehow should match to the provided data but in other case
   * this is helpful to filter data at different start level of the used wavelet.
   * Which comes around as a coarser or finer filtering as selected by the level.
   * 
   * @author Christian Scheiblich
   * date Feb 7, 2013 2:52:09 PM
   *
   * @param fromLevel start level of the transform 
   * @param toLevel stop level of the transform
   * @throws JWaveException thrown if fromLevel equals toLevel are the same
   */
  public TransformModeLeveling( int fromLevel, int toLevel ) throws JWaveException {
    
    if( toLevel == fromLevel )
      throw new JWaveFailure( "fromLevel equals toLevel - not possible" );
    
    _fromLevel = fromLevel;
    _toLevel = toLevel;
    
    if( _fromLevel < _toLevel )
      _steps = _toLevel - _fromLevel; // positive steps
      
    if( _toLevel < _fromLevel )
      _steps = _fromLevel - _toLevel; // positive steps
      
  }
  
}
