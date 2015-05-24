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

import java.util.HashMap;

import math.jwave.datatypes.blocks.Block;
import math.jwave.datatypes.blocks.BlockHash;
import math.jwave.exceptions.JWaveException;
import math.jwave.exceptions.JWaveFailure;

/**
 * Uses HashMap generic for sparse data representations.
 * 
 * @author Christian Scheiblich (cscheiblich@gmail.com)
 * @date 16.05.2015 16:52:40
 */
public class SpaceHash extends Space {

  /**
   * Storing BlockHash objects in a HashMap for sparse representation.
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 16.05.2015 16:55:22
   */
  HashMap< Integer, Block > _hashMapBlocks;

  /**
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 16.05.2015 16:52:40
   * @param noOfRows
   *          0 ..
   * @param noOfCols
   *          0 ..
   * @param noOfLvls
   *          0 ..
   */
  public SpaceHash( int noOfRows, int noOfCols, int noOfLvls ) {
    super( noOfRows, noOfCols, noOfLvls );

    _hashMapBlocks = new HashMap< Integer, Block >( );

  }

  /*
   * Getter!
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 16.05.2015 16:52:40 (non-Javadoc)
   * @see math.jwave.datatypes.spaces.Space#get(int, int, int)
   */
  @Override public double get( int i, int j, int k ) throws JWaveException {

    check( i, j, k );

    Block block = null;
    double value = 0.;

    if( _hashMapBlocks.containsKey( k ) ) {

      block = _hashMapBlocks.get( k );

      value = block.get( i, j );

    } else
      throw new JWaveFailure( "Line - no value stored for requested i: " + i );

    return value;

  } // get

  /*
   * Setter!
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 16.05.2015 16:52:40 (non-Javadoc)
   * @see math.jwave.datatypes.spaces.Space#set(int, int, int, double)
   */
  @Override public void set( int i, int j, int k, double value )
      throws JWaveException {

    check( i, j, k );

    Block block = null;

    if( _hashMapBlocks.containsKey( k ) ) {

      block = _hashMapBlocks.get( k );
      block.set( i, j, value );

    } else {

      block = new BlockHash( _noOfRows, _noOfCols );
      block.set( i, j, value );
      _hashMapBlocks.put( k, block );

    } // if

  } // set

  /*
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 24.05.2015 15:03:28 (non-Javadoc)
   * @see math.jwave.datatypes.Super#isAllocated()
   */
  @Override public boolean isAllocated( ) {
    // TODO Auto-generated method stub
    return false;
  } // isAllocated

  /*
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 24.05.2015 15:03:28 (non-Javadoc)
   * @see math.jwave.datatypes.Super#alloc()
   */
  @Override public void alloc( ) throws JWaveException {
    // TODO Auto-generated method stub
  } // alloc

  /*
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 24.05.2015 15:03:28 (non-Javadoc)
   * @see math.jwave.datatypes.Super#erase()
   */
  @Override public void erase( ) throws JWaveException {
    // TODO Auto-generated method stub
  } // erase

} // class
