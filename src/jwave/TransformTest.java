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
package jwave;

import static org.junit.Assert.*;

import java.util.Random;

import jwave.compressions.Compressor;
import jwave.compressions.CompressorMagnitude;
import jwave.datatypes.natives.Complex;
import jwave.exceptions.JWaveFailure;
import jwave.tools.MathToolKit;
import jwave.transforms.DiscreteFourierTransform;
import jwave.transforms.FastWaveletTransform;
import jwave.transforms.WaveletPacketTransform;
import jwave.transforms.wavelets.Wavelet;
import jwave.transforms.wavelets.WaveletBuilder;
import jwave.transforms.wavelets.haar.Haar1;
import jwave.transforms.wavelets.haar.Haar1Orthogonal;
import jwave.transforms.wavelets.legendre.Legendre1;
import jwave.transforms.wavelets.legendre.Legendre2;
import jwave.transforms.wavelets.legendre.Legendre3;
import jwave.transforms.wavelets.other.DiscreteMayer;

import org.junit.Test;

/**
 * @author Christian Scheiblich (cscheiblich@gmail.com)
 * @date 10.02.2014 21:32:22
 */
public class TransformTest {

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

  } // testSampling

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

  } // testSamplingSine

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

      testFastWaveletTransformRounding( arrTime, wavelet, delta );
      testWaveletPacketTransformRounding( arrTime, wavelet, delta );
      System.out.println( "" );

    } // w

    System.out
        .println( "testRounding Haar Orthogonal - 1000 transforms => rounding error: "
            + delta );
    testFastWaveletTransformRounding( arrTime, new Haar1Orthogonal( ), delta );
    testWaveletPacketTransformRounding( arrTime, new Haar1Orthogonal( ), delta );
    System.out.println( "" );

    System.out
        .println( "testRounding Legendre 1 - 1000 transforms => rounding error: "
            + delta );
    testFastWaveletTransformRounding( arrTime, new Legendre1( ), delta );
    testWaveletPacketTransformRounding( arrTime, new Legendre1( ), delta );
    System.out.println( "" );

    System.out
        .println( "testRounding Legendre 2 - 1000 transforms => rounding error: "
            + delta );
    testFastWaveletTransformRounding( arrTime, new Legendre2( ), delta );
    testWaveletPacketTransformRounding( arrTime, new Legendre2( ), delta );
    System.out.println( "" );

    System.out
        .println( "testRounding Legendre 3 - 1000 transforms => rounding error: "
            + delta );
    testFastWaveletTransformRounding( arrTime, new Legendre3( ), delta );
    testWaveletPacketTransformRounding( arrTime, new Legendre3( ), delta );
    System.out.println( "" );

    System.out
        .println( "testRounding Discrete Mayer - 1000 transforms => rounding error: "
            + delta );
    testFastWaveletTransformRounding( arrTime, new DiscreteMayer( ), 1.e-2 );
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
   * Test some real values over all wavelets.
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
   * JUnit tests for the stepping methods to certain levels of Hilbert space.
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 22.03.2015 11:47:49
   */
  @Test public void testStepping( ) {

    // run this part for any wavelet that is available in JWave!

    double delta = 1.e-8; // due to a lot of wavelets with different precisions
    Wavelet[ ] arrOfWaveletObjects = WaveletBuilder.create2arr( ); // over 50 wavelets :-p
    int noOfWavelets = arrOfWaveletObjects.length;

    // go for Fast Wavelet Transforms
    for( int w = 0; w < noOfWavelets; w++ ) {

      Wavelet wavelet = arrOfWaveletObjects[ w ];

      System.out.println( "" );
      System.out.println( "Testing the Fast Wavelet Transform "
          + "stepping forward and reverse 1-D methods " + "using "
          + wavelet.getName( ) + " and small array" );

      double[ ] arrTime = { 1., 1., 1., 1. };
      double sqrt2 = Math.sqrt( 2. );
      double[ ][ ] expected =
          { { 1., 1., 1., 1., }, { sqrt2, sqrt2, 0., 0. }, { 2., 0., 0., 0. } }; // orthonormal Hilbert space

      Transform fwt = new Transform( new FastWaveletTransform( wavelet ) );

      double[ ] arrHilbLevel0 = fwt.forward( arrTime, 0 );
      double[ ] arrHilbLevel1 = fwt.forward( arrTime, 1 );
      double[ ] arrHilbLevel2 = fwt.forward( arrTime, 2 );

      assertArray( expected[ 0 ], arrHilbLevel0, delta );
      assertArray( expected[ 1 ], arrHilbLevel1, delta );
      assertArray( expected[ 2 ], arrHilbLevel2, delta );

      double[ ] arrTimeFromLevel0 = fwt.reverse( arrHilbLevel0, 0 );
      double[ ] arrTimeFromLevel1 = fwt.reverse( arrHilbLevel1, 1 );
      double[ ] arrTimeFromLevel2 = fwt.reverse( arrHilbLevel2, 2 );

      assertArray( arrTime, arrTimeFromLevel0, delta );
      assertArray( arrTime, arrTimeFromLevel1, delta );
      assertArray( arrTime, arrTimeFromLevel2, delta );

      System.out.println( "Testing the Fast Wavelet Transform "
          + "decompose, recompose, and recomposeFromLevel 1-D methods "
          + "using " + wavelet.getName( ) + " and long array" );

      double[ ] arrTime64 = { // array of length 64
          1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1.,
              1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1.,
              1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1.,
              1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1. };

      double d1sqrt2 = 1 * sqrt2; // symbolic one times square root of 2
      double d2sqrt2 = 2. * d1sqrt2; // 2 times square root of two
      double d4sqrt2 = 2. * d2sqrt2; // 4 times square root of two

      double[ ][ ] expected64 =
          {
              { // array of length 64
              1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1.,
                  1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1.,
                  1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1.,
                  1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1.,
                  1., 1., 1. },

              { d1sqrt2, d1sqrt2, d1sqrt2, d1sqrt2, d1sqrt2, d1sqrt2, d1sqrt2,
                  d1sqrt2, d1sqrt2, d1sqrt2, d1sqrt2, d1sqrt2, d1sqrt2,
                  d1sqrt2, d1sqrt2, d1sqrt2, d1sqrt2, d1sqrt2, d1sqrt2,
                  d1sqrt2, d1sqrt2, d1sqrt2, d1sqrt2, d1sqrt2, d1sqrt2,
                  d1sqrt2, d1sqrt2, d1sqrt2, d1sqrt2, d1sqrt2, d1sqrt2,
                  d1sqrt2, 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.,
                  0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.,
                  0., 0., 0., 0. },

              { 2., 2., 2., 2., 2., 2., 2., 2., 2., 2., 2., 2., 2., 2., 2., 2.,
                  0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.,
                  0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.,
                  0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.,
                  0., 0., 0. },

              { d2sqrt2, d2sqrt2, d2sqrt2, d2sqrt2, d2sqrt2, d2sqrt2, d2sqrt2,
                  d2sqrt2, 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.,
                  0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.,
                  0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.,
                  0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0. },

              { 4., 4., 4., 4., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.,
                  0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.,
                  0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.,
                  0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.,
                  0., 0., 0. },

              { d4sqrt2, d4sqrt2, 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.,
                  0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.,
                  0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.,
                  0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.,
                  0., 0., 0., 0., 0., 0. },

              { 8., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.,
                  0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.,
                  0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.,
                  0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.,
                  0., 0., 0. },

          }; // orthonormal Hilbert space

      // test reconstruction from a certain level of decomposition
      double[ ] arrHilb64Level0 = fwt.forward( arrTime64, 0 );
      assertArray( expected64[ 0 ], arrHilb64Level0, delta );
      double[ ] arrTime64FromLevel0 = fwt.reverse( arrHilb64Level0, 0 );
      assertArray( arrTime64, arrTime64FromLevel0, delta );

      double[ ] arrHilb64Level1 = fwt.forward( arrTime64, 1 );
      assertArray( expected64[ 1 ], arrHilb64Level1, delta );
      double[ ] arrTime64FromLevel1 = fwt.reverse( arrHilb64Level1, 1 );
      assertArray( arrTime64, arrTime64FromLevel1, delta );

      double[ ] arrHilb64Level2 = fwt.forward( arrTime64, 2 );
      assertArray( expected64[ 2 ], arrHilb64Level2, delta );
      double[ ] arrTime64FromLevel2 = fwt.reverse( arrHilb64Level2, 2 );
      assertArray( arrTime64, arrTime64FromLevel2, delta );

      double[ ] arrHilb64Level3 = fwt.forward( arrTime64, 3 );
      assertArray( expected64[ 3 ], arrHilb64Level3, delta );
      double[ ] arrTime64FromLevel3 = fwt.reverse( arrHilb64Level3, 3 );
      assertArray( arrTime64, arrTime64FromLevel3, delta );

      double[ ] arrHilb64Level4 = fwt.forward( arrTime64, 4 );
      assertArray( expected64[ 4 ], arrHilb64Level4, delta );
      double[ ] arrTime64FromLevel4 = fwt.reverse( arrHilb64Level4, 4 );
      assertArray( arrTime64, arrTime64FromLevel4, delta );

      double[ ] arrHilb64Level5 = fwt.forward( arrTime64, 5 );
      assertArray( expected64[ 5 ], arrHilb64Level5, delta );
      double[ ] arrTime64FromLevel5 = fwt.reverse( arrHilb64Level5, 5 );
      assertArray( arrTime64, arrTime64FromLevel5, delta );

      double[ ] arrHilb64Level6 = fwt.forward( arrTime64, 6 );
      assertArray( expected64[ 6 ], arrHilb64Level6, delta );
      double[ ] arrTime64FromLevel6 = fwt.reverse( arrHilb64Level6, 6 );
      assertArray( arrTime64, arrTime64FromLevel6, delta );

    } // FWT over all wavelets

    // go for Wavelet Packet Transforms
    for( int w = 0; w < noOfWavelets; w++ ) {

      Wavelet wavelet = arrOfWaveletObjects[ w ];

      System.out.println( "" );
      System.out.println( "Testing the Fast Wavelet Transform "
          + "stepping forward and reverser 1-D methods " + "using "
          + wavelet.getName( ) + " and small array" );

      double[ ] arrTime = { 1., 1., 1., 1. };
      double sqrt2 = Math.sqrt( 2. );
      double[ ][ ] expected =
          { { 1., 1., 1., 1., }, { sqrt2, sqrt2, 0., 0. }, { 2., 0., 0., 0. } }; // orthonormal Hilbert space

      Transform wpt = new Transform( new WaveletPacketTransform( wavelet ) );

      double[ ] arrHilbLevel0 = wpt.forward( arrTime, 0 );
      double[ ] arrHilbLevel1 = wpt.forward( arrTime, 1 );
      double[ ] arrHilbLevel2 = wpt.forward( arrTime, 2 );

      assertArray( expected[ 0 ], arrHilbLevel0, delta );
      assertArray( expected[ 1 ], arrHilbLevel1, delta );
      assertArray( expected[ 2 ], arrHilbLevel2, delta );

      double[ ] arrTimeFromLevel0 = wpt.reverse( arrHilbLevel0, 0 );
      double[ ] arrTimeFromLevel1 = wpt.reverse( arrHilbLevel1, 1 );
      double[ ] arrTimeFromLevel2 = wpt.reverse( arrHilbLevel2, 2 );

      assertArray( arrTime, arrTimeFromLevel0, delta );
      assertArray( arrTime, arrTimeFromLevel1, delta );
      assertArray( arrTime, arrTimeFromLevel2, delta );

      System.out.println( "Testing the Fast Wavelet Transform "
          + "decompose, recompose, and recomposeFromLevel 1-D methods "
          + "using " + wavelet.getName( ) + " and long array" );

      double[ ] arrTime64 = { // array of length 64
          1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1.,
              1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1.,
              1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1.,
              1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1. };

      double d1sqrt2 = 1 * sqrt2; // symbolic one times square root of 2
      double d2sqrt2 = 2. * d1sqrt2; // 2 times square root of two
      double d4sqrt2 = 2. * d2sqrt2; // 4 times square root of two

      double[ ][ ] expected64 =
          {
              { // array of length 64
              1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1.,
                  1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1.,
                  1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1.,
                  1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1.,
                  1., 1., 1. },

              { d1sqrt2, d1sqrt2, d1sqrt2, d1sqrt2, d1sqrt2, d1sqrt2, d1sqrt2,
                  d1sqrt2, d1sqrt2, d1sqrt2, d1sqrt2, d1sqrt2, d1sqrt2,
                  d1sqrt2, d1sqrt2, d1sqrt2, d1sqrt2, d1sqrt2, d1sqrt2,
                  d1sqrt2, d1sqrt2, d1sqrt2, d1sqrt2, d1sqrt2, d1sqrt2,
                  d1sqrt2, d1sqrt2, d1sqrt2, d1sqrt2, d1sqrt2, d1sqrt2,
                  d1sqrt2, 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.,
                  0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.,
                  0., 0., 0., 0. },

              { 2., 2., 2., 2., 2., 2., 2., 2., 2., 2., 2., 2., 2., 2., 2., 2.,
                  0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.,
                  0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.,
                  0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.,
                  0., 0., 0. },

              { d2sqrt2, d2sqrt2, d2sqrt2, d2sqrt2, d2sqrt2, d2sqrt2, d2sqrt2,
                  d2sqrt2, 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.,
                  0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.,
                  0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.,
                  0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0. },

              { 4., 4., 4., 4., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.,
                  0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.,
                  0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.,
                  0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.,
                  0., 0., 0. },

              { d4sqrt2, d4sqrt2, 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.,
                  0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.,
                  0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.,
                  0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.,
                  0., 0., 0., 0., 0., 0. },

              { 8., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.,
                  0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.,
                  0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.,
                  0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.,
                  0., 0., 0. },

          }; // orthonormal Hilbert space

      // test reconstruction from a certain level of decomposition
      double[ ] arrHilb64Level0 = wpt.forward( arrTime64, 0 );
      assertArray( expected64[ 0 ], arrHilb64Level0, delta );
      double[ ] arrTime64FromLevel0 = wpt.reverse( arrHilb64Level0, 0 );
      assertArray( arrTime64, arrTime64FromLevel0, delta );

      double[ ] arrHilb64Level1 = wpt.forward( arrTime64, 1 );
      assertArray( expected64[ 1 ], arrHilb64Level1, delta );
      double[ ] arrTime64FromLevel1 = wpt.reverse( arrHilb64Level1, 1 );
      assertArray( arrTime64, arrTime64FromLevel1, delta );

      double[ ] arrHilb64Level2 = wpt.forward( arrTime64, 2 );
      assertArray( expected64[ 2 ], arrHilb64Level2, delta );
      double[ ] arrTime64FromLevel2 = wpt.reverse( arrHilb64Level2, 2 );
      assertArray( arrTime64, arrTime64FromLevel2, delta );

      double[ ] arrHilb64Level3 = wpt.forward( arrTime64, 3 );
      assertArray( expected64[ 3 ], arrHilb64Level3, delta );
      double[ ] arrTime64FromLevel3 = wpt.reverse( arrHilb64Level3, 3 );
      assertArray( arrTime64, arrTime64FromLevel3, delta );

      double[ ] arrHilb64Level4 = wpt.forward( arrTime64, 4 );
      assertArray( expected64[ 4 ], arrHilb64Level4, delta );
      double[ ] arrTime64FromLevel4 = wpt.reverse( arrHilb64Level4, 4 );
      assertArray( arrTime64, arrTime64FromLevel4, delta );

      double[ ] arrHilb64Level5 = wpt.forward( arrTime64, 5 );
      assertArray( expected64[ 5 ], arrHilb64Level5, delta );
      double[ ] arrTime64FromLevel5 = wpt.reverse( arrHilb64Level5, 5 );
      assertArray( arrTime64, arrTime64FromLevel5, delta );

      double[ ] arrHilb64Level6 = wpt.forward( arrTime64, 6 );
      assertArray( expected64[ 6 ], arrHilb64Level6, delta );
      double[ ] arrTime64FromLevel6 = wpt.reverse( arrHilb64Level6, 6 );
      assertArray( arrTime64, arrTime64FromLevel6, delta );

    } // WPT over all Wavelets

  } // testForwardSteppingDoubleArray

  /**
   * Test method for {@link jwave.Transform#decompose(double[])}.
   */
  @Test public void testDecompose( ) {

    // run this part for any wavelet that is available in JWave!

    double delta = 1.e-8;

    Wavelet[ ] arrOfWaveletObjects = WaveletBuilder.create2arr( ); // over 50 wavelets :-p
    int noOfWavelets = arrOfWaveletObjects.length;

    for( int w = 0; w < noOfWavelets; w++ ) {

      Wavelet wavelet = arrOfWaveletObjects[ w ];

      System.out.println( "" );
      System.out.println( "Testing the Fast Wavelet Transform "
          + "decompose, recompose, and recomposeFromLevel 1-D methods "
          + "using " + wavelet.getName( ) + " and small array" );

      double[ ] arrTime = { 1., 1., 1., 1. };

      Transform t = new Transform( new FastWaveletTransform( wavelet ) );

      double[ ][ ] matDeComp = t.decompose( arrTime );

      double sqrt2 = Math.sqrt( 2. );

      double[ ][ ] expected =
          { { 1., 1., 1., 1., }, { sqrt2, sqrt2, 0., 0. }, { 2., 0., 0., 0. } }; // orthonormal Hilbert space

      assertMatrix( expected, matDeComp, delta );

      double[ ] arrTimeReComp = t.recompose( matDeComp );

      assertArray( arrTime, arrTimeReComp, delta );

      // test reconstruction from a certain level of decomposition
      double[ ] arrTimeReCompLevel0 = t.recompose( matDeComp, 0 );
      assertArray( arrTime, arrTimeReCompLevel0, delta );

      double[ ] arrTimeReCompLevel1 = t.recompose( matDeComp, 0 );
      assertArray( arrTime, arrTimeReCompLevel1, delta );

      double[ ] arrTimeReCompLevel2 = t.recompose( matDeComp, 0 );
      assertArray( arrTime, arrTimeReCompLevel2, delta );

      System.out.println( "Testing the Fast Wavelet Transform "
          + "decompose, recompose, and recomposeFromLevel 1-D methods "
          + "using " + wavelet.getName( ) + " and long array" );

      double[ ] arrTime64 = { // array of length 64
          1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1.,
              1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1.,
              1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1.,
              1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1. };

      double[ ][ ] matDeComp64 = t.decompose( arrTime64 );

      double d1sqrt2 = 1 * sqrt2; // symbolic one times square root of 2
      double d2sqrt2 = 2. * d1sqrt2; // 2 times square root of two
      double d4sqrt2 = 2. * d2sqrt2; // 4 times square root of two

      double[ ][ ] expected64 =
          {
              { // array of length 64
              1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1.,
                  1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1.,
                  1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1.,
                  1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1.,
                  1., 1., 1. },

              { d1sqrt2, d1sqrt2, d1sqrt2, d1sqrt2, d1sqrt2, d1sqrt2, d1sqrt2,
                  d1sqrt2, d1sqrt2, d1sqrt2, d1sqrt2, d1sqrt2, d1sqrt2,
                  d1sqrt2, d1sqrt2, d1sqrt2, d1sqrt2, d1sqrt2, d1sqrt2,
                  d1sqrt2, d1sqrt2, d1sqrt2, d1sqrt2, d1sqrt2, d1sqrt2,
                  d1sqrt2, d1sqrt2, d1sqrt2, d1sqrt2, d1sqrt2, d1sqrt2,
                  d1sqrt2, 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.,
                  0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.,
                  0., 0., 0., 0. },

              { 2., 2., 2., 2., 2., 2., 2., 2., 2., 2., 2., 2., 2., 2., 2., 2.,
                  0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.,
                  0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.,
                  0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.,
                  0., 0., 0. },

              { d2sqrt2, d2sqrt2, d2sqrt2, d2sqrt2, d2sqrt2, d2sqrt2, d2sqrt2,
                  d2sqrt2, 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.,
                  0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.,
                  0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.,
                  0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0. },

              { 4., 4., 4., 4., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.,
                  0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.,
                  0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.,
                  0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.,
                  0., 0., 0. },

              { d4sqrt2, d4sqrt2, 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.,
                  0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.,
                  0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.,
                  0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.,
                  0., 0., 0., 0., 0., 0. },

              { 8., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.,
                  0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.,
                  0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.,
                  0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.,
                  0., 0., 0. },

          }; // orthonormal Hilbert space

      assertMatrix( expected64, matDeComp64, delta );

      // decompose from highest level: 64 = 2^p | p = 6
      double[ ] arrTimeReComp64 = t.recompose( matDeComp64 );
      assertArray( arrTime64, arrTimeReComp64, delta );

      // test reconstruction from a certain level of decomposition
      double[ ] arrTimeReComp64Level0 = t.recompose( matDeComp64, 0 );
      assertArray( arrTime64, arrTimeReComp64Level0, delta );

      double[ ] arrTimeReComp64Level1 = t.recompose( matDeComp64, 1 );
      assertArray( arrTime64, arrTimeReComp64Level1, delta );

      double[ ] arrTimeReComp64Level2 = t.recompose( matDeComp64, 2 );
      assertArray( arrTime64, arrTimeReComp64Level2, delta );

      double[ ] arrTimeReComp64Level3 = t.recompose( matDeComp64, 3 );
      assertArray( arrTime64, arrTimeReComp64Level3, delta );

      double[ ] arrTimeReComp64Level4 = t.recompose( matDeComp64, 4 );
      assertArray( arrTime64, arrTimeReComp64Level4, delta );

      double[ ] arrTimeReComp64Level5 = t.recompose( matDeComp64, 5 );
      assertArray( arrTime64, arrTimeReComp64Level5, delta );

      double[ ] arrTimeReComp64Level6 = t.recompose( matDeComp64, 6 );
      assertArray( arrTime64, arrTimeReComp64Level6, delta );

    } // w

  } // testDecomposeDoubleArray

  /**
   * Test method for {@link jwave.Transform#forward(Complex[])}.
   */
  @Test public void testComplex( ) {

    System.out.println( "" );
    System.out.println( "Testing the Fast Wavelet Transform "
        + "forward 1-D method " + "using Haar1 Wavelet" );

    double delta = 1.e-12;

    int arrTimeLength = 8;

    Complex[ ] arrTime = new Complex[ arrTimeLength ];

    for( int i = 0; i < arrTimeLength; i++ )
      arrTime[ i ] = new Complex( 1., 1. );

    // showTime( arrTime );

    Transform t = new Transform( new FastWaveletTransform( new Haar1( ) ) );
    // Transform t = new Transform( new FastWaveletTransform( new Haar1Orthogonal( ) ) );
    // Transform t = new Transform( new FastWaveletTransform( new Daubechies20( ) ) );

    Complex[ ] arrHilb = t.forward( arrTime );

    // showHilb( arrHilb );

    Complex[ ] expected = new Complex[ arrTimeLength ];

    for( int i = 0; i < arrTimeLength; i++ )
      expected[ i ] = new Complex( 0., 0. ); // { 0., 0., 0., .. }

    expected[ 0 ].setReal( 4. );  // { 4., 0., 0., .. }

    assertArray( expected, arrHilb, delta );

    System.out.println( "" );
    System.out.println( "Testing the Fast Wavelet Transform "
        + "reverse 1-D method " + "using Haar1 Wavelet" );

    arrHilb = new Complex[ arrTimeLength ];

    for( int i = 0; i < arrTimeLength; i++ )
      arrHilb[ i ] = new Complex( 0., 0. ); // { 0., 0., 0., .. }

    arrHilb[ 0 ].setReal( 4. );  // { 4., 0., 0., .. }

    // showHilb( arrHilb );

    arrTime = t.reverse( arrHilb );

    // showTime( arrTime );

    expected = new Complex[ arrTimeLength ];

    for( int i = 0; i < arrTimeLength; i++ )
      expected[ i ] = new Complex( 1., 1. );

    assertArray( expected, arrTime, delta );

    System.out.println( "" );
    System.out.println( "Testing the Fast Wavelet Transform "
        + "reverse 1-D method " + "using Haar1 Wavelet" );

  } // testComplex

  /**
   * Test method for {@link jwave.Transform#forward(double[][])}.
   */
  @Test public void testMatrix( ) {

    System.out.println( "" );
    System.out.println( "Testing the Fast Wavelet Transform "
        + "forward 2-D method " + "using Haar1 Wavelet" );

    double delta = 1.e-12;

    double[ ][ ] matrixTime =
        { { 1., 1., 1., 1. }, { 1., 1., 1., 1. }, { 1., 1., 1., 1. },
            { 1., 1., 1., 1. } };

    // showTime( matrixTime );

    Transform t = new Transform( new FastWaveletTransform( new Haar1( ) ) );
    double[ ][ ] matrixHilb = t.forward( matrixTime );

    // showHilb( matrixHilb );

    double[ ][ ] expected =
        { { 4., 0., 0., 0. }, { 0., 0., 0., 0. }, { 0., 0., 0., 0. },
            { 0., 0., 0., 0. } };
    assertMatrix( expected, matrixHilb, delta );

    System.out.println( "" );
    System.out.println( "Testing the Fast Wavelet Transform "
        + "reverse 2-D method " + "using Haar1 Wavelet" );

    double[ ][ ] matrixReco = t.reverse( matrixHilb );

    // showTime( matrixReco );

    double[ ][ ] expectedReco =
        { { 1., 1., 1., 1. }, { 1., 1., 1., 1. }, { 1., 1., 1., 1. },
            { 1., 1., 1., 1. } };
    assertMatrix( expectedReco, matrixReco, delta );

  } // testMatrix

  /**
   * Test method for {@link jwave.Transform#forward(double[][][])}.
   */
  @Test public void testSpace( ) {

    System.out.println( "" );
    System.out.println( "Testing the Fast Wavelet Transform "
        + "forward 3-D method " + "using Haar1 Wavelet" );

    double delta = 1.e-12;

    double[ ][ ][ ] spaceTime =
        {
            { { 1., 1., 1., 1. }, { 1., 1., 1., 1. }, { 1., 1., 1., 1. },
                { 1., 1., 1., 1. } },
            { { 1., 1., 1., 1. }, { 1., 1., 1., 1. }, { 1., 1., 1., 1. },
                { 1., 1., 1., 1. } },
            { { 1., 1., 1., 1. }, { 1., 1., 1., 1. }, { 1., 1., 1., 1. },
                { 1., 1., 1., 1. } },
            { { 1., 1., 1., 1. }, { 1., 1., 1., 1. }, { 1., 1., 1., 1. },
                { 1., 1., 1., 1. } } };

    // showTime( spaceTime );

    Transform t = new Transform( new FastWaveletTransform( new Haar1( ) ) );
    double[ ][ ][ ] spaceHilb = t.forward( spaceTime );

    // showHilb( spaceHilb );

    double[ ][ ][ ] expected =
        {
            { { 8., 0., 0., 0. }, { 0., 0., 0., 0. }, { 0., 0., 0., 0. },
                { 0., 0., 0., 0. } },
            { { 0., 0., 0., 0. }, { 0., 0., 0., 0. }, { 0., 0., 0., 0. },
                { 0., 0., 0., 0. } },
            { { 0., 0., 0., 0. }, { 0., 0., 0., 0. }, { 0., 0., 0., 0. },
                { 0., 0., 0., 0. } },
            { { 0., 0., 0., 0. }, { 0., 0., 0., 0. }, { 0., 0., 0., 0. },
                { 0., 0., 0., 0. } } };
    assertSpace( expected, spaceHilb, delta );

    System.out.println( "" );
    System.out.println( "Testing the Fast Wavelet Transform "
        + "reverse 3-D method " + "using Haar1 Wavelet" );

    double[ ][ ][ ] spaceReco = t.reverse( spaceHilb );

    // showTime( spaceReco );

    double[ ][ ][ ] expectedReco =
        {
            { { 1., 1., 1., 1. }, { 1., 1., 1., 1. }, { 1., 1., 1., 1. },
                { 1., 1., 1., 1. } },
            { { 1., 1., 1., 1. }, { 1., 1., 1., 1. }, { 1., 1., 1., 1. },
                { 1., 1., 1., 1. } },
            { { 1., 1., 1., 1. }, { 1., 1., 1., 1. }, { 1., 1., 1., 1. },
                { 1., 1., 1., 1. } },
            { { 1., 1., 1., 1. }, { 1., 1., 1., 1. }, { 1., 1., 1., 1. },
                { 1., 1., 1., 1. } } };

    assertSpace( expectedReco, spaceReco, delta );

  } // testSpace

  /**
   * Test the Compressor classes by giving an example for data compression with
   * wavelets.
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 14.05.2015 18:26:03
   */
  @Test public void testCompression( ) {

    Compressor compressor = new CompressorMagnitude( 1.0 );

    Wavelet[ ] arrOfWaveletObjects = WaveletBuilder.create2arr( ); // over 50 wavelets :-p
    int noOfWavelets = arrOfWaveletObjects.length;

    // go for Fast Wavelet Transforms
    for( int w = 0; w < noOfWavelets; w++ ) {

      Wavelet wavelet = arrOfWaveletObjects[ w ];

      System.out
          .println( "Testing example with FWT using " + wavelet.getName( ) );

      Transform fwt = new Transform( new FastWaveletTransform( wavelet ) );

      double[ ] arrTime = { 1.2, 2.3, 3.4, 4.5, 5.4, 4.3, 3.2, 2.1, 1.2, -0.3, -1.4, -2.5, -1.6, -0.7, 0.6, 1.5 };

      double[ ] arrHilb = fwt.forward( arrTime );
      
      double[ ] arrComp = compressor.compress( arrHilb );

      double[ ] arrReco = fwt.reverse( arrComp );

      showTime( arrTime );
      showHilb( arrHilb );
      showHilb( arrComp );
      showTime( arrReco );

    } // w 
    
  } // testCompression

  //
  // Following methods are internal helpers for the JUnit methods
  //

  /**
   * Test method to check the rounding error of several forward and reverse
   * transforms using the Fast Wavelet Transform algorithm and any given Wavelet
   * object as input.
   * 
   * @date 10.02.2010 10:28:00
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   */
  public void testFastWaveletTransformRounding( double[ ] arr, Wavelet wavelet,
      double delta ) {

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
  public void testWaveletPacketTransformRounding( double[ ] arr,
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

  protected void show( double value ) {
    System.out.printf( "%6.3f", value );
  } // show

  protected void showTime( double[ ] arrTime ) {
    System.out.print( "time domain: " + "\t" + "\t" );
    for( int c = 0; c < arrTime.length; c++ ) {
      show( arrTime[ c ] );
      System.out.print( " " );
    }
    System.out.println( "" );
  } // showTime

  protected void showFreq( double[ ] arrFreq ) {
    System.out.print( "frequency domain: " + "\t" );
    for( int c = 0; c < arrFreq.length; c++ ) {
      show( arrFreq[ c ] );
      System.out.print( " " );
    }
    System.out.println( "" );
  } // showHilb

  protected void showHilb( double[ ] arrHilb ) {
    System.out.print( "Hilbert domain: " + "\t" );
    for( int c = 0; c < arrHilb.length; c++ ) {
      show( arrHilb[ c ] );
      System.out.print( " " );
    }
    System.out.println( "" );
  } // showHilb

  protected void showTime( Complex[ ] arrTime ) {
    System.out.print( "time domain: " + "\t" + "\t" );
    for( int c = 0; c < arrTime.length; c++ )
      System.out.print( arrTime[ c ].toString( ) + " " );
    System.out.println( "" );
  } // showTime

  protected void showHilb( Complex[ ] arrFreq ) {
    System.out.print( "frequency domain: " + "\t" );
    for( int c = 0; c < arrFreq.length; c++ )
      System.out.print( arrFreq[ c ].toString( ) + " " );
    System.out.println( "" );
  } // showHilb

  protected void showTime( double[ ][ ] matrixTime ) {
    System.out.println( "time domain: " + "\t" );
    for( int i = 0; i < matrixTime.length; i++ ) {
      for( int j = 0; j < matrixTime[ i ].length; j++ ) {
        show( matrixTime[ i ][ j ] );
        System.out.print( " " );
      }
      System.out.println( "" );
    }
    System.out.println( "" );
  } // showTime

  protected void showFreq( double[ ][ ] matrixFreq ) {
    System.out.println( "frequency domain: " + "\t" );
    for( int i = 0; i < matrixFreq.length; i++ ) {
      for( int j = 0; j < matrixFreq[ i ].length; j++ ) {
        show( matrixFreq[ i ][ j ] );
        System.out.print( " " );
      }
      System.out.println( "" );
    }
    System.out.println( "" );
  } // showFreq

  protected void showHilb( double[ ][ ] matrixHilb ) {
    System.out.println( "Hilbert domain: " + "\t" );
    for( int i = 0; i < matrixHilb.length; i++ ) {
      for( int j = 0; j < matrixHilb[ i ].length; j++ ) {
        show( matrixHilb[ i ][ j ] );
        System.out.print( " " );
      }
      System.out.println( "" );
    }
    System.out.println( "" );
  } // showHilb

  protected void showTime( double[ ][ ][ ] spaceTime ) {
    System.out.println( "time domain: " + "\t" );
    for( int i = 0; i < spaceTime.length; i++ ) {
      for( int j = 0; j < spaceTime[ i ].length; j++ ) {
        for( int k = 0; k < spaceTime[ i ][ j ].length; k++ ) {
          show( spaceTime[ i ][ j ][ k ] );
          System.out.print( " " );
        }
        System.out.println( "" );
      }
      System.out.println( "" );
    }
    System.out.println( "" );
  } // showTime

  protected void showFreq( double[ ][ ][ ] spaceFreq ) {
    System.out.println( "frequency domain: " + "\t" );
    for( int i = 0; i < spaceFreq.length; i++ ) {
      for( int j = 0; j < spaceFreq[ i ].length; j++ ) {
        for( int k = 0; k < spaceFreq[ i ][ j ].length; k++ ) {
          show( spaceFreq[ i ][ j ][ k ] );
          System.out.print( " " );
        }
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
        for( int k = 0; k < spaceTime[ i ][ j ].length; k++ ) {
          show( spaceTime[ i ][ j ][ k ] );
          System.out.print( " " );
        }
        System.out.println( "" );
      }
      System.out.println( "" );
    }
    System.out.println( "" );
  } // showHilb

} // TransformTest
