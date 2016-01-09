/**
 * Testing transform for a generated - sampled - sine signal.
 *
 * @author Christian Scheiblich (cscheiblich@gmail.com)
 * @date 09.01.2016 08:03:06 
 *
 * SamplingTest.java
 */
package jwave;

import jwave.tools.MathToolKit;

import org.junit.Test;

/**
 * Testing transform for a generated - sampled - sine signal.
 * 
 * @author Christian Scheiblich (cscheiblich@gmail.com)
 * @date 09.01.2016 08:03:06
 */
public class SamplingTest extends Base {

  /**
   * Testing transform for a generated - sampled - sine signal.
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 09.01.2016 08:17:56
   */
  @Test public void testSampling( ) {

    int samplingRate = 1024 * 1024; // sampling rate
    int noOfOscillations = 1024;

    // generate sampled (discrete) sine over 2 pi
    double[ ] arrTime =
        MathToolKit.createSineOscillation( samplingRate, noOfOscillations );

    Transform transform =
        TransformBuilder.create( "Fast Wavelet Transform", "Haar" );

    double[ ] arrHilb = transform.forward( arrTime );

    double[ ] arrReco = transform.reverse( arrHilb );

    assertArray( arrTime, arrReco, 1e-10 );

    // generate sampled (discrete) sine over 2 pi
    arrTime =
        MathToolKit.createCosineOscillation( samplingRate, noOfOscillations );

    arrHilb = transform.forward( arrTime );

    arrReco = transform.reverse( arrHilb );

    assertArray( arrTime, arrReco, 1e-10 );

    transform = TransformBuilder.create( "Wavelet Packet Transform", "Haar" );

    arrHilb = transform.forward( arrTime );

    arrReco = transform.reverse( arrHilb );

    assertArray( arrTime, arrReco, 1e-10 );

    // generate sampled (discrete) sine over 2 pi
    arrTime =
        MathToolKit.createCosineOscillation( samplingRate, noOfOscillations );

    arrHilb = transform.forward( arrTime );

    arrReco = transform.reverse( arrHilb );

    assertArray( arrTime, arrReco, 1e-10 );

  } // testSampling

} // class
