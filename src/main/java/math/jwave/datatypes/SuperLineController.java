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
package math.jwave.datatypes;

import math.jwave.datatypes.lines.Line;
import math.jwave.datatypes.lines.LineFull;
import math.jwave.datatypes.lines.LineHash;
import math.jwave.exceptions.JWaveException;
import math.jwave.exceptions.JWaveFailureNotKnown;

/**
 * Builds a SuperLine object filled up with Line objects of selected sub type.
 * However, the SuperLine forms the global object thats data is sub divided into
 * smaller parts represented by the Line objects.
 * 
 * @author Christian Scheiblich (cscheiblich@gmail.com)
 * @date 18.05.2015 20:05:02
 */
public class SuperLineController {

  /**
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 18.05.2015 20:05:02
   */
  public SuperLineController( ) {
    // TODO Auto-generated constructor stub
  }

  /**
   * Strategy to create a SuperLine object filled with Line objects.
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 24.05.2015 18:18:46
   * @param pattern
   * @param noOfLineObj
   * @param noOfEntries
   * @return
   * @throws JWaveException
   */
  public static SuperLine
      create( Line pattern, int noOfLineObj, int noOfEntries )
          throws JWaveException {

    int lineType = -1;
    if( pattern instanceof LineFull )
      lineType = 1; // LineFull
    else if( pattern instanceof LineHash )
      lineType = 2; // LineFull      
    else
      throw new JWaveFailureNotKnown(
          "SuperLineController - type of Line object not known!" );

    Line line = null; // keep cool ~8>

    SuperLine superLine = null;

    return superLine;

  } // create
  
} // class
