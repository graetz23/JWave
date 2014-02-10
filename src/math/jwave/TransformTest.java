/**
 * @author tucker 10.02.2014 21:32:22 TransformTest.java
 */
package math.jwave;

import static org.junit.Assert.*;
import math.jwave.datatypes.Complex;
import math.jwave.exc.JWaveException;
import math.jwave.transforms.FastWaveletTransform;
import math.jwave.transforms.wavelets.Coiflet06;
import math.jwave.transforms.wavelets.Daubechie02;
import math.jwave.transforms.wavelets.Daubechie03;
import math.jwave.transforms.wavelets.Daubechie04;
import math.jwave.transforms.wavelets.Haar01;
import math.jwave.transforms.wavelets.Legendre01;
import math.jwave.transforms.wavelets.Legendre02;
import math.jwave.transforms.wavelets.Legendre03;
import math.jwave.transforms.wavelets.Wavelet;

import org.junit.Test;

/**
 * @author tucker 10.02.2014 21:32:22
 */
public class TransformTest {

  /**
   * Test method for {@link math.jwave.Transform#forward(double[])}.
   */
  @Test public void testForwardDoubleArray( ) {

    System.out.println( "" );
    System.out.println( "testRoundingHaar02FWT" );

    double delta = 1.e-8;

    double[ ] arrTime = { 1., 1., 1., 1., 1., 1., 1., 1. };

    try {

      testFastWaveletTransformRounding( arrTime, new Haar01( ), delta );
      testFastWaveletTransformRounding( arrTime, new Daubechie02( ), delta );
      // testFastWaveletTransformRounding( arrTime, new Daubechie03( ), delta ); // not passed yet -> @Deprecated
      // testFastWaveletTransformRounding( arrTime, new Daubechie04( ), delta ); // not passed yet -> @Deprecated
      testFastWaveletTransformRounding( arrTime, new Legendre01( ), delta );
      // testFastWaveletTransformRounding( arrTime, new Legendre02( ), delta ); // not passed yet -> @Deprecated
      // testFastWaveletTransformRounding( arrTime, new Legendre03( ), delta ); // not passed yet -> @Deprecated
      // testFastWaveletTransformRounding( arrTime, new Coiflet06( ), delta ); // not passed yet -> @Deprecated

    } catch( JWaveException e ) {

      e.showMessage( );
      e.printStackTrace( );

    } // try

  } // testForwardDoubleArray

  /**
   * Test method for {@link math.jwave.Transform#reverse(double[])}.
   */
  @Test public void testReverseDoubleArray( ) {
    fail( "Not yet implemented" );
  }

  /**
   * Test method for {@link math.jwave.Transform#forward(double[][])}.
   */
  @Test public void testForwardDoubleArrayArray( ) {
    fail( "Not yet implemented" );
  }

  /**
   * Test method for {@link math.jwave.Transform#reverse(double[][])}.
   */
  @Test public void testReverseDoubleArrayArray( ) {
    fail( "Not yet implemented" );
  }

  /**
   * Test method for {@link math.jwave.Transform#forward(double[][][])}.
   */
  @Test public void testForwardDoubleArrayArrayArray( ) {
    fail( "Not yet implemented" );
  }

  /**
   * Test method for {@link math.jwave.Transform#reverse(double[][][])}.
   */
  @Test public void testReverseDoubleArrayArrayArray( ) {
    fail( "Not yet implemented" );
  }

