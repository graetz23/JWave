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

/**
 * A line of Data; 1-D organized by (0) .. (noOfRows). Using a double array for
 * storage of data.
 * 
 * @author Christian Scheiblich (cscheiblich@gmail.com)
 * @date 16.05.2015 15:05:28
 */
public class LineFull extends Line {

  protected double[ ] _arr;

  /**
   * TODO Comment me please!
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 16.05.2015 15:05:28
   * @param noOfRows
   */
  public LineFull( int noOfRows ) {
    super( noOfRows );

    _arr = new double[ noOfRows ];

  } // LineFull

  /*
   * Getter!
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 16.05.2015 15:18:14 (non-Javadoc)
   * @see math.jwave.datatypes.lines.Line#get(int)
   */
  @Override public double get( int pos ) throws JWaveException {

    check( pos );

    return _arr[ pos ];

  } // get 

  /*
   * Setter!
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 16.05.2015 15:18:26 (non-Javadoc)
   * @see math.jwave.datatypes.lines.Line#set(int, double)
   */
  @Override public void set( int pos, double val ) throws JWaveException {

    check( pos );

    _arr[ pos ] = val;

  } // set

} // class
