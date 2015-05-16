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
import math.jwave.exceptions.JWaveFailure;

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
    _hashMap = new HashMap< Integer, Double >( );

  } // LineHash

  /*
   * Getter!
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 16.05.2015 16:30:00 (non-Javadoc)
   * @see math.jwave.datatypes.lines.Line#get(int)
   */
  @Override public double get( int i ) throws JWaveException {

    check( i );
    
    double value = 0.;
    
    if( _hashMap.containsKey( i ) )
      value = _hashMap.get( i );
    else 
      throw new JWaveFailure( "Line - no value stored for requested i: " + i );
    
    return value;
    
  } // get

  /*
   * Setter!
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 16.05.2015 16:30:00 (non-Javadoc)
   * @see math.jwave.datatypes.lines.Line#set(int, double)
   */
  @Override public void set( int i, double value ) throws JWaveException {

    check( i );
    
    _hashMap.put( i, value );
    
  } // set

} // class
