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
   * Returns a double array with the coefficients of the wavelet function.
   * 
   * @author Christian Scheiblich
   * @date 15.02.2014 22:11:25
   * @return double array keeping the coeffs.
   */
  public double[ ] getWaveletCoeffs( ) {

    double[ ] coeffs = new double[ _coeffs.length ];

    for( int c = 0; c < _coeffs.length; c++ )
      coeffs[ c ] = _coeffs[ c ];

    return coeffs;

  } // getWaveletCoeffs

  /**
   * Returns a double array with the coefficients of the scaling function.
   * 
   * @author Christian Scheiblich
   * @date 15.02.2010 22:11:42
   * @return double array keeping the scales.
   */
  public double[ ] getScalingCoeffs( ) {

    double[ ] scales = new double[ _scales.length ];

    for( int s = 0; s < _scales.length; s++ )
      scales[ s ] = _scales[ s ];

    return scales;

  } // getScalingCoeffs

} // class
