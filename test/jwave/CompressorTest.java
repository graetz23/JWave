/**
 * JUnit test cases for data compression using wavelets.
 *
 * @author Christian Scheiblich (cscheiblich@gmail.com)
 * @date 09.01.2016 07:29:24 
 *
 * CompressorTest.java
 */
package jwave;

import jwave.compressions.Compressor;
import jwave.compressions.CompressorMagnitude;
import jwave.tools.MathToolKit;
import jwave.transforms.FastWaveletTransform;
import jwave.transforms.wavelets.Wavelet;
import jwave.transforms.wavelets.WaveletBuilder;

import org.junit.Test;

/**
 * JUnit test cases for data compression using wavelets.
 * 
 * @author Christian Scheiblich (cscheiblich@gmail.com)
 * @date 09.01.2016 07:29:24
 */
public class CompressorTest extends Base {

  /**
   * Test the Compressor classes by giving an example for a strong data
   * compression with several wavelets for a sampled (generated) sine signal.
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 06.01.2016 23:34:05
   */
  @Test public void testCompressionSine( ) {

    Compressor compressor = new CompressorMagnitude( 1.0 );

    Wavelet[ ] arrOfWaveletObjects = WaveletBuilder.create2arr( ); // over 50 wavelets :-p
    int noOfWavelets = arrOfWaveletObjects.length;

    int samplingRate = 1024 * 1024; // sampling rate
    int noOfOscillations = 1024;

    // generate sampled (discrete) sine over 2 pi
    double[ ] arrTime =
        MathToolKit.createSineOscillation( samplingRate, noOfOscillations );

    // go for Fast Wavelet Transforms
    for( int w = 0; w < noOfWavelets; w++ ) {

      Wavelet wavelet = arrOfWaveletObjects[ w ];

      System.out
          .println( "Testing example with FWT using " + wavelet.getName( ) );

      Transform fwt = new Transform( new FastWaveletTransform( wavelet ) );

      double[ ] arrHilb = fwt.forward( arrTime );

      double[ ] arrComp = compressor.compress( arrHilb );

      double[ ] arrReco = fwt.reverse( arrComp );

      // calculate and print the absolute difference
      int pos = 0;
      double maxDiff = 0.;
      for( int i = 0; i < arrReco.length; i++ ) {
        double diff = Math.abs( arrTime[ i ] - arrReco[ i ] );
        if( diff > maxDiff ) {
          maxDiff = diff;
          pos = i;
        } // if
      } // i
      System.out.println( "absolute max difference at position " + pos
          + " is: " + maxDiff );

      //calculate the compression rate
      double compressionRate = compressor.calcCompressionRate( arrComp );
      System.out.println( "The achieved compression rate: " + compressionRate );

      System.out.println( );

    } // w 

  } // testCompressionSine

  /**
   * Test the Compressor classes by giving an example for data compression with
   * wavelets.
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 14.05.2015 18:26:03
   */
  @Test public void testCompressionSteps( ) {

    Compressor compressor = new CompressorMagnitude( 1.0 );

    Wavelet[ ] arrOfWaveletObjects = WaveletBuilder.create2arr( ); // over 50 wavelets :-p
    int noOfWavelets = arrOfWaveletObjects.length;

    // go for Fast Wavelet Transforms
    for( int w = 0; w < noOfWavelets; w++ ) {

      Wavelet wavelet = arrOfWaveletObjects[ w ];

      System.out
          .println( "Testing example with FWT using " + wavelet.getName( ) );

      Transform fwt = new Transform( new FastWaveletTransform( wavelet ) );

      double[ ] arrTime =
          { 1., 2., 3., 4., 5., 4., 3., 2., 1., 0., -1., -2., -3, -2., -1., 0. };

      double[ ] arrHilb = fwt.forward( arrTime );

      double[ ] arrComp = compressor.compress( arrHilb );

      double[ ] arrReco = fwt.reverse( arrComp );

      showTime( arrTime );
      showHilb( arrHilb );
      showHilb( arrComp );
      showTime( arrReco );

    } // w 

  } // testCompressionSteps

} // CompressorTest
