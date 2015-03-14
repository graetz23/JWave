/**
 * Create Transform objects ...
 *
 * @author Christian Scheiblich (cscheiblich@gmail.com)
 * @date 14.03.2015 14:25:21 
 *
 * TransformBuilder.java
 */
package math.jwave;

import math.jwave.exceptions.JWaveFailure;
import math.jwave.transforms.BasicTransform;
import math.jwave.transforms.DiscreteFourierTransform;
import math.jwave.transforms.FastWaveletTransform;
import math.jwave.transforms.WaveletPacketTransform;
import math.jwave.transforms.wavelets.Wavelet;
import math.jwave.transforms.wavelets.WaveletBuilder;

/**
 * Class for creating and identifying Transform object.
 * 
 * @author Christian Scheiblich (cscheiblich@gmail.com)
 * @date 14.03.2015 14:25:21
 */
public class TransformBuilder {

  /**
   * Create a Transform object by a given string and a given Wavelet object.
   * Look into each Transform for matching string identifier.
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 14.03.2015 14:35:12
   * @param name
   *          identifier as stored in Transform object
   * @param wavelet
   * @return a matching object of type Transform
   * @throws JWaveFailure
   *           if string is not valid or Transform is broken yet
   */
  static public Transform create( String name, Wavelet wavelet )
      throws JWaveFailure {

    BasicTransform basicTransform = null;

    switch( name ){

      case "Discrete Fourier Transform":
        basicTransform = new DiscreteFourierTransform( );
        break;

      case "Fast Wavelet Transform":
        basicTransform = new FastWaveletTransform( wavelet );
        break;

      case "Wavelet Packet Transform":
        basicTransform = new WaveletPacketTransform( wavelet );
        break;

      default:

        throw new JWaveFailure(
            "TransformBuilder::create - unknown type of transform for given string!" );

    } // switch

    return new Transform( basicTransform );

  } // create

  /**
   * Create a Transform object by a given string and a given string for a
   * Wavelet object. Look into each Transform and Wavelet for matching string
   * identifier.
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 14.03.2015 14:37:30
   * @param name
   *          identifier as stored in Transform object
   * @param waveletName
   * @return identifier as stored in Wavelet object
   * @throws JWaveFailure
   *           if string is not valid or Transform is broken yet
   */
  static public Transform create( String name, String waveletName )
      throws JWaveFailure {

    return create( name, WaveletBuilder.create( waveletName ) );

  } // create

  /**
   * Returns the identifier string of a given Transform object.
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 14.03.2015 14:34:20
   * @param transform
   *          string identifier of the given Transform object
   * @return a string as the identifier of the given Transform object
   */
  static public String identify( BasicTransform transform ) {

    return transform.getName( );

  } // identify

} // class
