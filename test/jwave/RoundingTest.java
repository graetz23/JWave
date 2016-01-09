/**
 * Testing the growing of rounding errors by forward and reverse transforming
 * around a thousand times.
 *
 * @author Christian Scheiblich (cscheiblich@gmail.com)
 * @date 09.01.2016 07:49:21 
 *
 * RoundingTest.java
 */
package jwave;

import jwave.transforms.FastWaveletTransform;
import jwave.transforms.WaveletPacketTransform;
import jwave.transforms.wavelets.Wavelet;
import jwave.transforms.wavelets.WaveletBuilder;
import jwave.transforms.wavelets.haar.Haar1Orthogonal;
import jwave.transforms.wavelets.legendre.Legendre1;
import jwave.transforms.wavelets.legendre.Legendre2;
import jwave.transforms.wavelets.legendre.Legendre3;
import jwave.transforms.wavelets.other.DiscreteMayer;

import org.junit.Test;

/**
 * Testing the growing of rounding errors by forward and reverse transforming
 * around a thousand times.
 * 
 * @author Christian Scheiblich (cscheiblich@gmail.com)
 * @date 09.01.2016 07:49:21
 */
public class RoundingTest extends Base {

  /**
   * Test method for {@link jwave.Transform#forward(double[])} and
   * {@link jwave.Transform#reverse(double[])}..
   */
  @Test public void testRounding( ) {

    System.out.println( "" );
    System.out.println( "testRounding" );
    System.out.println( "" );

    int arrTimeLength = 1024; // 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048, 4096, 8192, 16384, 32768, ..

    double[ ] arrTime = new double[ arrTimeLength ];

    for( int i = 0; i < arrTime.length; i++ )
      arrTime[ i ] = 1.; // for calculating rounding error and to allow work for filters ;-) 

    double delta = 1.e-8; // define a request rounding error that the test checks to be held

    Wavelet[ ] arrOfWaveletObjects = WaveletBuilder.create2arr( ); // over 50 wavelets :-p
    int noOfWavelets = arrOfWaveletObjects.length;

    for( int w = 0; w < noOfWavelets; w++ ) { // loop over Wavelets

      Wavelet wavelet = arrOfWaveletObjects[ w ];

      testRoundingFWT( arrTime, wavelet, delta );
      testRoundingWPT( arrTime, wavelet, delta );
      System.out.println( "" );

    } // w

    System.out
        .println( "testRounding Haar Orthogonal - 1000 transforms => rounding error: "
            + delta );
    testRoundingFWT( arrTime, new Haar1Orthogonal( ), delta );
    testRoundingWPT( arrTime, new Haar1Orthogonal( ), delta );
    System.out.println( "" );

    System.out
        .println( "testRounding Legendre 1 - 1000 transforms => rounding error: "
            + delta );
    testRoundingFWT( arrTime, new Legendre1( ), delta );
    testRoundingWPT( arrTime, new Legendre1( ), delta );
    System.out.println( "" );

    System.out
        .println( "testRounding Legendre 2 - 1000 transforms => rounding error: "
            + delta );
    testRoundingFWT( arrTime, new Legendre2( ), delta );
    testRoundingWPT( arrTime, new Legendre2( ), delta );
    System.out.println( "" );

    System.out
        .println( "testRounding Legendre 3 - 1000 transforms => rounding error: "
            + delta );
    testRoundingFWT( arrTime, new Legendre3( ), delta );
    testRoundingWPT( arrTime, new Legendre3( ), delta );
    System.out.println( "" );

    System.out
        .println( "testRounding Discrete Mayer - 1000 transforms => rounding error: "
            + delta );
    testRoundingFWT( arrTime, new DiscreteMayer( ), 1.e-2 );
    System.out.println( "" );

    //      System.out
    //          .println( "testRounding LeGall 5/3 - 1000 transforms => rounding error: "
    //              + delta );
    //      testFastWaveletTransformRounding( arrTime, new CDF53( ), 1.e-2 );
    //      System.out.println( "" );      

    //      System.out
    //          .println( "testRounding Battle23 - 1000 transforms => rounding error: "
    //              + delta );
    //      testFastWaveletTransformRounding( arrTime, new Battle23( ), delta );
    //      System.out.println( "" );

  } // testRounding

