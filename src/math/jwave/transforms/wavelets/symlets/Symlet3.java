/**
 * TODO Comment me please!
 *
 * @author Christian Scheiblich (cscheiblich@gmail.com)
 * @date 17.08.2014 14:06:57 
 *
 * Symlet3.java
 */
package math.jwave.transforms.wavelets.symlets;

import math.jwave.transforms.wavelets.Wavelet;

/**
 * Symlet3 filter: near symmetric, orthogonal (orthonormal), biorthogonal.
 * 
 * @author Christian Scheiblich (cscheiblich@gmail.com)
 * @date 17.08.2014 14:06:57
 */
public class Symlet3 extends Wavelet {

  /**
   * Already orthonormal coefficients taken from Filip Wasilewski's webpage
   * http://wavelets.pybytes.com/wavelet/sym3/ Thanks!
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 17.08.2014 14:06:57
   */
  public Symlet3( ) {

    _name = "Symlet 3"; // name of the wavelet

    _transformWavelength = 2; // minimal wavelength of input signal

    _motherWavelength = 6; // wavelength of mother wavelet

    _scalingDeCom = new double[ _motherWavelength ];
    _scalingDeCom[ 0 ] = 0.035226291882100656;
    _scalingDeCom[ 1 ] = -0.08544127388224149;
    _scalingDeCom[ 2 ] = -0.13501102001039084;
    _scalingDeCom[ 3 ] = 0.4598775021193313;
    _scalingDeCom[ 4 ] = 0.8068915093133388;
    _scalingDeCom[ 5 ] = 0.3326705529509569;

    _buildOrthonormalSpace( );

  } // Symlet3

} // Symlet3
