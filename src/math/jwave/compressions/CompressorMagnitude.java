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
package math.jwave.compressions;

import math.jwave.exceptions.JWaveException;

/**
 * Compression algorithm is adding up all magnitudes of an array, a matrix, or a
 * space and dividing this absolute value by the number of given data samples.
 * 
 * @author Christian Scheiblich (cscheiblich@gmail.com)
 * @date 20.02.2014 23:56:09
 */
public class CompressorMagnitude extends Compressor {

  /**
   * Member variable for remembering the calculated magnitude.
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 21.02.2014 00:02:52
   */
  protected double _magnitude;

  /**
   * Threshold is set to one, which should always guarantee a rather good
   * compression result.
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 20.02.2014 23:56:09
   */
  public CompressorMagnitude( ) {

    _magnitude = 0.;

    _threshold = 1.;

  } // CompressorMagnitude

  /**
   * Threshold is set a chosen; value 0 means no compression.
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 20.02.2014 23:56:09
   * @param threshold
   *          has to be positive value starting at 0 - 0 means no compression.
   * @throws JWaveException
   */
  public CompressorMagnitude( double threshold ) throws JWaveException {

    super( threshold );

    _magnitude = 0.;

  } // CompressorMagnitude

  /*
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 20.02.2014 23:56:09 (non-Javadoc)
   * @see math.jwave.compressions.Compressor#compress(double[])
   */
  @Override protected double[ ] compress( double[ ] arrHilb ) {

    _magnitude = 0.;

    int arrHilbSize = arrHilb.length;

    double[ ] arrComp = new double[ arrHilbSize ];

    for( int i = 0; i < arrHilbSize; i++ )
      _magnitude += Math.abs( arrHilb[ i ] );

    for( int i = 0; i < arrHilbSize; i++ )
      if( Math.abs( arrHilb[ i ] ) >= _magnitude * _threshold )
        arrComp[ i ] = arrHilb[ i ];
      else
        arrComp[ i ] = 0.;

    return arrComp;

  } // compress

  /*
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 20.02.2014 23:56:09 (non-Javadoc)
   * @see math.jwave.compressions.Compressor#compress(double[][])
   */
  @Override protected double[ ][ ] compress( double[ ][ ] matHilb ) {

    _magnitude = 0.;

    int matHilbNoOfRows = matHilb.length;
    int matHilbNoOfCols = matHilb[ 0 ].length;

    double[ ][ ] matComp = new double[ matHilbNoOfRows ][ matHilbNoOfCols ];

    for( int i = 0; i < matHilbNoOfRows; i++ )
      for( int j = 0; j < matHilbNoOfCols; j++ )
        _magnitude += Math.abs( matHilb[ i ][ j ] );

    for( int i = 0; i < matHilbNoOfRows; i++ )
      for( int j = 0; j < matHilbNoOfCols; j++ )
        if( Math.abs( matHilb[ i ][ j ] ) >= _magnitude * _threshold )
          matComp[ i ][ j ] = matHilb[ i ][ j ];
        else
          matComp[ i ][ j ] = 0.;

    return matComp;

  } // compress

  /*
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 20.02.2014 23:56:09 (non-Javadoc)
   * @see math.jwave.compressions.Compressor#compress(double[][][])
   */
  @Override protected double[ ][ ][ ] compress( double[ ][ ][ ] spcHilb ) {

    _magnitude = 0.;

    int matHilbNoOfRows = spcHilb.length;
    int matHilbNoOfCols = spcHilb[ 0 ].length;
    int matHilbNoOfLvls = spcHilb[ 0 ][ 0 ].length;

    double[ ][ ][ ] spcComp =
        new double[ matHilbNoOfRows ][ matHilbNoOfCols ][ matHilbNoOfLvls ];

    for( int i = 0; i < matHilbNoOfRows; i++ )
      for( int j = 0; j < matHilbNoOfCols; j++ )
        for( int k = 0; k < matHilbNoOfLvls; k++ )
          _magnitude += Math.abs( spcHilb[ i ][ j ][ k ] );

    for( int i = 0; i < matHilbNoOfRows; i++ )
      for( int j = 0; j < matHilbNoOfCols; j++ )
        for( int k = 0; k < matHilbNoOfLvls; k++ )
          if( Math.abs( spcHilb[ i ][ j ][ k ] ) >= _magnitude * _threshold )
            spcComp[ i ][ j ][ k ] = spcHilb[ i ][ j ][ k ];
          else
            spcComp[ i ][ j ][ k ] = 0.;

    return spcComp;

  } // compress

} // CompressorMagnitude
