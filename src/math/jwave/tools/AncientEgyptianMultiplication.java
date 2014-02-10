/**
 * JWave
 *
 * Copyright 2009-2013 Christian Scheiblich
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at 
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 *
 * This file AncientEgyptianMultiplication.java is part of JWave.
 *
 * @author Christian Scheiblich
 * contact cscheiblich@gmail.com
 * date Feb 11, 2013 1:42:37 PM
 *
 */
package math.jwave.tools;

import math.jwave.exceptions.*;

/**
 * @author Christian Scheiblich
 * date Feb 11, 2013 1:42:37 PM
 *
 */
public class AncientEgyptianMultiplication {
  
  /**
   * Some how useless ~8>
   * 
   * @author Christian Scheiblich
   * date Feb 11, 2013 1:42:37 PM
   *
   */
  public AncientEgyptianMultiplication( ) {
  } // AncientEgyptianMultiplication
  
  /**
   * The method converts a positive integer to the ancient Egyptian multipliers
   * which are actually the multipliers to display the number by a sum of the
   * largest possible powers of two. E.g. 42 = 2^5 + 2^3 + 2^1 = 32 + 8 + 2.
   * However, odd numbers always 2^0 = 1 as the last entry. Also see:
   * http://en.wikipedia.org/wiki/Ancient_Egyptian_multiplication
   * 
   * @author Christian Scheiblich
   * date Feb 11, 2013 1:50:42 PM
   *
   * @param number
   * @return
   * @throws JWaveException
   */
  public int[ ] decompose( int number ) throws JWaveException {
    
    if( number < 1 )
      throw new JWaveFailure( "the supported number for decomposition is smaller than one" );
    
    int power = getExponent( (double)number );
    
    int[ ] tmpArr = new int[ power + 1 ]; // max no of possible multipliers
    
    int pos = 0;
    double current = (double)number;
    while( current >= 1. ) {
      
      power = getExponent( current );
      tmpArr[ pos ] = power;
      current = current - scalb( 1., power ); // 1. * 2 ^ power
      pos++;
      
    } // while
    
    int[ ] ancientEgyptianMultipliers = new int[ pos ]; // shrink
    for( int c = 0; c < pos; c++ )
      ancientEgyptianMultipliers[ c ] = tmpArr[ c ];
    
    return ancientEgyptianMultipliers;
    
  } // decompose
  
  /**
  * The method converts a list of ancient Egyptian multipliers to the
  * corresponding integer. The ancient Egyptian multipliers are actually the
  * multipliers to display am integer by a sum of the largest possible powers
  * of two. E.g. 42 = 2^5 + 2^3 + 2^1 = 32 + 8 + 2. Also see:
  * http://en.wikipedia.org/wiki/Ancient_Egyptian_multiplication
  * 
  * @author Christian Scheiblich
  * date Feb 11, 2013 1:55:54 PM
  *
  * @param ancientEgyptianMultipliers
  *  an integer array keeping the ancient Egyptian multipliers
  * @return resulting integer as sum of powers of two
  * @throws JWaveException
  */
  public int compose( int[ ] ancientEgyptianMultipliers ) throws JWaveException {
    
    if( ancientEgyptianMultipliers == null )
      throw new JWaveError( "given array is null" );
    
    int number = 0;
    
    int noOfAncientEgyptianMultipliers = ancientEgyptianMultipliers.length;
    for( int m = 0; m < noOfAncientEgyptianMultipliers; m++ ) {
      
      int ancientEgyptianMultiplier = ancientEgyptianMultipliers[ m ];
      
      number += (int)scalb( 1., ancientEgyptianMultiplier ); // 1. * 2^p
      
    } // compose
    
    return number;
    
  }
  
  /**
   * Replaced Math.getExponent due to google's Android OS is not
   * supporting it in Math library.
   * 
   * @author Christian Scheiblich
   * date Feb 11, 2013 1:47:05 PM
   * 
   * @author sashi
   * @date 19.04.2011 15:43:16
   * 
   * @param f
   * @return p of 2^p <= f < 2^(p+1)
   */
  public int getExponent( double f ) {
    
    int exp = (int)( Math.log( f ) / Math.log( 2. ) );
    
    return exp;
    
  } // exp
  
  /**
   * Replaced Math.scalb due to google's Android OS is not supporting
   * it in Math library.
   * 
   * @author Christian Scheiblich
   * date Feb 11, 2013 1:46:33 PM
   *
   * @param f
   * @param scaleFactor
   * @return f times 2^(scaleFactor)
   */
  public double scalb( double f, int scaleFactor ) {
    
    double res = f * Math.pow( 2., scaleFactor );
    
    return res;
    
  } // scalb
  
}
