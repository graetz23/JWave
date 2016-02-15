/**
 * Shifting Wavelet Transform that start with the smallest
 * wavelength of the wavelet.
 *
 * @author Christian Scheiblich (cscheiblich@gmail.com)
 * @date 15.02.2016 23:12:55 
 *
 * ShiftingWaveletTransform.java
 */
package jwave.transforms;

import jwave.exceptions.JWaveException;
import jwave.exceptions.JWaveFailureNotImplemented;
import jwave.transforms.wavelets.Wavelet;

/**
 * Shifting Wavelet Transform that start with the smallest wavelength of the
 * wavelet.
 * 
 * @author Christian Scheiblich (cscheiblich@gmail.com)
 * @date 15.02.2016 23:12:55
 */
public class ShiftingWaveletTransform extends WaveletTransform {

  /**
   * TODO Comment me please!
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 15.02.2016 23:12:55
   */
  public ShiftingWaveletTransform( Wavelet wavelet ) {
    super( wavelet );
  } // ShiftingWaveletTransform

  /*
   * Forward method that uses strictly the abilities of an orthogonal transform.
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 15.02.2016 23:12:55 (non-Javadoc)
   * @see jwave.transforms.BasicTransform#forward(double[])
   */
  @Override public double[ ] forward( double[ ] arrTime ) throws JWaveException {
    // TODO start with smallest wavelength of 2 and shift along input array, then with 4, then with p-1, then with p.
    throw new JWaveFailureNotImplemented( "yet" );
  } // forward

  /*
   * Reverse method that uses strictly the abilities of an orthogonal transform.
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 15.02.2016 23:12:55 (non-Javadoc)
   * @see jwave.transforms.BasicTransform#reverse(double[])
   */
  @Override public double[ ] reverse( double[ ] arrFreq ) throws JWaveException {
    // TODO start with largest possible wavelength of p and shift along input array, then with p-1, .., then with 2.
    throw new JWaveFailureNotImplemented( "yet" );
  } // reverse

} // class