  /**
   * Test method to check the rounding error of several forward and reverse
   * transforms using the Fast Wavelet Transform algorithm and any given Wavelet
   * object as input.
   * 
   * @date 10.02.2010 10:28:00
   * @author Christian Scheiblich
   * @throws JWaveException
   */
  public void testFastWaveletTransformRounding( double[ ] arr, Wavelet wavelet,
      double delta ) throws JWaveException {

    long noOfSteps = 10000000;

    noOfSteps = 1000;

    double[ ] arrTime = arr;

    showTime( arrTime );

    double[ ] arrTimeRound = new double[ arrTime.length ];
    for( int c = 0; c < arrTime.length; c++ )
      arrTimeRound[ c ] = arrTime[ c ];

    Transform t = new Transform( new FastWaveletTransform( wavelet ) );

    System.out.println( "" );
    System.out.println( "" );
    System.out.print( "Performing: " + noOfSteps
        + " forward and reverse transforms ..." );
    // Bar bar = new Bar( new BarHandlerOs( noOfSteps ) );
    for( long s = 0; s < noOfSteps; s++ ) {
      arrTimeRound = t.reverse( t.forward( arrTimeRound ) );
      // bar.trigger( );
    } // s
    System.out.println( "" );
    System.out.println( "" );

    assertArray( arrTime, arrTimeRound, delta );

    System.out.println( "Input ..." );
    showTime( arrTime );
    System.out.println( "" );

    System.out.println( "Result ..." );
    showTime( arrTimeRound );
    System.out.println( "" );

    double[ ] arrTimeErrorAbs = new double[ arrTimeRound.length ];
    for( int c = 0; c < arrTimeRound.length; c++ )
      arrTimeErrorAbs[ c ] = Math.abs( arrTimeRound[ c ] - arrTime[ c ] );

    System.out.println( "Absolute error" );
    showTime( arrTimeErrorAbs );
    System.out.println( "" );

    double[ ] arrTimeErrorRel = new double[ arrTimeRound.length ];
    for( int c = 0; c < arrTimeRound.length; c++ )
      arrTimeErrorRel[ c ] =
          Math.abs( ( arrTimeRound[ c ] - arrTime[ c ] ) * 100. / arrTime[ c ] );

    System.out.println( "Relative error [%] ..." );
    showTime( arrTimeErrorRel );
    System.out.println( "" );

  } // testFastWaveletTransformRounding

  public void
      assertArray( Complex[ ] expected, Complex[ ] actual, double delta ) {

    int expectedLength = expected.length;
    int actualLength = actual.length;

    assertEquals( expectedLength, actualLength );

    for( int c = 0; c < expectedLength; c++ ) {

      double expectedReal = expected[ c ].getReal( );
      double expectedImag = expected[ c ].getImag( );

      double actualReal = actual[ c ].getReal( );
      double actualImag = actual[ c ].getImag( );

      assertEquals( expectedReal, actualReal, delta );
      assertEquals( expectedImag, actualImag, delta );

    } // c

  } // assertArray

  protected void
      assertArray( double[ ] expected, double[ ] actual, double delta ) {
    for( int i = 0; i < expected.length; i++ )
      assertEquals( expected[ i ], actual[ i ], delta );
  } // assertMatrix

  protected void assertMatrix( double[ ][ ] expected, double[ ][ ] actual,
      double delta ) {
    for( int i = 0; i < expected.length; i++ )
      for( int j = 0; j < expected[ i ].length; j++ )
        assertEquals( expected[ i ][ j ], actual[ i ][ j ], delta );
  } // assertMatrix

  protected void assertSpace( double[ ][ ][ ] expected, double[ ][ ][ ] actual,
      double delta ) {
    for( int i = 0; i < expected.length; i++ )
      for( int j = 0; j < expected[ i ].length; j++ )
        for( int k = 0; k < expected[ i ][ j ].length; k++ )
          assertEquals( expected[ i ][ j ][ k ], actual[ i ][ j ][ k ], delta );
  } // assertSpace

  protected void showTime( double[ ] arrTime ) {
    System.out.print( "time domain: " + "\t" + "\t" );
    for( int c = 0; c < arrTime.length; c++ )
      System.out.print( arrTime[ c ] + " " );
    System.out.println( "" );
  } // showTime

