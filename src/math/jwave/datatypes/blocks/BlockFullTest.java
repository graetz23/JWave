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

import static org.junit.Assert.*;
import math.jwave.datatypes.lines.Line;
import math.jwave.datatypes.lines.LineFull;
import math.jwave.exceptions.JWaveException;

import org.junit.Test;

/**
 * @author Christian Scheiblich (cscheiblich@gmail.com)
 * @date 16.05.2015 16:15:47
 */
public class BlockFullTest {

  private int _noOfRows = 1000; // adjust runtime of test
  private int _noOfCols = 1000; // adjust runtime of test

  /**
   * generate a block for tests keeping already values.
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 16.05.2015 16:21:33
   * @param noOfRows
   * @param noOfCols
   * @return
   * @throws JWaveException
   */
  private Block genBlockFullObject( int noOfRows, int noOfCols )
      throws JWaveException {

    Block block = new BlockFull( noOfRows, noOfCols );

    for( int i = 0; i < block.getNoOfRows( ); i++ )
      for( int j = 0; j < block.getNoOfCols( ); j++ )
        block.set( i, j, (double)( i + j ) );

    return block;

  } // genBlockFullObject

  /**
   * Test method for {@link math.jwave.datatypes.blocks.BlockFull#get(int, int)}
   * .
   */
  @Test public void testGet( ) {

    try {

      Block block = genBlockFullObject( _noOfRows, _noOfCols );

      for( int i = 0; i < block.getNoOfRows( ); i++ )
        for( int j = 0; j < block.getNoOfCols( ); j++ )
          assertEquals( (double)( i + j ), block.get( i, j ), 0. );

    } catch( JWaveException e ) {
      fail( "caught exception" );
      e.printStackTrace( );
    } // try 

  }

  /**
   * Test method for
   * {@link math.jwave.datatypes.blocks.BlockFull#set(int, int, double)}.
   */
  @Test public void testSet( ) {

    try {

      Block block = genBlockFullObject( _noOfRows, _noOfCols );

      for( int i = 0; i < block.getNoOfRows( ); i++ )
        for( int j = 0; j < block.getNoOfCols( ); j++ )
          block.set( i, j, (double)( i + j + 1 ) );

      for( int i = 0; i < block.getNoOfRows( ); i++ )
        for( int j = 0; j < block.getNoOfCols( ); j++ )
          assertEquals( (double)( i + j + 1 ), block.get( i, j ), 0. );

    } catch( JWaveException e ) {
      fail( "caught exception" );
      e.printStackTrace( );
    } // try 

  }

}
