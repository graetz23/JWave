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

import math.jwave.exceptions.JWaveException;

/**
 * Instead using Java's Object class as super type, a named Super typed is used
 * for grouping data containers like Line, Block, or Space objects together!
 * 
 * @author Christian Scheiblich (cscheiblich@gmail.com)
 * @date 16.05.2015 19:27:00
 */
public abstract class Super {

  /**
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 16.05.2015 19:27:01
   */
  public Super( ) {
  } // Super

  /**
   * If memory is allocated then return true else return false.
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 18.05.2015 18:03:55
   * @return true if memory is allocated
   */
  public abstract boolean isAllocated( );

  /**
   * Allocates the memory of the object internally, but only if no memory is
   * allocated yet. However, there can be different strategies in data storage
   * for each object.
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 18.05.2015 17:59:24
   * @throws JWaveException
   *           if memory is already occupied or if memory cannot be allocated
   */
  public abstract void alloc( ) throws JWaveException;

  /**
   * Simply drops the internal storage and places a null pointer in Java.
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 18.05.2015 18:01:56
   * @throws JWaveException
   *           if internal memory is already erased
   */
  public abstract void erase( ) throws JWaveException;

} // Super
