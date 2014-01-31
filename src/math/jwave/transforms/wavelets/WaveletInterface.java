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
 * This file Wavelet.java is part of JWave.
 *
 * @author Christian Scheiblich
 * date 23.02.2010 05:42:23
 * contact cscheiblich@gmail.com
 */
package math.jwave.transforms.wavelets;

/**
 * 
 * Interface for the base class of an wavelet.
 *
 * @date 30 juin 2011 10:31:38
 * @author Pol Kennel
 * @date 22.01.2012 18:20:33
 * @author Christian Scheiblich
 * 
 */
public interface WaveletInterface {
  
  public double[ ] forward( double[ ] values );
  
  public double[ ] reverse( double[ ] values );
  
  public int getWaveLength( );
  
  public double[ ] getCoeffs( );
  
  public double[ ] getScales( );
  
}
