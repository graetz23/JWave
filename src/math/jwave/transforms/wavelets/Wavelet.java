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
 * @date 23.05.2008 17:42:23
 * cscheiblich@gmail.com
 */
package math.jwave.transforms.wavelets;

/**
 * Basic class for one wavelet keeping coefficients of the wavelet function, the
 * scaling function, the base wavelength, the forward transform method, and the
 * reverse transform method.
 * 
 * @date 10.02.2010 08:54:48
 * @author Christian Scheiblich
 */
public abstract class Wavelet implements WaveletInterface {

  /**
   * The wavelength of the base or so called mother wavelet and its matching
   * scaling function.
   */
  protected int _motherWavelength;

  /**
   * The minimal wavelength of a signal that can be transformed
   */
  protected int _transformWavelength;

  /**
   * The coefficients of the mother wavelet (low pass filter) for decomposition.
   */
  protected double[ ] _coeffsDeCom;

  /**
   * The coefficients of the mother scaling (high pass filter) for
   * decomposition.
   */
  protected double[ ] _scalesDeCom;

  /**
   * The coefficients of the mother wavelet (low pass filter) for
   * reconstruction.
   */
  protected double[ ] _coeffsReCon;

  /**
   * The coefficients of the mother scaling (high pass filter) for
   * reconstruction.
   */
  protected double[ ] _scalesReCon;

  /**
   * The coefficients of the base wavelet; wavelet function.
   */
  protected double[ ] _coeffs;

  /**
   * The coefficients of the base scales; scaling function.
   */
  protected double[ ] _scales;

  /**
   * Constructor; predefine members to default values
   * 
   * @date 15.02.2014 22:16:27
   * @author Christian Scheiblich
   */
  public Wavelet( ) {

    _motherWavelength = 0;

    _transformWavelength = 0;

    _coeffs = null;

    _scales = null;

  } // Wavelet

  /**
   * Performs the forward transform for the given array from time domain to
   * Hilbert domain and returns a new array of the same size keeping
   * coefficients of Hilbert domain and should be of length 2 to the power of p
   * -- length = 2^p where p is a positive integer.
   * 
   * @date 10.02.2010 08:18:02
   * @author Christian Scheiblich
   * @param arrTime
   *          array keeping time domain coefficients
   * @param arrTimeLength
   *          is necessary, due to working only on a part of arrTime not on the
   *          full length of arrTime!
   * @return coefficients represented by frequency domain
   */
  public double[ ] forward( double[ ] arrTime, int arrTimeLength ) {

    double[ ] arrHilb = new double[ arrTimeLength ];

    int h = arrHilb.length >> 1; // .. -> 64 -> 32 -> 16 -> 8 -> 4 -> 2 -> 1

    for( int i = 0; i < h; i++ ) {

      arrHilb[ i ] = arrHilb[ i + h ] = 0.; // set to zero before sum up

      for( int j = 0; j < _motherWavelength; j++ ) {

        int k = ( i * 2 ) + j; // int k = ( i << 1 ) + j;

        while( k >= arrHilb.length )
          k -= arrHilb.length; // circulate over arrays if scaling and wavelet are too long 

        arrHilb[ i ] += arrTime[ k ] * _scales[ j ]; // low pass filter - energy
        arrHilb[ i + h ] += arrTime[ k ] * _coeffs[ j ]; // high pass filter - details

      } // { scaling coefs | wavelet coefs }

    } // h = 2^(p-1) | p = { 1, 2, .., N } 

    return arrHilb;

  } // forward

