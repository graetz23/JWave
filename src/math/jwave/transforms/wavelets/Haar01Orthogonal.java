/**
 * JWave - Java implementation of wavelet transform algorithms
 *
 * Copyright 2008-2014 Christian Scheiblich
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at 
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 *
 * This file is part of JWave.
 *
 * @author Christian Scheiblich
 * @date 23.05.2008 17:42:23
 * cscheiblich@gmail.com
 */
package math.jwave.transforms.wavelets;

/**
 * Alfred Haar's orthogonal wavelet transform.
 * 
 * @date 03.06.2010 09:47:24
 * @author Christian Scheiblich
 */
public class Haar01Orthogonal extends Wavelet {

  /**
   * Constructor setting up the orthogonal Haar wavelet coefficients and the
   * scaling coefficients.
   * 
   * @date 03.06.2010 09:47:24
   * @author Christian Scheiblich
   */
  public Haar01Orthogonal( ) {

    //
    // Remark on mathematics (perpendicular, orthogonal, and orthonormal):
    // 
    // "Orthogonal" is used for vectors which are perpendicular but of any length.
    // "Orthonormal" is used for vectors which are perpendicular and of a unit length of one.
    //
    //
    // "Orthogonal" system -- ASCII art does not display the angles in 90 deg (or 45 deg):
    //            
    //    ^ y          
    //    |          
    //  1 +      .  scaling function  
    //    |    /    {1,1}  \
    //    |  /             | length = 1.4142135623730951
    //    |/               /        = sqrt( (1)^2 + (1)^2 )
    //  --o------+-> x
    //  0 |\     1         \
    //    |  \             | length = 1.4142135623730951
    //    |    \           /        = sqrt( (1)^2 + (-1)^2 )
    // -1 +      .  wavelet function     
    //    |         {1,-1}
    //
    // One can see that by each step of the algorithm the input coefficients "energy" 
    // (energy := ||.||_2 euclidean norm) rises, while ever input value is multiplied
    // by 1.414213 (sqrt(2)). However, one has to correct this change of "energy" in
    // the reverse transform by adding the factor 1/2 .
    //
    // (see http://en.wikipedia.org/wiki/Euclidean_norm  for the euclidean norm)
    //
    // The main disadvantage using an "orthogonal" wavelet is that the generated wavelet
    // sub spaces of different size and/or level can not be combined anymore easily, due
    // to their differing "energy" or norm (||.||_2). If an "orthonormal" wavelet is
    // taken, the ||.||_2 norm does not change at any size or any transform level. This
    // allows for combining wavelet sub spaces of different dimension or even level.

    // Also possible coefficients -> change forward and reverse functions in common
    // _waveletDeCom[ 0 ] = .5; // w0 
    // _waveletDeCom[ 1 ] = -.5; // w1
    // _scalingDeCom[ 0 ] = -_waveletDeCom[ 1 ]; // -w1 
    // _scalingDeCom[ 1 ] = _waveletDeCom[ 0 ]; // w0
    // The ||.||_2 norm will shrink compared to the input signal's norm.

    _transformWavelength = 2; // minimal wavelength of input signal

    _motherWavelength = 2; // wavelength of mother wavelet

    // Orthogonal wavelet coefficients; NOT orthonormal, due to missing sqrt(2.) 
    _scalingDeCom = new double[ _motherWavelength ]; // can be done in static way also; faster?
    _scalingDeCom[ 0 ] = 1.; // w0 
    _scalingDeCom[ 1 ] = 1.; //  w1

    // Rule for constructing an orthogonal vector in R^2 -- scales
    _waveletDeCom = new double[ _motherWavelength ]; // can be done in static way also; faster?
    _waveletDeCom[ 0 ] = _scalingDeCom[ 1 ]; // w1 
    _waveletDeCom[ 1 ] = -_scalingDeCom[ 0 ]; // -w0

    // Copy to reconstruction filters due to orthogonality (orthonormality)!
    _scalingReCon = new double[ _motherWavelength ];
    _waveletReCon = new double[ _motherWavelength ];
    for( int i = 0; i < _motherWavelength; i++ ) {

      _scalingReCon[ i ] = _scalingDeCom[ i ];
      _waveletReCon[ i ] = _waveletDeCom[ i ];

    } // i

  } // Haar01

  /**
   * The forward wavelet transform using the Alfred Haar's wavelet. The arrTime
   * array keeping coefficients of time domain should be of length 2 to the
   * power of p -- length = 2^p where p is a positive integer.
   * 
   * @author Christian Scheiblich
   * @date 15.02.2014 21:16:54
   */
  @Override public double[ ] forward( double[ ] arrTime, int arrTimeLength ) {

    double[ ] arrHilb = new double[ arrTimeLength ];

    int h = arrHilb.length >> 1; // .. -> 8 -> 4 -> 2 .. shrinks in each step by half wavelength

    for( int i = 0; i < h; i++ ) {

      arrHilb[ i ] = arrHilb[ i + h ] = 0.; // set to zero before sum up

      for( int j = 0; j < _motherWavelength; j++ ) {

        int k = ( i * 2 ) + j; // int k = ( i << 1 ) + j;

        while( k >= arrHilb.length )
          k -= arrHilb.length; // circulate over arrays if scaling and wavelet are are larger

        arrHilb[ i ] += arrTime[ k ] * _scalingDeCom[ j ]; // low pass filter for the energy (approximation)
        arrHilb[ i + h ] += arrTime[ k ] * _waveletDeCom[ j ]; // high pass filter for the details

      } // Sorting each step in patterns of: { scaling coefficients | wavelet coefficients }

    } // h = 2^(p-1) | p = { 1, 2, .., N } .. shrinks in each step by half wavelength 

    return arrHilb;

  } // forward

  /**
   * The reverse wavelet transform using the Alfred Haar's wavelet. The arrHilb
   * array keeping coefficients of Hilbert domain should be of length 2 to the
   * power of p -- length = 2^p where p is a positive integer. But in case of an
   * only orthogonal Haar wavelet the reverse transform has to have a factor of
   * 0.5 to reduce the up sampled "energy" ion Hilbert space.
   * 
   * @author Christian Scheiblich
   * @date 15.02.2014 21:17:22
   */
  @Override public double[ ] reverse( double[ ] arrHilb, int arrHilbLength ) {

    double[ ] arrTime = new double[ arrHilbLength ];

    for( int i = 0; i < arrTime.length; i++ )
      arrTime[ i ] = 0.;

    int h = arrTime.length >> 1; // .. -> 8 -> 4 -> 2 .. shrinks in each step by half wavelength

    for( int i = 0; i < h; i++ ) {

      for( int j = 0; j < _motherWavelength; j++ ) {

        int k = ( i * 2 ) + j; // int k = ( i << 1 ) + j;

        while( k >= arrTime.length )
          k -= arrTime.length; // circulate over arrays if scaling and wavelet are larger

        // adding up energy from low pass (approximation) and details from high pass filter;
        // but it has to be reduced in energy by half because of vectorial length of sqrt( 2. ).
        arrTime[ k ] +=
            .5 * ( ( arrHilb[ i ] * _scalingReCon[ j ] ) + ( arrHilb[ i + h ] * _waveletReCon[ j ] ) );

      } // Reconstruction from patterns of: { scaling coefficients | wavelet coefficients }

    } // h = 2^(p-1) | p = { 1, 2, .., N } .. shrink in each step by half wavelength 

    return arrTime;

  } // reverse

} // Haar01Orthogonal