/**
 * Testing the stepping methods which allow to stop at a certain level of
 * decomposition.
 *
 * @author Christian Scheiblich (cscheiblich@gmail.com)
 * @date 09.01.2016 08:06:20 
 *
 * SteppingTest.java
 */
package jwave;

import jwave.transforms.FastWaveletTransform;
import jwave.transforms.WaveletPacketTransform;
import jwave.transforms.wavelets.Wavelet;
import jwave.transforms.wavelets.WaveletBuilder;

import org.junit.Test;

/**
 * Testing the stepping methods which allow to stop at a certain level of
 * decomposition.
 * 
 * @author Christian Scheiblich (cscheiblich@gmail.com)
 * @date 09.01.2016 08:06:20
 */
public class SteppingTest extends Base {

  /**
   * Testing the stepping methods which allow to stop at a certain level of
   * decomposition.
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

  } // testStepping

} // class
