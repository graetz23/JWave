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
package math.jwave.datatypes.lines;

import math.jwave.exceptions.JWaveException;
import math.jwave.exceptions.JWaveFailure;

/**
 * A line of Data; 1-D organized by (0) .. (noOfRows).
 * 
 * @author Christian Scheiblich (cscheiblich@gmail.com)
 * @date 16.05.2015 15:00:01
 */
public abstract class Line {

  /**
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 16.05.2015 15:02:58
   */
  protected int _noOfRows;

  /**
   * Create a Line object by a certain number of rows.
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 16.05.2015 15:03:33
   * @param noOfRows
   */
  public Line( int noOfRows ) {

    _noOfRows = noOfRows;

  } // Line

  /**
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 16.05.2015 15:06:52
   * @return the _noOfRows
   */
  public int getNoOfRows( ) {
    return _noOfRows;
  } // getNoOfRows

  /**
   * Check the given input of position and throws a failure (exception) if
   * position is not valid.
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 16.05.2015 15:13:18
   * @param pos
   *          from 0 to noOfRows-1
   * @throws JWaveException
   *           if pos is out of bounds
   */
  protected void check( int pos ) throws JWaveException {

    if( pos < 0 )
      throw new JWaveFailure( "Line - pos is smaller than zero" );

    if( pos == _noOfRows )
      throw new JWaveFailure( "Line - pos is equal to noOfRows: " + _noOfRows );

    if( pos > _noOfRows )
      throw new JWaveFailure( "Line - pos is greater than noOfRows: "
          + _noOfRows );

  } // check

  /**
   * Getter for the stored values.
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 16.05.2015 15:10:04
   * @param pos
   *          from 0 to noOfRows-1 as range of input
   * @return the stored double value
   * @throws JWaveException
   *           if pos is out of bounds
   */
  public abstract double get( int pos ) throws JWaveException;

  /**
   * Setter to store values.
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 16.05.2015 15:16:42
   * @param pos
   *          from 0 to noOfRows-1 as range of input
   * @param val
   *          the value to be stored in Line object.
   * @throws JWaveException
   *           if pos out of bounds
   */
  public abstract void set( int pos, double val ) throws JWaveException;

} // class
