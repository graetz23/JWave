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
 * @author Christian Scheiblich
 * date 23.02.2008 17:42:23
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
   * Shifts scaling and wavelet over some hilbert in forward manners.
   * 
   * @author Christian Scheiblich
   * @date 15.02.2014 21:01:56
   * @param values
   * @return
   */
  public double[ ] forward( double[ ] arrTime, int arrTimeLength );

  /**
   * Shifts scaling and wavelet over some hilbert in reverse manners.
   * 
   * @author Christian Scheiblich
   * @date 15.02.2014 21:02:23
   * @param values
   * @return
   */
  public double[ ] reverse( double[ ] arrHilb, int arrTimeLength );

  /**
   * Returns the wavelength of the base or so called mother wavelet; the minimal
   * length for input signals. If the wavelength is 6 as for the Daubechie
   * Wavelet the minimal length is the next binary of length 8.
   * 
   * @author Christian Scheiblich
   * @date 15.02.2014 22:06:12
   * @return
   */
  public int getMotherWavelength( );

  /**
   * Returns the minimal necessary wavelength for a signal that can be
   * transformed by this wavelet.
   * 
   * @author Christian Scheiblich
   * @date 15.02.2014 22:08:43
   * @return integer representing minimal wavelength of the input signal that
   *         should be transformed by this wavelet.
   */
  public int getTransformWavelength( );

  /**
   * Returns a copy of the wavelet coefficients.
   * 
   * @author Christian Scheiblich
   * @date 15.02.2014 22:11:25
   * @return
   */
  public double[ ] getWaveletCoeffs( );

  /**
   * Returns a copy of the scaling coefficients.
   * 
   * @author Christian Scheiblich
   * @date 15.02.2010 22:11:42
   * @return
   */
  public double[ ] getScalingCoeffs( );

} // WaveletInterface
