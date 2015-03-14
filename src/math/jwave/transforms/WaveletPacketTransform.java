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
package math.jwave.transforms;

import math.jwave.exceptions.JWaveException;
import math.jwave.exceptions.JWaveFailure;
import math.jwave.transforms.wavelets.Wavelet;

/**
 * Base class for the forward and reverse Wavelet Packet Transform (WPT) also
 * called Wavelet Packet Decomposition (WPD) using a specified Wavelet by
 * inheriting class.
 * 
 * @date 23.02.2010 13:44:05
 * @author Christian Scheiblich (cscheiblich@gmail.com)
 */
public class WaveletPacketTransform extends WaveletTransform {

  /**
   * Constructor receiving a Wavelet object.
   * 
   * @date 23.02.2010 13:44:05
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @param wavelet
   *          object of type Wavelet; Haar1, Daubechies2, Coiflet1, ...
   * @throws JWaveFailure
   *           if object is null or not of type wavelet
   */
  public WaveletPacketTransform( Wavelet wavelet ) {

    super( wavelet );
    
    _name = "Wavelet Packet Transform";

  } // WaveletPacketTransform

  /**
   * Implementation of the 1-D forward wavelet packet transform for arrays of
   * dim N by filtering with the longest wavelet first and then always with both
   * sub bands -- low and high (approximation and details) -- by the next
   * smaller wavelet.
   * 
   * @date 23.02.2010 13:44:05
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @throws JWaveException
   * @see math.jwave.transforms.BasicTransform#forward(double[])
   */
  @Override public double[ ] forward( double[ ] arrTime ) throws JWaveException {

    if( !_mathToolKit.isBinary( arrTime.length ) )
      throw new JWaveFailure(
          "given array length is not 2^p = 1, 2, 4, 8, 16, 32, .. "
              + "please use the Ancient Egyptian Decomposition for any other array length!" );

    double[ ] arrHilb = new double[ arrTime.length ];
    for( int i = 0; i < arrTime.length; i++ )
      arrHilb[ i ] = arrTime[ i ];

    int k = arrTime.length;

    int h = arrTime.length;

    int transformWavelength = _wavelet.getTransformWavelength( ); // 2, 4, 8, 16, 32, ...

    if( h >= transformWavelength ) {

      while( h >= transformWavelength ) {

        int g = k / h; // 1 -> 2 -> 4 -> 8 -> ...

        for( int p = 0; p < g; p++ ) {

          double[ ] iBuf = new double[ h ];

          for( int i = 0; i < h; i++ )
            iBuf[ i ] = arrHilb[ i + ( p * h ) ];

          double[ ] oBuf = _wavelet.forward( iBuf, h );

          for( int i = 0; i < h; i++ )
            arrHilb[ i + ( p * h ) ] = oBuf[ i ];

        } // packets

        h = h >> 1;

      } // levels

    } // if

    return arrHilb;
  } // forward

  /**
   * Implementation of the 1-D reverse wavelet packet transform for arrays of
   * dim N by filtering with the smallest wavelet for all sub bands -- low and
   * high bands (approximation and details) -- and the by the next greater
   * wavelet combining two smaller and all other sub bands.
   * 
   * @date 23.02.2010 13:44:05
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @throws JWaveException
   * @see math.jwave.transforms.BasicTransform#reverse(double[])
   */
  @Override public double[ ] reverse( double[ ] arrHilb ) throws JWaveException {

    if( !_mathToolKit.isBinary( arrHilb.length ) )
      throw new JWaveFailure(
          "given array length is not 2^p = 1, 2, 4, 8, 16, 32, .. "
              + "please use the Ancient Egyptian Decomposition for any other array length!" );

    double[ ] arrTime = new double[ arrHilb.length ];

    for( int i = 0; i < arrHilb.length; i++ )
      arrTime[ i ] = arrHilb[ i ];

    int transformWavelength = _wavelet.getTransformWavelength( ); // 2, 4, 8, 16, 32, ...

    int k = arrTime.length;

    int h = transformWavelength;
    //    if( !_mathToolKit.isBinary( h ) )
    //      for( h = 2; h <= transformWavelength; h *= 2 ) {}
    // fixed h = h << 1; // 6 -> 8, 10 -> 16

    if( arrHilb.length >= transformWavelength ) {

      while( h <= arrTime.length && h >= transformWavelength ) {

        int g = k / h; // ... -> 8 -> 4 -> 2 -> 1

        for( int p = 0; p < g; p++ ) {

          double[ ] iBuf = new double[ h ];

          for( int i = 0; i < h; i++ )
            iBuf[ i ] = arrTime[ i + ( p * h ) ];

          double[ ] oBuf = _wavelet.reverse( iBuf, h );

          for( int i = 0; i < h; i++ )
            arrTime[ i + ( p * h ) ] = oBuf[ i ];

        } // packets

        h = h << 1;

      } // levels

    } // if

    return arrTime;

  } // reverse

} // class
