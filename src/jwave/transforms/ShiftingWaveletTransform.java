/**
 * Shifting Wavelet Transform shifts a wavelet of smallest wavelength over the
 * input array, then by the double wavelength, .., and so on.
 *
 * @author Christian Scheiblich (cscheiblich@gmail.com)
 * @date 15.02.2016 23:12:55 
 *
 * ShiftingWaveletTransform.java
 */
package jwave.transforms;

import jwave.exceptions.JWaveException;
import jwave.exceptions.JWaveFailureNotImplemented;
import jwave.tools.MathToolKit;
import jwave.transforms.wavelets.Wavelet;

/**
 * Shifting Wavelet Transform shifts a wavelet of smallest wavelength over the
 * input array, then by the double wavelength, .., and so on.
 * 
 * @author Christian Scheiblich (cscheiblich@gmail.com)
 * @date 15.02.2016 23:12:55
 */
public class ShiftingWaveletTransform extends WaveletTransform {

  /**
   * Constructor taking Wavelet object for performing the transform.
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 15.02.2016 23:12:55
   */
  public ShiftingWaveletTransform( Wavelet wavelet ) {
    super( wavelet );
  } // ShiftingWaveletTransform

  /**
   * Forward method that uses strictly the abilities of an orthogonal transform.
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 15.02.2016 23:12:55 (non-Javadoc)
   * @see jwave.transforms.BasicTransform#forward(double[])
   */
  @Override public double[ ] forward( double[ ] arrTime ) throws JWaveException {

    // TODO start with smallest wavelength of 2 and shift along input array, then with 4, then with p-1, then with p.
    int length = arrTime.length;

    // first try a one step implementation
    int steps = length / 2;
    int odd = length % 2; // if odd == 1 => steps * 2 + odd else steps * 2

    double[ ] arrHilb = new double[ length ];

    // doing smallest wavelength of 2 by no of steps
    double[ ] arrTime2 = new double[ 2 ];
    double[ ] arrHilb2 = null;

    for( int s = 0; s < steps; s++ ) {

      arrTime2[ 0 ] = arrTime[ s * 2 ];
      arrTime2[ 1 ] = arrTime[ s * 2 + 1 ];

      arrHilb2 = _wavelet.forward( arrTime2, 2 );

      arrHilb[ s * 2 ] = arrHilb2[ 0 ];
      arrHilb[ s * 2 + 1 ] = arrHilb2[ 1 ];

    } // s
    
    
    //    for( int currentLength = 1; currentLength <= 1; currentLength++ ) {
    //
    //      // doing smallest wavelength of 2 by no of steps
    //      double[ ] arrTime2 = new double[ currentLength * 2 ]; // 2, 4, 8
    //      double[ ] arrHilb2 = null;
    //
    //      for( int s = 0; s < steps; s++ ) {
    //
    //        for( int t = 0; t < currentLength * 2; t++ )
    //          arrTime2[ t ] = arrTime[ s * currentLength + t ];
    //
    //        arrHilb2 = _wavelet.forward( arrTime2, 2 );
    //
    //        for( int t = 0; t < currentLength * 2; t++ )
    //          arrHilb[ s * currentLength + t ] = arrHilb2[ t ];
    //
    //      } // s
    //
    //    } // l

    if( odd == 1 )
      arrHilb[ length - 1 ] = arrTime[ length - 1 ];

    return arrHilb;

  } // forward

  /**
   * Reverse method that uses strictly the abilities of an orthogonal transform.
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 15.02.2016 23:12:55 (non-Javadoc)
   * @see jwave.transforms.BasicTransform#reverse(double[])
   */
  @Override public double[ ] reverse( double[ ] arrHilb ) throws JWaveException {
    // TODO start with largest possible wavelength of p and shift along input array, then with p-1, .., then with 2.
    int length = arrHilb.length;

    // first try a one step implementation
    int steps = length / 2;
    int odd = length % 2; // if odd == 1 => steps * 2 + odd else steps * 2

    double[ ] arrTime = new double[ length ];

    // doing smallest wavelength of 2 by no of steps
    double[ ] arrHilb2 = new double[ 2 ];
    double[ ] arrTime2 = null;

    for( int s = 0; s < steps; s++ ) {

      arrHilb2[ 0 ] = arrHilb[ s * 2 ];
      arrHilb2[ 1 ] = arrHilb[ s * 2 + 1 ];

      arrTime2 = _wavelet.forward( arrHilb2, 2 );

      arrTime[ s * 2 ] = arrTime2[ 0 ];
      arrTime[ s * 2 + 1 ] = arrTime2[ 1 ];

    } // s
    
    
    if( odd == 1 )
      arrTime[ length - 1 ] = arrHilb[ length - 1 ];

    return arrTime;

  } // reverse

} // class
