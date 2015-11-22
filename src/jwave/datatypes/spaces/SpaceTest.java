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
package jwave.datatypes.spaces;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import jwave.exceptions.JWaveException;

import org.junit.Test;

/**
 * @author Christian Scheiblich (cscheiblich@gmail.com)
 * @date 16.05.2015 16:22:33
 */
public class SpaceTest {

  private int _noOfRows = 100; // adjust runtime of test
  private int _noOfCols = 100; // adjust runtime of test
  private int _noOfLvls = 100; // adjust runtime of test

  /**
   * Generates a SpaceFull object already keeping with data: i + j + k!
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 16.05.2015 16:24:42
   * @param noOfRows
   *          0 ..
   * @param noOfCols
   *          0 ..
   * @param noOfLvls
   *          0 ..
   * @return
   * @throws JWaveException
   */
  private Space genSpaceFullObject( int noOfRows, int noOfCols, int noOfLvls )
      throws JWaveException {

    Space space = new SpaceFull( noOfRows, noOfCols, noOfLvls );
    space.alloc( );
    for( int i = 0; i < space.getNoOfRows( ); i++ )
      for( int j = 0; j < space.getNoOfCols( ); j++ )
        for( int k = 0; k < space.getNoOfLvls( ); k++ )
          space.set( i, j, k, (double)( i + j + k ) );

    return space;

  } // genSpaceFullObject

  /**
   * Generates a SpaceHash object already keeping with data: i + j + k!
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 16.05.2015 16:59:08
   * @param noOfRows
   *          0 ..
   * @param noOfCols
   *          0 ..
   * @param noOfLvls
   *          0 ..
   * @return
   * @throws JWaveException
   */
  private Space genSpaceHashObject( int noOfRows, int noOfCols, int noOfLvls )
      throws JWaveException {

    Space space = new SpaceHash( noOfRows, noOfCols, noOfLvls );
    space.alloc( );
    for( int i = 0; i < space.getNoOfRows( ); i++ )
      for( int j = 0; j < space.getNoOfCols( ); j++ )
        for( int k = 0; k < space.getNoOfLvls( ); k++ )
          space.set( i, j, k, (double)( i + j + k ) );

    return space;

  } // genSpaceHashObject

  /**
   * Test method for
   * {@link jwave.datatypes.spaces.SpaceFull#get(int, int, int)}.
   */
  @Test public void testGet( ) {

    try {

      Space space = genSpaceFullObject( _noOfRows, _noOfCols, _noOfLvls );

      for( int i = 0; i < space.getNoOfRows( ); i++ )
        for( int j = 0; j < space.getNoOfCols( ); j++ )
          for( int k = 0; k < space.getNoOfLvls( ); k++ )
            assertEquals( (double)( i + j + k ), space.get( i, j, k ), 0. );

      space = genSpaceHashObject( _noOfRows, _noOfCols, _noOfLvls );

      for( int i = 0; i < space.getNoOfRows( ); i++ )
        for( int j = 0; j < space.getNoOfCols( ); j++ )
          for( int k = 0; k < space.getNoOfLvls( ); k++ )
            assertEquals( (double)( i + j + k ), space.get( i, j, k ), 0. );

    } catch( JWaveException e ) {
      e.printStackTrace( );
      fail( "caught exception" );
    } // try 

  } // testGet

  /**
   * Test method for
   * {@link jwave.datatypes.spaces.SpaceFull#set(int, int, int, double)}.
   */
  @Test public void testSet( ) {

    try {

      Space space = genSpaceFullObject( _noOfRows, _noOfCols, _noOfLvls );

      for( int i = 0; i < space.getNoOfRows( ); i++ )
        for( int j = 0; j < space.getNoOfCols( ); j++ )
          for( int k = 0; k < space.getNoOfLvls( ); k++ )
            space.set( i, j, k, (double)( i + j + k + 1 ) );

      for( int i = 0; i < space.getNoOfRows( ); i++ )
        for( int j = 0; j < space.getNoOfCols( ); j++ )
          for( int k = 0; k < space.getNoOfLvls( ); k++ )
            assertEquals( (double)( i + j + k + 1 ), space.get( i, j, k ), 0. );

      space = genSpaceHashObject( _noOfRows, _noOfCols, _noOfLvls );

      for( int i = 0; i < space.getNoOfRows( ); i++ )
        for( int j = 0; j < space.getNoOfCols( ); j++ )
          for( int k = 0; k < space.getNoOfLvls( ); k++ )
            space.set( i, j, k, (double)( i + j + k + 1 ) );

      for( int i = 0; i < space.getNoOfRows( ); i++ )
        for( int j = 0; j < space.getNoOfCols( ); j++ )
          for( int k = 0; k < space.getNoOfLvls( ); k++ )
            assertEquals( (double)( i + j + k + 1 ), space.get( i, j, k ), 0. );

    } catch( JWaveException e ) {
      e.printStackTrace( );
      fail( "caught exception" );
    } // try 

  } // testSet

} // class
