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

import java.util.HashMap;

import math.jwave.datatypes.lines.Line;
import math.jwave.datatypes.lines.LineFull;
import math.jwave.exceptions.JWaveException;

/**
 * A block that uses a full array for storage of Line objects.
 * 
 * @author Christian Scheiblich (cscheiblich@gmail.com)
 * @date 16.05.2015 14:45:52
 */
public class BlockFull extends Block {

  /**
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 16.05.2015 15:26:11
   */
  protected Line[ ] _arrLines;

  /**
   * Create an object of a sub type; e.g. as pattern.
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 24.05.2015 13:57:28
   */
  public BlockFull( ) {
    super( );
  } // BlockFull

  /**
   * Copy constructor that takes over - if available - the values of another
   * type of block.
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 24.05.2015 14:01:22
   * @param block
   *          object of type block; e.g. BlockHash
   */
  public BlockFull( Block block ) {
    super( block );

    // TODO copy values form Block object

  } // BlockFull

  /**
   * Constructor setting members for and allocating memory!
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 16.05.2015 14:45:52
   * @param noOfRows
   * @param noOfCols
   */
  public BlockFull( int noOfRows, int noOfCols ) {

    super( noOfRows, noOfCols ); // store does exclusively

    _arrLines = new Line[ _noOfCols ];
    for( int j = 0; j < _noOfCols; j++ )
      _arrLines[ j ] = new LineFull( noOfRows );

  } // BlockFull

  /**
   * Passing information that takes the block as a part of a global structure;
   * e.g. a SuperBlock.
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 24.05.2015 14:05:55
   * @param offSetRow
   *          the global off set of rows of the block
   * @param offSetCol
   *          the global off set of columns of the block
   * @param noOfRows
   *          the number of rows of the block
   * @param noOfCols
   *          the number of columns of the block
   */
  public BlockFull( int offSetRow, int offSetCol, int noOfRows, int noOfCols ) {

    super( offSetRow, offSetCol, noOfRows, noOfCols );

  } // BlockFull

  /*
   * Getter!
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 16.05.2015 15:34:59 (non-Javadoc)
   * @see math.jwave.datatypes.blocks.Block#get(int, int)
   */
  @Override public double get( int i, int j ) throws JWaveException {

    // check( j );
    check( i, j );

    return _arrLines[ j ].get( i ); // checks i again

  } // get

  @Override public void set( int i, int j, double value ) throws JWaveException {

    // check( j );
    check( i, j );

    _arrLines[ j ].set( i, value ); // checks i again

  } // set

  /*
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 24.05.2015 15:01:42 (non-Javadoc)
   * @see math.jwave.datatypes.Super#isAllocated()
   */
  @Override public boolean isAllocated( ) {
    // TODO Auto-generated method stub
    return false;
  } // isAllocated

  /*
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 24.05.2015 15:01:42 (non-Javadoc)
   * @see math.jwave.datatypes.Super#alloc()
   */
  @Override public void alloc( ) throws JWaveException {
    // TODO Auto-generated method stub
  } // alloc

  /*
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 24.05.2015 15:01:42 (non-Javadoc)
   * @see math.jwave.datatypes.Super#erase()
   */
  @Override public void erase( ) throws JWaveException {
    // TODO Auto-generated method stub
  } // erase

} // class