  /**
   * Performs the reverse transform for the given array from Hilbert domain to
   * time domain and returns a new array of the same size keeping coefficients
   * of time domain and should be of length 2 to the power of p -- length = 2^p
   * where p is a positive integer.
   * 
   * @date 10.02.2010 08:19:24
   * @author Christian Scheiblich
   * @param arrHilb
   *          array keeping frequency domain coefficients
   * @param arrHilbLength
   *          is necessary, due to working only on a part of arrHilb not on the
   *          full length of arrHilb!
   * @return coefficients represented by time domain
   */
  public double[ ] reverse( double[ ] arrHilb, int arrHilbLength ) {

    double[ ] arrTime = new double[ arrHilbLength ];

    for( int i = 0; i < arrTime.length; i++ )
      arrTime[ i ] = 0.;

    int h = arrTime.length >> 1; // .. -> 64 -> 32 -> 16 -> 8 -> 4 -> 2 -> 1

    for( int i = 0; i < h; i++ ) {

      for( int j = 0; j < _motherWavelength; j++ ) {

        int k = ( i * 2 ) + j; // int k = ( i << 1 ) + j;

        while( k >= arrTime.length )
          k -= arrTime.length; // circulate over arrays if scaling and wavelet are too long

        // adding up details times energy (approximation)
        arrTime[ k ] +=
            arrHilb[ i ] * _scales[ j ] + arrHilb[ i + h ] * _coeffs[ j ];

      } // // { scaling coefs + wavelet coefs }

    } // h = 2^(p-1) | p = { 1, 2, .., N }

    return arrTime;

  } // reverse

  /**
   * Returns the wavelength of the base or so called mother wavelet.
   * 
   * @author Christian Scheiblich
   * @date 15.02.2014 22:06:12
   * @return the minimal wavelength for the mother wavelet
   */
  public int getMotherWavelength( ) {

    return _motherWavelength;

  } // getMotherWavelength

  /**
   * Returns the minimal necessary wavelength for a signal that can be
   * transformed by this wavelet.
   * 
   * @author Christian Scheiblich
   * @date 15.02.2014 22:08:43
   * @return integer representing minimal wavelength of the input signal that
   *         should be transformed by this wavelet.
   */
  public int getTransformWavelength( ) {

    return _transformWavelength;

  } // getTransformWavelength

  /**
   * Returns a copy of the wavelet (low pass filter) coefficients of
   * decomposition.
   * 
   * @author Christian Scheiblich
   * @date 15.02.2014 22:11:25
   * @return array of length of the mother wavelet wavelength keeping the
   *         decomposition low pass filter coefficients
   */
  public double[ ] getWaveletCoeffsDeCom( ) {

    double[ ] coeffsDeCom = new double[ _coeffsDeCom.length ];

    for( int i = 0; i < _coeffsDeCom.length; i++ )
      coeffsDeCom[ i ] = _coeffsDeCom[ i ];

    return coeffsDeCom;

  } // getWaveletCoeffsDeCom

  /**
   * Returns a copy of the scaling (high pass filter) coefficients of
   * decomposition.
   * 
   * @author Christian Scheiblich
   * @date 15.02.2010 22:11:42
   * @return array of length of the mother wavelet wavelength keeping the
   *         decomposition high pass filter coefficients
   */
  public double[ ] getScalingCoeffsDeCom( ) {

    double[ ] scalesDeCom = new double[ _scalesDeCom.length ];

    for( int i = 0; i < _scalesDeCom.length; i++ )
      scalesDeCom[ i ] = _scalesDeCom[ i ];

    return scalesDeCom;

  } // getScalingCoeffsDeCom

  /**
   * Returns a copy of the wavelet (low pass filter) coefficients of
   * reconstruction.
   * 
   * @author Christian Scheiblich
   * @date 16.02.2014 10:35:09
   * @return array of length of the mother wavelet wavelength keeping the
   *         reconstruction low pass filter coefficients
   */
  public double[ ] getWaveletCoeffsReCon( ) {

    double[ ] coeffsReCon = new double[ _coeffsReCon.length ];

    for( int i = 0; i < _coeffsReCon.length; i++ )
      coeffsReCon[ i ] = _coeffsReCon[ i ];

    return coeffsReCon;

  } // getWaveletCoeffsReCon

  /**
   * Returns a copy of the scaling (high pass filter) coefficients of
   * reconstruction.
   * 
   * @author Christian Scheiblich
   * @date 16.02.2014 10:35:11
   * @return array of length of the mother wavelet wavelength keeping the
   *         reconstruction high pass filter coefficients
   */
  public double[ ] getScalingCoeffsReCon( ) {

    double[ ] scalesReCon = new double[ _scalesReCon.length ];

    for( int i = 0; i < _scalesReCon.length; i++ )
      scalesReCon[ i ] = _scalesReCon[ i ];

    return scalesReCon;

  } // getScalingCoeffsReCon

} // class
