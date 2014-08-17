/**
 * TODO Comment me please!
 *
 * @author Christian Scheiblich (cscheiblich@gmail.com)
 * @date 17.08.2014 14:17:36 
 *
 * Symlet5.java
 */
package math.jwave.transforms.wavelets.symlets;

import math.jwave.transforms.wavelets.Wavelet;

/**
 * Symlet5 filter: near symmetric, orthogonal (orthonormal), biorthogonal.
 * 
 * @author Christian Scheiblich (cscheiblich@gmail.com)
 * @date 17.08.2014 14:17:36
 */
public class Symlet5 extends Wavelet {

  /**
   * Already orthonormal coefficients taken from Filip Wasilewski's webpage
   * http://wavelets.pybytes.com/wavelet/sym5/ Thanks!
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 17.08.2014 14:17:36
   */
  public Symlet5( ) {

    _name = "Symlet 4"; // name of the wavelet

    _transformWavelength = 2; // minimal wavelength of input signal

    _motherWavelength = 10; // wavelength of mother wavelet

    _scalingDeCom = new double[ _motherWavelength ];
    _scalingDeCom[ 0 ] = 0.027333068345077982;
    _scalingDeCom[ 1 ] = 0.029519490925774643;
    _scalingDeCom[ 2 ] = -0.039134249302383094;
    _scalingDeCom[ 3 ] = 0.1993975339773936;
    _scalingDeCom[ 4 ] = 0.7234076904024206;
    _scalingDeCom[ 5 ] = 0.6339789634582119;
    _scalingDeCom[ 6 ] = 0.01660210576452232;
    _scalingDeCom[ 7 ] = -0.17532808990845047;
    _scalingDeCom[ 8 ] = -0.021101834024758855;
    _scalingDeCom[ 9 ] = 0.019538882735286728;

    _buildOrthonormalSpace( );

  } // Symlet5

} // Symlet5
