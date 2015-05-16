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

import static org.junit.Assert.*;
import math.jwave.exceptions.JWaveException;

import org.junit.Test;

/**
 * @author Christian Scheiblich (cscheiblich@gmail.com)
 * @date 16.05.2015 16:06:38
 */
public class LineFullTest {

  private int _lengthofLine = 10000; // adjust runtime of test

  /**
   * generate object for tests.
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 16.05.2015 16:09:13
   * @param noOfRows
   * @return
   * @throws JWaveException
   */
  private Line genLineFullObject( int noOfRows ) throws JWaveException {

    Line line = new LineFull( noOfRows );

    for( int i = 0; i < line.getNoOfRows( ); i++ )
      line.set( i, (double)i );

    return line;

  } // LineFull

  /**
   * Test method for {@link math.jwave.datatypes.lines.LineFull#get(int)}.
   */
  @Test public void testGet( ) {

    try {

      Line line = genLineFullObject( _lengthofLine );

      for( int i = 0; i < line.getNoOfRows( ); i++ )
        assertEquals( (double)i, line.get( i ), 0. );

    } catch( JWaveException e ) {
      fail( "caught exception" );
      e.printStackTrace( );
    } // try 

  }

  /**
   * Test method for
   * {@link math.jwave.datatypes.lines.LineFull#set(int, double)}.
   */
  @Test public void testSet( ) {

    try {

      Line line = genLineFullObject( _lengthofLine );

      for( int i = 0; i < line.getNoOfRows( ); i++ )
        line.set( i, (double)( i + 1 ) );

      for( int i = 0; i < line.getNoOfRows( ); i++ )
        assertEquals( (double)( i + 1 ), line.get( i ), 0. );

    } catch( JWaveException e ) {
      fail( "caught exception" );
      e.printStackTrace( );
    } // try 

  }

}
