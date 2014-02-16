/**
 * JWave - Java implementation of wavelet transform algorithms
 *
 * Copyright 2008-2014 Christian Scheiblich (cscheiblich@gmail.com)
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
 * This file is part of JWave.
 *
 * @author Christian Scheiblich (cscheiblich@gmail.com)
 * @date 23.05.2008 17:42:23
 *
 */
package math.jwave;

import math.jwave.exceptions.JWaveFailure;
import math.jwave.transforms.BasicTransform;
import math.jwave.transforms.DiscreteFourierTransform;
import math.jwave.transforms.FastWaveletTransform;
import math.jwave.transforms.WaveletPacketTransform;
import math.jwave.transforms.wavelets.Coiflet01;
import math.jwave.transforms.wavelets.Daubechies02;
import math.jwave.transforms.wavelets.Daubechies03;
import math.jwave.transforms.wavelets.Daubechies04;
import math.jwave.transforms.wavelets.Haar01;
import math.jwave.transforms.wavelets.Legendre01;
import math.jwave.transforms.wavelets.Legendre02;
import math.jwave.transforms.wavelets.Legendre03;
import math.jwave.transforms.wavelets.Wavelet;

/**
 * Main class for doing little test runs for different transform types and
 * different wavelets without JUnit.
 * 
 * @date 23.02.2010 14:26:47
 * @author Christian Scheiblich (cscheiblich@gmail.com)
 */
/**
 * @author tucker date 31.01.2014 20:26:06
 */
public class JWave {

  /**
   * Constructor.
   * 
   * @date 23.02.2010 14:26:47
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   */
  public JWave( ) {
  } // JWave

  /**
   * Main method for doing little test runs for different transform types and
   * different wavelets without JUnit. Requesting the transform type and the
   * type of wavelet to be used else usage is printed.
   * 
   * @date 23.02.2010 14:26:47
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @param args
   *          [transformType] [waveletType]
   */
  public static void main( String[ ] args ) {

    try { // try everything ~8>

      // String waveletTypeList =
      // "Haar01, Daubechies02, Daubechies03, Daubechies04, Legendre01, Legendre02, Legendre03, Coiflet01";
      String waveletTypeList = "Haar01, Daubechies02, Daubechies04, Legendre01";

      if( args.length < 2 || args.length > 3 ) {
        System.err
            .println( "usage: JWave [transformType] {waveletType} {noOfSteps}" );
        System.err.println( "" );
        System.err.println( "transformType: DFT, FWT, WPT, DWT" );
        System.err.println( "waveletType : " + waveletTypeList );
        System.err.println( "noOfSteps : "
            + "no of steps forward and reverse; optional" );
        return;
      } // if args

      String wType = args[ 1 ];
      Wavelet wavelet = null;
      if( wType.equalsIgnoreCase( "haar02" ) )
        wavelet = new Haar01( );
      else if( wType.equalsIgnoreCase( "lege02" ) )
        wavelet = new Legendre01( );
      else if( wType.equalsIgnoreCase( "daub02" ) )
        wavelet = new Daubechies02( );
      else if( wType.equalsIgnoreCase( "daub03" ) )
        wavelet = new Daubechies03( );
      else if( wType.equalsIgnoreCase( "daub04" ) )
        wavelet = new Daubechies04( );
      else if( wType.equalsIgnoreCase( "lege04" ) )
        wavelet = new Legendre02( );
      else if( wType.equalsIgnoreCase( "lege06" ) )
        wavelet = new Legendre03( );
      else if( wType.equalsIgnoreCase( "coif06" ) )
        wavelet = new Coiflet01( );
      else {
        System.err.println( "usage: JWave [transformType] {waveletType}" );
        System.err.println( "" );
        System.err.println( "available wavelets are " + waveletTypeList );
        return;
      } // if wType

      String tType = args[ 0 ];
      BasicTransform bWave = null;

      if( tType.equalsIgnoreCase( "dft" ) )
        bWave = new DiscreteFourierTransform( );
      else if( tType.equalsIgnoreCase( "fwt" ) )

        bWave = new FastWaveletTransform( wavelet );

      else if( tType.equalsIgnoreCase( "wpt" ) )

        bWave = new WaveletPacketTransform( wavelet );

      else {
        System.err.println( "usage: JWave [transformType] {waveletType}" );
        System.err.println( "" );
        System.err.println( "available transforms are DFT, FWT, WPT" );
        return;
      } // if tType

      // instance of transform
      Transform t;

      if( args.length > 2 ) {

        String argNoOfSteps = args[ 2 ];
        int noOfSteps = Integer.parseInt( argNoOfSteps );

        t = new Transform( bWave, noOfSteps ); // perform less steps than
                                               // possible

      } else {

        t = new Transform( bWave ); // perform all steps

      }

      double[ ] arrTime = { 1., 1., 1., 1., 1., 1., 1., 1. };

      System.out.println( "" );
      System.out.println( "time domain:" );
      for( int p = 0; p < arrTime.length; p++ )
        System.out.printf( "%9.6f", arrTime[ p ] );
      System.out.println( "" );

      double[ ] arrFreqOrHilb = null;
      arrFreqOrHilb = t.forward( arrTime );

      if( bWave instanceof DiscreteFourierTransform )
        System.out.println( "frequency domain:" );
      else
        System.out.println( "Hilbert domain:" );
      for( int p = 0; p < arrTime.length; p++ )
        System.out.printf( "%9.6f", arrFreqOrHilb[ p ] );
      System.out.println( "" );

      double[ ] arrReco = null;

      arrReco = t.reverse( arrFreqOrHilb );

      System.out.println( "reconstruction:" );
      for( int p = 0; p < arrTime.length; p++ )
        System.out.printf( "%9.6f", arrReco[ p ] );
      System.out.println( "" );
    } catch( JWaveFailure e1 ) {
      // TODO Auto-generated catch block
      e1.printStackTrace( );
    }
  } // main

} // class
