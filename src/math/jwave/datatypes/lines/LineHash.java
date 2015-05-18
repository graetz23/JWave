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

import java.util.HashMap;

import math.jwave.exceptions.JWaveException;
import math.jwave.exceptions.JWaveFailureNotAllocated;
import math.jwave.exceptions.JWaveFailureNotFound;

/**
 * Uses HashMap generic for sparse data representations.
 * 
 * @author Christian Scheiblich (cscheiblich@gmail.com)
 * @date 16.05.2015 16:30:00
 */
public class LineHash extends Line {

  /**
   * Simple hash mapping for sparse data.
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 16.05.2015 16:31:41
   */
  HashMap< Integer, Double > _hashMap;

  /**
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 16.05.2015 16:30:00
   * @param noOfRows
   */
  public LineHash( int noOfRows ) {
    super( noOfRows );
  } // LineHash

  public LineHash( int offSetRow, int noOfRows ) {
    super( offSetRow, noOfRows );
  } // LineHash

  /*
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 18.05.2015 18:11:28 (non-Javadoc)
   * @see math.jwave.datatypes.lines.Line#isAllocated()
   */
  @Override public boolean isAllocated( ) {
    boolean isAllocated = true;
    if( _hashMap == null )
      isAllocated = false;
    return isAllocated;
  } // isAllocated

  /*
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 18.05.2015 18:12:03 (non-Javadoc)
   * @see math.jwave.datatypes.lines.Line#alloc()
   */
  @Override public void alloc( ) throws JWaveException {

    if( !isAllocated( ) )
      _hashMap = new HashMap< Integer, Double >( );

  } // alloc

  /*
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 18.05.2015 18:12:13 (non-Javadoc)
   * @see math.jwave.datatypes.lines.Line#erase()
   */
  @Override public void erase( ) throws JWaveException {

    _hashMap = null;

  } // erase

  /*
   * Getter!
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 16.05.2015 16:30:00 (non-Javadoc)
   * @see math.jwave.datatypes.lines.Line#get(int)
   */
  @Override public double get( int i ) throws JWaveException {

    if( !isAllocated( ) )
      throw new JWaveFailureNotAllocated( "LineHash - no memory allocated!" );

    check( i );

    double value = 0.;

    if( _hashMap.containsKey( i ) )
      value = _hashMap.get( i );
    else
      throw new JWaveFailureNotFound( "Line - no value stored for requested i: " + i );

    return value;

  } // get

  /*
   * Setter!
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 16.05.2015 16:30:00 (non-Javadoc)
   * @see math.jwave.datatypes.lines.Line#set(int, double)
   */
  @Override public void set( int i, double value ) throws JWaveException {

    if( !isAllocated( ) )
      throw new JWaveFailureNotAllocated( "LineHash - no memory allocated!" );

    check( i );

    _hashMap.put( i, value );

  } // set

} // class
