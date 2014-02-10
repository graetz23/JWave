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
 * @author Christian Scheiblich
 * date 23.02.2010 05:42:23
 * contact cscheiblich@gmail.com
 */
package math.jwave.transforms.wavelets;

/**
 * Interface for the Wavelet class
 * 
 * @author Christian Scheiblich 10.02.2014 21:01:32
 */
public interface WaveletInterface {

  /**
   * Shifts the wavelet over the signal
   * 
   * @author Christian Scheiblich 10.02.2014 21:01:56
   * @param values
   * @return
   */
  public double[ ] forward( double[ ] arrTime );

  /**
   * Shifts the wavelet over the signal
   * 
   * @author Christian Scheiblich 10.02.2014 21:02:23
   * @param values
   * @return
   */
  public double[ ] reverse( double[ ] arrHilb );

  /**
   * Returns the wavelength of the base or so called mother wavelet; the minimal
   * length for input signals. If the wavelength is 6 as for the Daubechie
   * Wavelet the minimal length is the next binary of length 8.
   * 
   * @author Christian Scheiblich 10.02.2014 21:03:13
   * @return
   */
  public int getWaveLength( );

  /**
   * Returns a copy of the wavelet coefficients.
   * 
   * @author Christian Scheiblich 10.02.2014 21:03:52
   * @return
   */
  public double[ ] getCoeffs( );

  /**
   * Returns a copy of the scaling coefficients.
   * 
   * @author Christian Scheiblich 10.02.2014 21:04:17
   * @return
   */
  public double[ ] getScales( );

} // WaveletInterface
