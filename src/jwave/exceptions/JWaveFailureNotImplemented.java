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
package jwave.exceptions;

/**
 * This exception should be thrown if some code is not implemented yet.
 * 
 * @author Christian Scheiblich (cscheiblich@gmail.com)
 * @date 18.05.2015 20:15:06
 */
public class JWaveFailureNotImplemented extends JWaveFailure {

  /**
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 18.05.2015 20:16:06
   */
  private static final long serialVersionUID = 5830042445149954785L;

  /**
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 18.05.2015 20:15:06
   * @param message
   */
  public JWaveFailureNotImplemented( String message ) {
    super( message );
  } // JWaveFailureNotImplemented

} // class
