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

    int div = 2;
    int odd = length % div; // if odd == 1 => steps * 2 + odd else steps * 2

    double[ ] arrHilb = new double[ length ];

    while( div < length ) {

      int splits = length / div; // cuts the digits == round down to full

      // doing smallest wavelength of div by no of steps

      for( int s = 0; s < splits; s++ ) {

        double[ ] arrTimeTmp = new double[ div ];
        double[ ] arrHilbTmp = null;

        for( int p = 0; p < div; p++ )
          arrTimeTmp[ p ] = arrTime[ s * div + p ];

        arrHilbTmp = _wavelet.forward( arrTimeTmp, div );

        for( int q = 0; q < div; q++ )
          arrHilb[ s * 2 + q ] = arrHilbTmp[ q ];

      } // s

      div *= 2;

    } // while

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
    int transformWavelength = _wavelet.getTransformWavelength( ); // normally 2
    int h = transformWavelength;

    int level = calcExponent( length );
    int steps = calcExponent( length );
    for( int l = level; l < steps; l++ )
      h = h << 1; // begin reverse transform at certain - matching - level of Hilbert space

    int div = 2;
    int odd = length % div; // if odd == 1 => steps * 2 + odd else steps * 2

    double[ ] arrTime = new double[ length ];

    while( div < length ) {

      int splits = length / div; // cuts the digits == round down to full

      // doing smallest wavelength of div by no of steps

      for( int s = 0; s < splits; s++ ) {

        double[ ] arrHilbTmp = new double[ div ];
        double[ ] arrTimeTmp = null;

        for( int p = 0; p < div; p++ )
          arrHilbTmp[ p ] = arrHilb[ s * div + p ];

        arrTimeTmp = _wavelet.reverse( arrHilbTmp, div );

        for( int q = 0; q < div; q++ )
          arrTime[ s * 2 + q ] = arrTimeTmp[ q ];

      } // s

      div *= 2;

    } // while

    if( odd == 1 )
      arrTime[ length - 1 ] = arrHilb[ length - 1 ];

    return arrTime;

  } // reverse

} // class
