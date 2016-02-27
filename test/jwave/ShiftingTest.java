/**
 * TODO Comment me please!
 *
 * @author Christian Scheiblich (cscheiblich@gmail.com)
 * @date 16.02.2016 22:33:53 
 *
 * ShiftingTest.java
 */
package jwave;

import static org.junit.Assert.*;
import jwave.transforms.ShiftingWaveletTransform;
import jwave.transforms.wavelets.haar.Haar1;

import org.junit.Test;

/**
 * TODO Comment me please!
 * 
 * @author Christian Scheiblich (cscheiblich@gmail.com)
 * @date 16.02.2016 22:33:53
 */
public class ShiftingTest extends Base {

  /**
   * Test method for
   * {@link jwave.transforms.ShiftingWaveletTransform#forward(double[])}.
   */
  @Test public void testForwardDoubleArray( ) {

    Transform shiftingTransform =
        new Transform( new ShiftingWaveletTransform( new Haar1( ) ) );

    double[ ] arrTimeEven = { 1., 2., 3., 4., 5., 6., 7., 8. };

    double[ ] arrHilbEven = shiftingTransform.forward( arrTimeEven );

    showHilb( arrHilbEven );

    double[ ] arrTimeOdd = { 1., 2., 3., 4., 5., 6., 7., 8., 9. };

    double[ ] arrHilbOdd = shiftingTransform.forward( arrTimeOdd );

    showHilb( arrHilbOdd );

  }

  /**
   * Test method for
   * {@link jwave.transforms.ShiftingWaveletTransform#reverse(double[])}.
   */
  @Test public void testReverseDoubleArray( ) {

    Transform shiftingTransform =
        new Transform( new ShiftingWaveletTransform( new Haar1( ) ) );

    double[ ] arrHilbOdd =
        { 2.828, 4.243, 8.485, 9.899, -1.414, -1.414, -1.414, -1.414, 9.000 };

    double[ ] arrTimeOdd = shiftingTransform.reverse( arrHilbOdd );

    showTime( arrTimeOdd );

    double[ ] arrHilbEven =
        { 2.828, 4.243, 8.485, 9.899, -1.414, -1.414, -1.414, -1.414 };

    double[ ] arrTimeEven = shiftingTransform.reverse( arrHilbEven );

    showTime( arrTimeEven );

  }

}
