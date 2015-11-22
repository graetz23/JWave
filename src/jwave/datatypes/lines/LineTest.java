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
package jwave.datatypes.lines;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import jwave.exceptions.JWaveException;

import org.junit.Test;

/**
 * @author Christian Scheiblich (cscheiblich@gmail.com)
 * @date 16.05.2015 16:06:38
 */
public class LineTest {

  private int _noOfRows = 100000; // adjust runtime of test

  /**
   * Generate a LineFull object already set with data: i!
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 16.05.2015 16:09:13
   * @param noOfRows
   *          0 ..
   * @return
   * @throws JWaveException
   */
  private Line genLineFullObject( int noOfRows ) throws JWaveException {

    Line line = new LineFull( noOfRows );

    if( !line.isAllocated( ) )
      line.alloc( ); // double[ ] array

    for( int i = 0; i < line.getNoOfRows( ); i++ )
      line.set( i, (double)i );

    return line;

  } // genLineFullObject

  /**
   * Generate a LineHash object already set with data: i!
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 16.05.2015 16:39:06
   * @param noOfRows
   * @return
   * @throws JWaveException
   */
  private Line genLineHashObject( int noOfRows ) throws JWaveException {

    Line line = new LineHash( noOfRows );

    if( !line.isAllocated( ) )
      line.alloc( ); // HashMap< Integer, Double >

    for( int i = 0; i < line.getNoOfRows( ); i++ )
      line.set( i, (double)i );

    return line;

  } // genLineHashObject

  /**
   * Test method for {@link jwave.datatypes.lines.LineFull#get(int)}.
   */
  @Test public void testGet( ) {

    try {

      Line line = genLineFullObject( _noOfRows );

      for( int i = 0; i < line.getNoOfRows( ); i++ )
        assertEquals( (double)i, line.get( i ), 0. );

      line = genLineHashObject( _noOfRows );

      for( int i = 0; i < line.getNoOfRows( ); i++ )
        assertEquals( (double)i, line.get( i ), 0. );

    } catch( JWaveException e ) {
      e.printStackTrace( );
      fail( "caught exception" );
    } // try 

  }

  /**
   * Test method for
   * {@link jwave.datatypes.lines.LineFull#set(int, double)}.
   */
  @Test public void testSet( ) {

    try {

      Line line = genLineFullObject( _noOfRows );

      for( int i = 0; i < line.getNoOfRows( ); i++ )
        line.set( i, (double)( i + 1 ) );

      for( int i = 0; i < line.getNoOfRows( ); i++ )
        assertEquals( (double)( i + 1 ), line.get( i ), 0. );

      line = genLineHashObject( _noOfRows );

      for( int i = 0; i < line.getNoOfRows( ); i++ )
        line.set( i, (double)( i + 1 ) );

      for( int i = 0; i < line.getNoOfRows( ); i++ )
        assertEquals( (double)( i + 1 ), line.get( i ), 0. );

    } catch( JWaveException e ) {
      e.printStackTrace( );
      fail( "caught exception" );
    } // try 

  } // test

} // JUnit
