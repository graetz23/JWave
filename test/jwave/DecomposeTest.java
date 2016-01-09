/**
 * Testing decompose method for creating from 1-D input a 2-D output keeping all
 * possible stable levels of decomposition.
 *
 * @author Christian Scheiblich (cscheiblich@gmail.com)
 * @date 09.01.2016 07:59:56 
 *
 * DecomposeTest.java
 */
package jwave;

import jwave.transforms.FastWaveletTransform;
import jwave.transforms.wavelets.Wavelet;
import jwave.transforms.wavelets.WaveletBuilder;

import org.junit.Test;

/**
 * Testing decompose method for creating from 1-D input a 2-D output keeping all
 * possible stable levels of decomposition.
 * 
 * @author Christian Scheiblich (cscheiblich@gmail.com)
 * @date 09.01.2016 07:59:56
 */
public class DecomposeTest extends Base {

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

  } // testDecompose

} // class