  /**
   * Test method to check the rounding error of several forward and reverse
   * transforms using the Fast Wavelet Transform algorithm and any given Wavelet
   * object as input.
   * 
   * @date 22.03.2015 16:00:57
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   */
  private void testRoundingFWT( double[ ] arr,
      Wavelet wavelet, double delta ) {

    long noOfSteps = 1000;
    double[ ] arrTime = arr;

    double[ ] arrTimeRound = new double[ arrTime.length ];
    for( int c = 0; c < arrTime.length; c++ )
      arrTimeRound[ c ] = arrTime[ c ];

    Transform t = new Transform( new FastWaveletTransform( wavelet ) );

    System.out.println( "testRounding FWT " + wavelet.getName( ) + " - "
        + noOfSteps + " transforms => rounding error: " + delta );

    System.out.print( "Performing: " + noOfSteps
        + " forward and reverse transforms ..." );
    for( long s = 0; s < noOfSteps; s++ )
      arrTimeRound = t.reverse( t.forward( arrTimeRound ) );
    System.out.println( "done!" );

    assertArray( arrTime, arrTimeRound, delta );

    double timeErrorAbs = 0.;
    for( int c = 0; c < arrTimeRound.length; c++ )
      timeErrorAbs += Math.abs( arrTimeRound[ c ] - arrTime[ c ] );
    System.out.println( "Absolute error: " + timeErrorAbs );

    double timeErrorRel = 0.;
    for( int c = 0; c < arrTimeRound.length; c++ )
      timeErrorRel +=
          Math.abs( ( arrTimeRound[ c ] - arrTime[ c ] ) * 100. / arrTime[ c ] );

    System.out.println( "Relative error [%]: " + timeErrorRel );

  } // testFastWaveletTransformRounding

  /**
   * Test method to check the rounding error of several forward and reverse
   * transforms using the Wavelet Packet Transform algorithm and any given
   * Wavelet object as input.
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 22.03.2015 16:00:57
   * @param arr
   * @param wavelet
   * @param delta
   */
  private void testRoundingWPT( double[ ] arr,
      Wavelet wavelet, double delta ) {

    long noOfSteps = 256;
    double[ ] arrTime = arr;

    double[ ] arrTimeRound = new double[ arrTime.length ];
    for( int c = 0; c < arrTime.length; c++ )
      arrTimeRound[ c ] = arrTime[ c ];

    System.out.println( "testRounding WPT " + wavelet.getName( ) + " - "
        + noOfSteps + " transforms => rounding error: " + delta );

    Transform t = new Transform( new WaveletPacketTransform( wavelet ) );

    System.out.print( "Performing: " + noOfSteps
        + " forward and reverse transforms ..." );
    for( long s = 0; s < noOfSteps; s++ )
      arrTimeRound = t.reverse( t.forward( arrTimeRound ) );
    System.out.println( "done!" );

    assertArray( arrTime, arrTimeRound, delta );

    double timeErrorAbs = 0.;
    for( int c = 0; c < arrTimeRound.length; c++ )
      timeErrorAbs += Math.abs( arrTimeRound[ c ] - arrTime[ c ] );
    System.out.println( "Absolute error: " + timeErrorAbs );

    double timeErrorRel = 0.;
    for( int c = 0; c < arrTimeRound.length; c++ )
      timeErrorRel +=
          Math.abs( ( arrTimeRound[ c ] - arrTime[ c ] ) * 100. / arrTime[ c ] );

    System.out.println( "Relative error [%]: " + timeErrorRel );

  } // testWaveletPacketTransformRounding

} // class
