/**
 *
 * @author tucker
 * 16.02.2014 11:42:09
 *
 * BiOrthogonal_6_8.java
 */
package math.jwave.transforms.wavelets;

/**
 * BiOrthogonal Wavelet of type 6.8 - Six vanishing moments in wavelet function
 * and eight vanishing moments in scaling function.
 * 
 * @author Christian Scheiblich (cscheiblich@gmail.com)
 * @date 16.02.2014 11:42:09
 */
public class BiOrthogonal_6_8 extends Wavelet {

  /**
   * Already orthonormal coefficients taken from Filip Wasilewski's webpage
   * http://wavelets.pybytes.com/wavelet/bior6.8/ Thanks!
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 16.02.2014 11:42:09
   */
  public BiOrthogonal_6_8( ) {

    _transformWavelength = 2; // minimal wavelength of input signal

    _motherWavelength = 18; // wavelength of mother wavelet

    _scalingDeCom = new double[ _motherWavelength ];
    _scalingDeCom[ 0 ] = 0.;
    _scalingDeCom[ 1 ] = 0.0019088317364812906;
    _scalingDeCom[ 2 ] = -0.0019142861290887667;
    _scalingDeCom[ 3 ] = -0.016990639867602342;
    _scalingDeCom[ 4 ] = 0.01193456527972926;
    _scalingDeCom[ 5 ] = 0.04973290349094079;
    _scalingDeCom[ 6 ] = -0.07726317316720414;
    _scalingDeCom[ 7 ] = -0.09405920349573646;
    _scalingDeCom[ 8 ] = 0.4207962846098268;
    _scalingDeCom[ 9 ] = 0.8259229974584023;
    _scalingDeCom[ 10 ] = 0.4207962846098268;
    _scalingDeCom[ 11 ] = -0.09405920349573646;
    _scalingDeCom[ 12 ] = -0.07726317316720414;
    _scalingDeCom[ 13 ] = 0.04973290349094079;
    _scalingDeCom[ 14 ] = 0.01193456527972926;
    _scalingDeCom[ 15 ] = -0.016990639867602342;
    _scalingDeCom[ 16 ] = -0.0019142861290887667;
    _scalingDeCom[ 17 ] = 0.0019088317364812906;

    _waveletDeCom = new double[ _motherWavelength ];
    _waveletDeCom[ 0 ] = 0.;
    _waveletDeCom[ 1 ] = 0.;
    _waveletDeCom[ 2 ] = 0.;
    _waveletDeCom[ 3 ] = 0.014426282505624435;
    _waveletDeCom[ 4 ] = -0.014467504896790148;
    _waveletDeCom[ 5 ] = -0.07872200106262882;
    _waveletDeCom[ 6 ] = 0.04036797903033992;
    _waveletDeCom[ 7 ] = 0.41784910915027457;
    _waveletDeCom[ 8 ] = -0.7589077294536541;
    _waveletDeCom[ 9 ] = 0.41784910915027457;
    _waveletDeCom[ 10 ] = 0.04036797903033992;
    _waveletDeCom[ 11 ] = -0.07872200106262882;
    _waveletDeCom[ 12 ] = -0.014467504896790148;
    _waveletDeCom[ 13 ] = 0.014426282505624435;
    _waveletDeCom[ 14 ] = 0.;
    _waveletDeCom[ 15 ] = 0.;
    _waveletDeCom[ 16 ] = 0.;
    _waveletDeCom[ 17 ] = 0.;

    _scalingReCon = new double[ _motherWavelength ];
    _scalingReCon[ 0 ] = 0.;
    _scalingReCon[ 1 ] = 0.;
    _scalingReCon[ 2 ] = 0.;
    _scalingReCon[ 3 ] = 0.014426282505624435;
    _scalingReCon[ 4 ] = 0.014467504896790148;
    _scalingReCon[ 5 ] = -0.07872200106262882;
    _scalingReCon[ 6 ] = -0.04036797903033992;
    _scalingReCon[ 7 ] = 0.41784910915027457;
    _scalingReCon[ 8 ] = 0.7589077294536541;
    _scalingReCon[ 9 ] = 0.41784910915027457;
    _scalingReCon[ 10 ] = -0.04036797903033992;
    _scalingReCon[ 11 ] = -0.07872200106262882;
    _scalingReCon[ 12 ] = 0.014467504896790148;
    _scalingReCon[ 13 ] = 0.014426282505624435;
    _scalingReCon[ 14 ] = 0.;
    _scalingReCon[ 15 ] = 0.;
    _scalingReCon[ 16 ] = 0.;
    _scalingReCon[ 17 ] = 0.;

    _waveletReCon = new double[ _motherWavelength ];
    _waveletReCon[ 0 ] = 0.;
    _waveletReCon[ 1 ] = -0.0019088317364812906;
    _waveletReCon[ 2 ] = -0.0019142861290887667;
    _waveletReCon[ 3 ] = 0.016990639867602342;
    _waveletReCon[ 4 ] = 0.01193456527972926;
    _waveletReCon[ 5 ] = -0.04973290349094079;
    _waveletReCon[ 6 ] = -0.07726317316720414;
    _waveletReCon[ 7 ] = 0.09405920349573646;
    _waveletReCon[ 8 ] = 0.4207962846098268;
    _waveletReCon[ 9 ] = -0.8259229974584023;
    _waveletReCon[ 10 ] = 0.4207962846098268;
    _waveletReCon[ 11 ] = 0.09405920349573646;
    _waveletReCon[ 12 ] = -0.07726317316720414;
    _waveletReCon[ 13 ] = -0.04973290349094079;
    _waveletReCon[ 14 ] = 0.01193456527972926;
    _waveletReCon[ 15 ] = 0.016990639867602342;
    _waveletReCon[ 16 ] = -0.0019142861290887667;
    _waveletReCon[ 17 ] = -0.0019088317364812906;

  } // BiOrthogonal_6_8

} // BiOrthogonal_6_8