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

import math.jwave.datatypes.blocks.Block;
import math.jwave.datatypes.blocks.BlockFull;
import math.jwave.exceptions.JWaveException;

/**
 * A space that uses a full array for storage of Block objects.
 * 
 * @author Christian Scheiblich (cscheiblich@gmail.com)
 * @date 16.05.2015 15:57:35
 */
public class SpaceFull extends Space {

  /**
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 16.05.2015 16:00:11
   */
  protected Block[ ] _arrBlocks;

  /**
   * Constructor setting members for and allocating memory!
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 16.05.2015 15:57:35
   * @param i
   *          from 0 to noOfRows-1
   * @param j
   *          from 0 to noOfCols-1
   * @param k
   *          from 0 to noOfLvls-1
   */
  public SpaceFull( int noOfRows, int noOfCols, int noOfLvls ) {

    super( noOfRows, noOfCols, noOfLvls );

    _arrBlocks = new Block[ _noOfLvls ];
    for( int k = 0; k < _noOfLvls; k++ )
      _arrBlocks[ k ] = new BlockFull( noOfRows, noOfCols );

  } // SpaceFull

  /*
   * Getter!
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 16.05.2015 15:57:35 (non-Javadoc)
   * @see math.jwave.datatypes.spaces.Space#get(int, int, int)
   */
  @Override public double get( int i, int j, int k ) throws JWaveException {

    check( i, j, k );

    return _arrBlocks[ k ].get( i, j );

  } // get

  /*
   * Setter!
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 16.05.2015 15:57:35 (non-Javadoc)
   * @see math.jwave.datatypes.spaces.Space#set(int, int, int, double)
   */
  @Override public void set( int i, int j, int k, double value )
      throws JWaveException {

    check( i, j, k );

    _arrBlocks[ k ].set( i, j, value );

  } // set

  /*
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 24.05.2015 15:14:11 (non-Javadoc)
   * @see math.jwave.datatypes.Super#copy()
   */
  @Override public Space copy( ) {
    // TODO Auto-generated method stub
    return null;
  } // copy

  /*
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 24.05.2015 15:04:11 (non-Javadoc)
   * @see math.jwave.datatypes.Super#isAllocated()
   */
  @Override public boolean isAllocated( ) {
    // TODO Auto-generated method stub
    return false;
  } // isAllocated

  /*
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 24.05.2015 15:04:11 (non-Javadoc)
   * @see math.jwave.datatypes.Super#alloc()
   */
  @Override public void alloc( ) throws JWaveException {
    // TODO Auto-generated method stub
  } // alloc

  /*
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 24.05.2015 15:04:11 (non-Javadoc)
   * @see math.jwave.datatypes.Super#erase()
   */
  @Override public void erase( ) throws JWaveException {
    // TODO Auto-generated method stub
  } // erase

} // class
