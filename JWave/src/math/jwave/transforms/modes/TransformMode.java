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
 * This file TransformMode.java is part of JWave.
 *
 * @author Christian Scheiblich
 * contact cscheiblich@gmail.com
 * date Feb 7, 2013 1:43:13 PM
 *
 */
package math.jwave.transforms.modes;

import math.jwave.exc.JWaveException;

/**
 * Base class to specify a transform mode: e. g. if JWave should do
 * only two steps from normal to hilbert space or from hilbert space
 * 3 steps towards normal space. Therefore, a class is necessary to
 * solve the problem that JWave can start at data of level 2 and go
 * to a level 3 which means, that the algorithm has to be started
 * in the mid of its full job and has to be stopped earlier again.
 * 
 * @author Christian Scheiblich
 * date Feb 7, 2013 1:43:13 PM
 *
 */
public abstract class TransformMode {
  
  /**
   * Storing how many steps the Algorithm should do in a later
   * configured direction.
   * 
   * @author Christian Scheiblich
   * date Feb 7, 2013 1:47:41 PM
   *
   */
  protected int _steps;
  
  /**
   * The ending level of the transform that can be either smaller
   * or greater as the starting level given by fromLevel integer.
   * 
   * @author Christian Scheiblich
   * date Feb 7, 2013 3:05:22 PM
   *
   */
  protected int _toLevel;
  
  /**
   * The starting level of the transform that can be either smaller
   * or greater as the starting level given by toLevel integer.
   * 
   * @author Christian Scheiblich
   * date Feb 7, 2013 3:05:24 PM
   *
   */
  protected int _fromLevel;
  
  /**
   * Base constructor setting internal steps integer to minus one
   * to signal that it is unused and that there are full steps allowed
   * for the algorithm so far. 
   * 
   * @author Christian Scheiblich
   * date Feb 7, 2013 1:52:06 PM
   *
   * @throws JWaveException
   */
  public TransformMode( ) throws JWaveException {
    
    _steps = -1; // not used
    _toLevel = -1; // not used
    _fromLevel = -1; // not used
  }
  
}
