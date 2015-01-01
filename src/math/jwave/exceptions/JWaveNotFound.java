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
package math.jwave.exceptions;

/**
 * Exception for not found objects in JWave
 * 
 * @author Christian Scheiblich (cscheiblich@gmail.com)
 * @date 25.02.2014 20:51:39
 */
public class JWaveNotFound extends JWaveFailure {

  /**
	 * 
	 */
  private static final long serialVersionUID = -7215817371882941856L;

  /**
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 25.02.2014 20:52:08
   * @param message
   *          reason for the instance.
   */
  public JWaveNotFound( String message ) {
    super( message );
    _message = "JWave"; // overwrite
    _message += ": "; // separator
    _message += "Not found"; // Exception type
    _message += ": "; // separator
    _message += message; // add message
    _message += "\n"; // break line
  } // JWaveNotFound

} // JWaveNotFound