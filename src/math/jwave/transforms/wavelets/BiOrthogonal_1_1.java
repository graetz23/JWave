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
 * BiOrthogonal Wavelet of type 1.1 - One vanishing moment in wavelet function
 * and one vanishing moment in scaling function.
 * 
 * @author Christian Scheiblich
 * @date 16.02.2014 10:15:11
 */
public class BiOrthogonal_1_1 extends Wavelet {

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
   * Already orthonormal coefficients taken from Filip Wasilewski's webpage
   * http://wavelets.pybytes.com/wavelet/bior1.1/ Thanks!
   * 
   * @author Christian Scheiblich
   * @date 16.02.2014 10:15:11
   */
  public BiOrthogonal_1_1( ) {

    _transformWavelength = 2; // minimal wavelength of input signal

    _motherWavelength = 2; // wavelength of mother wavelet

    _coeffsDeCom = new double[ _motherWavelength ];
    _coeffsDeCom[ 0 ] = 0.7071067811865476;
    _coeffsDeCom[ 1 ] = 0.7071067811865476;

    _scalesDeCom = new double[ _motherWavelength ];
    _scalesDeCom[ 0 ] = -0.7071067811865476;
    _scalesDeCom[ 1 ] = 0.7071067811865476;

    _coeffsReCon = new double[ _motherWavelength ];
    _coeffsReCon[ 0 ] = 0.7071067811865476;
    _coeffsReCon[ 1 ] = 0.7071067811865476;

    _scalesReCon = new double[ _motherWavelength ];
    _scalesReCon[ 0 ] = 0.7071067811865476;
    _scalesReCon[ 1 ] = -0.7071067811865476;

  } // BiOrthogonal_1_1

  /*
   * @author tucker
   * @date 16.02.2014 10:26:31 (non-Javadoc)
   * @see math.jwave.transforms.wavelets.Wavelet#forward(double[], int)
   */
  @Override public double[ ] forward( double[ ] arrTime, int arrTimeLength ) {

    double[ ] arrHilb = new double[ arrTimeLength ];

    int h = arrHilb.length >> 1; // .. -> 64 -> 32 -> 16 -> 8 -> 4 -> 2 -> 1

    for( int i = 0; i < h; i++ ) {

      arrHilb[ i ] = arrHilb[ i + h ] = 0.; // set to zero before sum up

      for( int j = 0; j < _motherWavelength; j++ ) {

        int k = ( i * 2 ) + j; // int k = ( i << 1 ) + j;

        while( k >= arrHilb.length )
          k -= arrHilb.length; // circulate over arrays if scaling and wavelet are too long 

        arrHilb[ i ] += arrTime[ k ] * _scalesDeCom[ j ]; // low pass filter - energy
        arrHilb[ i + h ] += arrTime[ k ] * _coeffsDeCom[ j ]; // high pass filter - details

      } // { scaling coefs | wavelet coefs }

    } // h = 2^(p-1) | p = { 1, 2, .., N } 

    return arrHilb;

  } // forward

  /*
   * @author tucker
   * @date 16.02.2014 10:26:31 (non-Javadoc)
   * @see math.jwave.transforms.wavelets.Wavelet#reverse(double[], int)
   */
  @Override public double[ ] reverse( double[ ] arrHilb, int arrHilbLength ) {
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
            arrHilb[ i ] * _scalesReCon[ j ] + arrHilb[ i + h ]
                * _coeffsReCon[ j ];

      } // // { scaling coefs + wavelet coefs }

    } // h = 2^(p-1) | p = { 1, 2, .., N }

    return arrTime;

  } // reverse

} // BiOrthogonal_1_1