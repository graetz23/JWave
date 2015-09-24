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

import java.util.HashMap;

import jwave.datatypes.blocks.Block;
import jwave.datatypes.blocks.BlockHash;
import jwave.exceptions.JWaveException;
import jwave.exceptions.JWaveFailure;

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
  HashMap< Integer, Block > _hashMapBlocks = null;

  /**
   * A space object with no input; e.g. as a pattern.
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 24.05.2015 19:05:01
   */
  public SpaceHash( ) {
    super( );
  } // SpaceHash

  /**
   * Copy constructor for generating the same space (cube) object again.
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 24.05.2015 19:06:25
   * @param space
   */
  public SpaceHash( Space space ) {
    super( space );

    try {
      alloc( );
      for( int i = 0; i < _noOfRows; i++ )
        for( int j = 0; j < _noOfCols; j++ )
          for( int k = 0; k < _noOfLvls; k++ )
            set( i, j, k, space.get( i, j, k ) );
    } catch( JWaveException e ) {
      e.printStackTrace( );
    } // try

    // TODO implement more efficient by using instanceof

  } // SpaceHash

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
  } // SpaceHash

  /**
   * Configure a space (a cube) as a part of a super space.
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 24.05.2015 19:07:05
   * @param offSetRow
   *          the starting position for the row of the space
   * @param offSetCol
   *          the starting position for the column of the space
   * @param offSetLvl
   *          the starting position for the level (height) of the space
   * @param noOfRows
   *          the number of rows
   * @param noOfCols
   *          the number of columns
   * @param noOfLvls
   *          the number of levels (height)
   */
  public SpaceHash( int offSetRow, int offSetCol, int offSetLvl, int noOfRows,
      int noOfCols, int noOfLvls ) {
    super( offSetRow, offSetCol, offSetLvl, noOfRows, noOfCols, noOfLvls );
  } // SpaceHash

  /*
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 24.05.2015 15:14:34 (non-Javadoc)
   * @see jwave.datatypes.Super#copy()
   */
  @Override public Space copy( ) {
    return new SpaceHash( this );
  } // copy

  /*
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 24.05.2015 15:03:28 (non-Javadoc)
   * @see jwave.datatypes.Super#isAllocated()
   */
  @Override public boolean isAllocated( ) {
    boolean isAllocated = true;
    if( _hashMapBlocks == null )
      isAllocated = false;
    return isAllocated;
  } // isAllocated

  /*
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 24.05.2015 15:03:28 (non-Javadoc)
   * @see jwave.datatypes.Super#alloc()
   */
  @Override public void alloc( ) throws JWaveException {
    if( !isAllocated( ) )
      _hashMapBlocks = new HashMap< Integer, Block >( );
  } // alloc

  /*
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 24.05.2015 15:03:28 (non-Javadoc)
   * @see jwave.datatypes.Super#erase()
   */
  @Override public void erase( ) throws JWaveException {
    if( _hashMapBlocks != null )
      _hashMapBlocks.clear( );
    _hashMapBlocks = null;
  } // erase

  /*
   * Getter!
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 16.05.2015 16:52:40 (non-Javadoc)
   * @see jwave.datatypes.spaces.Space#get(int, int, int)
   */
  @Override public double get( int i, int j, int k ) throws JWaveException {

    checkMemory( );

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
   * @see jwave.datatypes.spaces.Space#set(int, int, int, double)
   */
  @Override public void set( int i, int j, int k, double value )
      throws JWaveException {

    checkMemory( );

    check( i, j, k );

    Block block = null;

    if( _hashMapBlocks.containsKey( k ) ) {

      block = _hashMapBlocks.get( k );
      block.set( i, j, value );

    } else {

      block = new BlockHash( _offSetRow, _offSetCol, _noOfRows, _noOfCols );
      block.alloc( );
      block.set( i, j, value );
      _hashMapBlocks.put( k, block );

    } // if

  } // set

} // class
