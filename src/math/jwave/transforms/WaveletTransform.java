/**
 * JWave Copyright 2009-2013 Christian Scheiblich Licensed under the Apache
 * License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License. This file
 * WaveletTransform.java is part of JWave.
 * 
 * @author Christian Scheiblich contact cscheiblich@gmail.com date Feb 6, 2013
 *         4:50:31 PM
 */
package math.jwave.transforms;

import math.jwave.exc.JWaveError;
import math.jwave.exc.JWaveException;
import math.jwave.exc.JWaveFailure;
import math.jwave.transforms.wavelets.Wavelet;

/**
 * @author Christian Scheiblich date Feb 6, 2013 4:50:31 PM
 */
public abstract class WaveletTransform extends BasicTransform {

  /**
   * The used wavelet for transforming
   * 
   * @author Christian Scheiblich date Feb 6, 2013 4:51:51 PM
   */
  protected Wavelet _wavelet;

  /**
   * @author Christian Scheiblich date Feb 6, 2013 4:51:01 PM
   * @param wavelet
   * @throws JWaveFailure if given object is null or not of type wavelet
   */
  protected WaveletTransform( Wavelet wavelet ) throws JWaveFailure {
    
    if( _wavelet == null )
      throw new JWaveFailure( "given object is null!" );
    
    if( !( wavelet instanceof Wavelet ) )
      throw new JWaveFailure( "given object is not of type Wavelet object" );
    
    _wavelet = wavelet;
              
  }

}
