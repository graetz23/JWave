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
package math.jwave.compressions;

import math.jwave.exceptions.JWaveException;
import math.jwave.exceptions.JWaveFailure;

/**
 * Some how this class is doing the same as the technical counterpart is doing -
 * compressing data that is transformed to Hilbert space by different methods.
 * 
 * @author Christian Scheiblich (cscheiblich@gmail.com)
 * @date 20.02.2014 23:41:35
 */
public abstract class Compressor {

  /**
   * A threshold that is used in several compression methods.
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 20.02.2014 23:44:26
   */
  protected double _threshold = 1.;

  /**
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 20.02.2014 23:41:35
   */
  public Compressor( ) {

    _threshold = 1.;

  } // Compressor

  public Compressor( double threshold ) throws JWaveException {

    if( threshold < 0. )
      throw new JWaveFailure( "given threshold is negative" );

    _threshold = threshold;

  } // Compressor

  /**
   * Interface for arrays for driving the different compression methods.
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 20.02.2014 23:48:06
   * @param arrHilb
   * @return
   */
  abstract protected double[ ] compress( double[ ] arrHilb );

  /**
   * Interface for matrices for driving the different compression methods.
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 20.02.2014 23:54:11
   * @param matHilb
   * @return
   */
  abstract protected double[ ][ ] compress( double[ ][ ] matHilb );

  /**
   * Interface for spaces for driving the different compression methods.
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 20.02.2014 23:54:52
   * @param spcHilb
   * @return
   */
  abstract protected double[ ][ ][ ] compress( double[ ][ ][ ] spcHilb );

} // Compressor
