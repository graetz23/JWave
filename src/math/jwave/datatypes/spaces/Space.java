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
package math.jwave.datatypes.spaces;

import math.jwave.exceptions.JWaveException;
import math.jwave.exceptions.JWaveFailure;

/**
 * A space of Data; 3-D organized by (0,0,0) .. (noOfRows,noOfCols,noOfBlocks).
 * 
 * @author Christian Scheiblich (cscheiblich@gmail.com)
 * @date 16.05.2015 14:47:21
 */
public abstract class Space {

  /**
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 16.05.2015 15:46:06
   */
  protected int _noOfRows;

  /**
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 16.05.2015 15:46:07
   */
  protected int _noOfCols;

  /**
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 16.05.2015 15:46:08
   */
  protected int _noOfLvls;

  /**
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 16.05.2015 14:47:21
   */
  public Space( int noOfRows, int noOfCols, int noOfLvls ) {

    _noOfRows = noOfRows;
    _noOfCols = noOfCols;
    _noOfLvls = noOfLvls;

  } // Space

  /**
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 16.05.2015 15:47:27
   * @return the _noOfRows
   */
  public int getNoOfRows( ) {
    return _noOfRows;
  } // getNoOfRows

  /**
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 16.05.2015 15:47:27
   * @return the _noOfCols
   */
  public int getNoOfCols( ) {
    return _noOfCols;
  } // getNoOfCols

  /**
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 16.05.2015 15:47:27
   * @return the _noOfLvls
   */
  public int getNoOfLvls( ) {
    return _noOfLvls;
  } // getNoOfLvls

  /**
   * Check the given input of i as position in number of levels, otherwise throw
   * a failure (exception) if i as position is not valid.
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 16.05.2015 15:49:28
   * @param k
   *          from 0 to noOfLvls-1
   * @throws JWaveException
   *           if i is out of bounds
   */
  protected void check( int k ) throws JWaveException {

    if( k < 0 )
      throw new JWaveFailure( "Space - i is smaller than zero" );

    if( k == _noOfLvls )
      throw new JWaveFailure( "Space - i is equal to noOfLvls: " + _noOfLvls );

    if( k > _noOfLvls )
      throw new JWaveFailure( "Space - i is greater than noOfLvls: "
          + _noOfLvls );

  } // check

  /**
   * Check the given input of (j,k) and throws a failure (exception) if position
   * is not valid.
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 16.05.2015 15:52:00
   * @param j
   *          from 0 to noOfCols-1
   * @param k
   *          from 0 to noOfLvls-1
   * @throws JWaveException
   *           if (j,k) is out of bounds
   */
  protected void check( int j, int k ) throws JWaveException {

    if( j < 0 )
      throw new JWaveFailure( "Space - i is smaller than zero" );

    if( j == _noOfCols )
      throw new JWaveFailure( "Space - i is equal to noOfCols: " + _noOfCols );

    if( j > _noOfCols )
      throw new JWaveFailure( "Space - i is greater than noOfCols: "
          + _noOfCols );

    check( k ); // for checking k, while j is in every Block object stored.

  } // check

  /**
   * Check the given input of (i,j,k) and throws a failure (exception) if
   * position is not valid.
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 16.05.2015 15:54:08
   * @param i
   *          from 0 to noOfRows-1
   * @param j
   *          from 0 to noOfCols-1
   * @param k
   *          from 0 to noOfLvls-1
   * @throws JWaveException
   *           if (i,j,k) is out of bounds
   */
  protected void check( int i, int j, int k ) throws JWaveException {

    if( i < 0 )
      throw new JWaveFailure( "Space - i is smaller than zero" );

    if( i == _noOfRows )
      throw new JWaveFailure( "Space - i is equal to noOfRows: " + _noOfRows );

    if( i > _noOfRows )
      throw new JWaveFailure( "Space - i is greater than noOfRows: "
          + _noOfRows );

    check( j, k ); // for checking (j,k), while i is in every Line object stored.

  } // check

  /**
   * Getter!
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 16.05.2015 15:55:57
   * @param i
   *          from 0 to noOfRows-1
   * @param j
   *          from 0 to noOfCols-1
   * @param k
   *          from 0 to noOfLvls-1
   * @return the stored double value
   * @throws JWaveException
   *           if (i,j,k) is out of bounds
   */
  public abstract double get( int i, int j, int k ) throws JWaveException;

  /**
   * Setter!
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 16.05.2015 15:56:24
   * @param i
   *          from 0 to noOfRows-1
   * @param j
   *          from 0 to noOfCols-1
   * @param k
   *          from 0 to noOfLvls-1
   * @param value
   *          any value of type double
   * @throws JWaveException
   *           if (i,j,k) is out of bounds
   */
  public abstract void set( int i, int j, int k, double value )
      throws JWaveException;

} // class
