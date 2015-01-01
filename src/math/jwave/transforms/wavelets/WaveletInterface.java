/**
 * JWave is distributed under the MIT License (MIT); this file is part of.
 *
 * Copyright (c) 2008-2015 Christian Scheiblich (cscheiblich@gmail.com)
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package math.jwave.transforms.wavelets;

/**
 * Interface for the Wavelet class
 * 
 * @author Christian Scheiblich (cscheiblich@gmail.com) 10.02.2014 21:01:32
 */
public interface WaveletInterface {

  /**
   * Returns a String keeping the name of the current Wavelet.
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 17.08.2014 10:59:13
   * @return String keeping the name of the wavelet
   */
  public String getName( );

  /**
   * Shifts scaling and wavelet over some hilbert in forward manners.
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 15.02.2014 21:01:56
   * @param values
   * @return
   */
  public double[ ] forward( double[ ] arrTime, int arrTimeLength );

  /**
   * Shifts scaling and wavelet over some hilbert in reverse manners.
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 15.02.2014 21:02:23
   * @param values
   * @return
   */
  public double[ ] reverse( double[ ] arrHilb, int arrTimeLength );

  /**
   * Returns the wavelength of the so called mother wavelet or scaling function.
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 15.02.2014 22:06:12
   * @return
   */
  public int getMotherWavelength( );

  /**
   * Returns the minimal necessary wavelength for a signal that can be
   * transformed by this wavelet.
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 15.02.2014 22:08:43
   * @return integer representing minimal wavelength of the input signal that
   *         should be transformed by this wavelet.
   */
  public int getTransformWavelength( );

  /**
   * Returns a copy of the scaling (low pass filter) coefficients of the
   * decomposition.
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 15.02.2010 22:11:42
   * @return array of length of the mother wavelet wavelength keeping the
   *         decomposition low pass filter coefficients
   */
  public double[ ] getScalingDeComposition( );

  /**
   * Returns a copy of the wavelet (low pass filter) coefficients of the
   * decomposition.
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 15.02.2014 22:11:25
   * @return array of length of the mother wavelet wavelength keeping the
   *         decomposition high pass filter coefficients
   */
  public double[ ] getWaveletDeComposition( );

  /**
   * Returns a copy of the scaling (low pass filter) coefficients of the
   * reconstruction.
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 16.02.2014 10:35:11
   * @return array of length of the mother wavelet wavelength keeping the
   *         reconstruction low pass filter coefficients
   */
  public double[ ] getScalingReConstruction( );

  /**
   * Returns a copy of the wavelet (high pass filter) coefficients of the
   * reconstruction.
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 16.02.2014 10:35:09
   * @return array of length of the mother wavelet wavelength keeping the
   *         reconstruction high pass filter coefficients
   */
  public double[ ] getWaveletReConstruction( );

} // WaveletInterface