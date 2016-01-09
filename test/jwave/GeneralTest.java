/**
 * Test a 1-D array keeping some real values over all wavelets.
 *
 * @author Christian Scheiblich (cscheiblich@gmail.com)
 * @date 09.01.2016 08:08:57 
 *
 * GeneralTest.java
 */
package jwave;

import java.util.Random;

import jwave.exceptions.JWaveFailure;
import jwave.tools.MathToolKit;
import jwave.transforms.DiscreteFourierTransform;
import jwave.transforms.FastWaveletTransform;
import jwave.transforms.wavelets.Wavelet;
import jwave.transforms.wavelets.WaveletBuilder;

import org.junit.Test;

/**
 * Test a 1-D array keeping some real values over all wavelets.
 * 
 * @author Christian Scheiblich (cscheiblich@gmail.com)
 * @date 09.01.2016 08:08:57
 */
public class GeneralTest extends Base {

  /**
   * Test a 1-D array keeping some real values over all wavelets.
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 22.03.2015 16:13:25
   */
  @Test public void testExample( ) {

    double delta = 1.e-6; // due to a lot of wavelets with different precisions

    Wavelet[ ] arrOfWaveletObjects = WaveletBuilder.create2arr( ); // over 50 wavelets :-p
    int noOfWavelets = arrOfWaveletObjects.length;

    // go for Fast Wavelet Transforms
    for( int w = 0; w < noOfWavelets; w++ ) {

      Wavelet wavelet = arrOfWaveletObjects[ w ];

      System.out
          .println( "Testing example with FWT using " + wavelet.getName( ) );

      Transform fwt = new Transform( new FastWaveletTransform( wavelet ) );

      double[ ] arrTime = { 1.2, 2.3, 3.4, 4.5, 5.4, 4.3, 3.2, 2.1 };
      double[ ] arrHilb = fwt.forward( arrTime );
      double[ ] arrReco = fwt.reverse( arrHilb );

      showTime( arrTime );
      showHilb( arrHilb );
      showTime( arrReco );

      assertArray( arrTime, arrReco, delta );

      int arrLengthRnd = 1024 * 1024; // do long random number array
      double[ ] arrTimeRnd = new double[ arrLengthRnd ];
      Random randomGenerator = new Random( );
      for( int i = 0; i < arrLengthRnd; i++ )
        arrTimeRnd[ i ] = randomGenerator.nextDouble( );
      double[ ] arrHilbRnd = fwt.forward( arrTimeRnd );
      double[ ] arrRecoRnd = fwt.reverse( arrHilbRnd );
      assertArray( arrTimeRnd, arrRecoRnd, delta );

    } // w 

  } // testExample

  /**
   * JUnit for Discrete Fourier Transform
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 22.03.2015 17:03:44
   * @throws JWaveFailure
   */
  @Test public void testDFT( ) throws JWaveFailure {

    int samplingRate = 8; // sampling rate
    int noOfOscillations = 1;

    Transform transform = new Transform( new DiscreteFourierTransform( ) );

    System.out.println( " " );
    // generate sampled (discrete) sine over 2 pi
    double[ ] arrTimeSine =
        MathToolKit.createSineOscillation( samplingRate, noOfOscillations );
    showTime( arrTimeSine );
    double[ ] arrFreqSine = transform.forward( arrTimeSine );
    showFreq( arrFreqSine );
    double[ ] arrRecoSine = transform.reverse( arrFreqSine );
    showTime( arrRecoSine );
    assertArray( arrTimeSine, arrRecoSine, 1e-10 );

    System.out.println( " " );
    // generate sampled (discrete) sine over 2 pi
    double[ ] arrTimeCosine =
        MathToolKit.createCosineOscillation( samplingRate, noOfOscillations );
    showTime( arrTimeCosine );
    double[ ] arrFreqCosine = transform.forward( arrTimeCosine );
    showFreq( arrFreqCosine );
    double[ ] arrRecoCosine = transform.reverse( arrFreqCosine );
    showTime( arrRecoCosine );
    assertArray( arrTimeCosine, arrRecoCosine, 1e-10 );

    transform = TransformBuilder.create( "Fast Wavelet Transform", "Haar" );

    // generate sampled (discrete) sine over 2 pi
    arrTimeSine =
        MathToolKit.createSineOscillation( samplingRate, noOfOscillations );
    // showTime( arrTimeSine );
    arrFreqSine = transform.forward( arrTimeSine );
    // showFreq( arrFreqSine );
    arrRecoSine = transform.reverse( arrFreqSine );
    // showTime( arrRecoSine );
    assertArray( arrTimeSine, arrRecoSine, 1e-10 );

    // generate sampled (discrete) sine over 2 pi
    // showTime( arrTimeCosine );
    arrFreqCosine = transform.forward( arrTimeCosine );
    // showFreq( arrFreqCosine );
    arrRecoCosine = transform.reverse( arrFreqCosine );
    // showTime( arrRecoCosine );
    assertArray( arrTimeCosine, arrRecoCosine, 1e-10 );

  } // testDFT

} // class
