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
package math.jwave.datatypes.blocks;

import math.jwave.exceptions.JWaveException;
import math.jwave.exceptions.JWaveFailure;

/**
 * A block of Data; 2-D organized by (0,0) .. (noOfRows,noOfCols). This object
 * is strictly using the Line objects, due to having here the possibility to
 * implement different strategies on values storage.
 * 
 * @author Christian Scheiblich (cscheiblich@gmail.com)
 * @date 16.05.2015 14:39:22
 */
public abstract class Block {

  /**
   * The number of rows of this Block.
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 16.05.2015 14:41:23
   */
  protected int _noOfRows;

  /**
   * The number of columns of this Block.
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 16.05.2015 14:41:42
   */
  protected int _noOfCols;

  /**
   * super, super, super, ..
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 16.05.2015 15:21:17
   * @param noOfRows
   *          stores the noOfRows exclusively, even if each Line object stores
   *          that value again.
   * @param noOfCols
   *          the no of columns or the number of Line objects.
   */
  public Block( int noOfRows, int noOfCols ) {

    _noOfRows = noOfRows;
    _noOfCols = noOfCols;

  } // Block

  /**
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 16.05.2015 14:43:41
   * @return the _noOfRows
   */
  public int getNoOfRows( ) {
    return _noOfRows;
  } // getNoOfRows

  /**
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 16.05.2015 14:43:41
   * @return the _noOfCols
   */
  public int getNoOfCols( ) {
    return _noOfCols;
  } // getNoOfCols

  /**
   * Check the given input of j is in bound of the number of columns, otherwise
   * throw a failure (exception) if j is not valid.
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 16.05.2015 15:37:57
   * @param j
   *          from 0 to noOfCols-1
   * @throws JWaveException
   */
  protected void check( int j ) throws JWaveException {

    if( j < 0 )
      throw new JWaveFailure( "Block - i is smaller than zero" );

    if( j == _noOfCols )
      throw new JWaveFailure( "Block - i is equal to noOfCols: " + _noOfCols );

    if( j > _noOfCols )
      throw new JWaveFailure( "Block - i is greater than noOfCols: "
          + _noOfCols );

  } // check

  /**
   * Check the given input of (i,j) and throws a failure (exception) if position
   * is not valid.
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 16.05.2015 15:33:14
   * @param i
   *          from 0 to noOfRows-1
   * @param j
   *          from 0 to noOfCols-1
   * @throws JWaveException
   *           if (i,j) is out of bounds
   */
  protected void check( int i, int j ) throws JWaveException {

    if( i < 0 )
      throw new JWaveFailure( "Block - i is smaller than zero" );

    if( i == _noOfRows )
      throw new JWaveFailure( "Block - i is equal to noOfRows: " + _noOfRows );

    if( i > _noOfRows )
      throw new JWaveFailure( "Block - i is greater than noOfRows: "
          + _noOfRows );

    check( j ); // for checking j, while i is in every Line object stored.

  } // check

  /**
   * Getter for a stored value of type double.
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 16.05.2015 15:23:22
   * @param i
   *          position in rows of the block
   * @param j
   *          position in columns of the block
   * @return the stored value
   * @throws JWaveException
   *           if i and / or j are out of bounds
   */
  public abstract double get( int i, int j ) throws JWaveException;

  /**
   * Setter for a stored value of type double!
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 16.05.2015 15:42:53
   * @param i
   *          position in rows of the block
   * @param j
   *          position in columns of the block
   * @param value
   *          any value in range of a double type is possible
   * @throws JWaveException
   *           if i and / or j are out of bounds
   */
  public abstract void set( int i, int j, double value ) throws JWaveException;

} // class
