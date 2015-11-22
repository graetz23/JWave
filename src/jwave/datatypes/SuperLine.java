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
package jwave.datatypes;

import java.util.ArrayList;

import jwave.datatypes.lines.Line;
import jwave.exceptions.JWaveException;
import jwave.exceptions.JWaveFailureNotValid;

/**
 * SuperLine consists of several Line objects of different sizes.
 * 
 * @author Christian Scheiblich (cscheiblich@gmail.com)
 * @date 16.05.2015 19:30:28
 */
public class SuperLine {

  /**
   * However, who knows how many blocks are delivered!
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 18.05.2015 20:02:59
   */
  ArrayList< Super > _listOfLines;

  /**
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 16.05.2015 19:30:28
   */
  public SuperLine( ) {

    _listOfLines = new ArrayList< Super >( );

  } // SuperLine

  /**
   * Returns the number of stored Line objects.
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 16.05.2015 19:36:13
   * @return number of Line objects
   */
  public int getNoOfLines( ) {

    return _listOfLines.size( );

  } // getNoOfLines

  /**
   * Add a Line object to list
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 16.05.2015 19:36:54
   * @param line
   *          object of type Line
   */
  public void add( Line line ) {

    _listOfLines.add( line );

  } // add

  /**
   * Return Line object at position p; 0 .. N-1
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 24.05.2015 18:16:27
   * @param p
   * @return
   * @throws JWaveException
   */
  public Line get( int p ) throws JWaveException {

    Line line = null;

    if( p < 0 || p >= getNoOfLines( ) )
      throw new JWaveFailureNotValid( "SuperLine#get - position p is out of bound!" );

    line = (Line)_listOfLines.get( p );

    return line;

  } // get

} // class