  protected void showFreq( double[ ] arrFreq ) {
    System.out.print( "frequency domain: " + "\t" );
    for( int c = 0; c < arrFreq.length; c++ )
      System.out.print( arrFreq[ c ] + " " );
    System.out.println( "" );
  } // showHilb

  protected void showHilb( double[ ] arrHilb ) {
    System.out.print( "Hilbert domain: " + "\t" );
    for( int c = 0; c < arrHilb.length; c++ )
      System.out.print( arrHilb[ c ] + " " );
    System.out.println( "" );
  } // showHilb

  protected void showTime( Complex[ ] arrTime ) {
    System.out.print( "time domain: " + "\t" + "\t" );
    for( int c = 0; c < arrTime.length; c++ )
      System.out.print( arrTime[ c ].toString( ) + " " );
    System.out.println( "" );
  } // showTime

  protected void showFreq( Complex[ ] arrFreq ) {
    System.out.print( "frequency domain: " + "\t" );
    for( int c = 0; c < arrFreq.length; c++ )
      System.out.print( arrFreq[ c ].toString( ) + " " );
    System.out.println( "" );
  } // showHilb

  protected void showTime( double[ ][ ] matrixTime ) {
    System.out.println( "time domain: " + "\t" );
    for( int i = 0; i < matrixTime.length; i++ ) {
      for( int j = 0; j < matrixTime[ i ].length; j++ )
        System.out.print( matrixTime[ i ][ j ] + " " );
      System.out.println( "" );
    }
    System.out.println( "" );
  } // showTime

  protected void showFreq( double[ ][ ] matrixFreq ) {
    System.out.println( "frequency domain: " + "\t" );
    for( int i = 0; i < matrixFreq.length; i++ ) {
      for( int j = 0; j < matrixFreq[ i ].length; j++ )
        System.out.print( matrixFreq[ i ][ j ] + " " );
      System.out.println( "" );
    }
    System.out.println( "" );
  } // showFreq

  protected void showHilb( double[ ][ ] matrixHilb ) {
    System.out.println( "Hilbert domain: " + "\t" );
    for( int i = 0; i < matrixHilb.length; i++ ) {
      for( int j = 0; j < matrixHilb[ i ].length; j++ )
        System.out.print( matrixHilb[ i ][ j ] + " " );
      System.out.println( "" );
    }
    System.out.println( "" );
  } // showHilb

  protected void showTime( double[ ][ ][ ] spaceTime ) {
    System.out.println( "time domain: " + "\t" );
    for( int i = 0; i < spaceTime.length; i++ ) {
      for( int j = 0; j < spaceTime[ i ].length; j++ ) {
        for( int k = 0; k < spaceTime[ i ][ j ].length; k++ )
          System.out.print( spaceTime[ i ][ j ][ k ] + " " );
        System.out.println( "" );
      }
      System.out.println( "" );
    }
    System.out.println( "" );
  } // showTime

  protected void showFreq( double[ ][ ][ ] spaceTime ) {
    System.out.println( "frequency domain: " + "\t" );
    for( int i = 0; i < spaceTime.length; i++ ) {
      for( int j = 0; j < spaceTime[ i ].length; j++ ) {
        for( int k = 0; k < spaceTime[ i ][ j ].length; k++ )
          System.out.print( spaceTime[ i ][ j ][ k ] + " " );
        System.out.println( "" );
      }
      System.out.println( "" );
    }
    System.out.println( "" );
  } // showFreq

  protected void showHilb( double[ ][ ][ ] spaceTime ) {
    System.out.println( "Hilbert domain: " + "\t" );
    for( int i = 0; i < spaceTime.length; i++ ) {
      for( int j = 0; j < spaceTime[ i ].length; j++ ) {
        for( int k = 0; k < spaceTime[ i ][ j ].length; k++ )
          System.out.print( spaceTime[ i ][ j ][ k ] + " " );
        System.out.println( "" );
      }
      System.out.println( "" );
    }
    System.out.println( "" );
  } // showHilb

} // TransformTest
