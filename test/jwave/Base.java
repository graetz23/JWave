/**
 * Base class for the JUnit tests of JWave - mainly console out methods for
 * arrays, matrices, and spaces. Also asserting methods for arrays, matrices,
 * and spaces.
 *
 * @author Christian Scheiblich (cscheiblich@gmail.com)
 * @date 09.01.2016 07:36:28 
 *
 * Base.java
 */
package jwave;

import static org.junit.Assert.assertEquals;
import jwave.datatypes.natives.Complex;

/**
 * Base class for the JUnit tests of JWave - mainly console out methods for
 * arrays, matrices, and spaces. Also asserting methods for arrays, matrices,
 * and spaces.
 * 
 * @author Christian Scheiblich (cscheiblich@gmail.com)
 * @date 09.01.2016 07:36:28
 */
public class Base {

  /**
   * Base class for the JUnit tests of JWave - mainly console out methods for
   * arrays, matrices, and spaces. Also asserting methods for arrays, matrices,
   * and spaces.
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 09.01.2016 07:36:28
   */
  protected Base( ) {
  } // Base

  /**
   * assert an array of Complex types.
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 09.01.2016 08:19:07
   * @param expected
   * @param actual
   * @param delta
   */
  protected void assertArray( Complex[ ] expected, Complex[ ] actual,
      double delta ) {

    int expectedLength = expected.length;
    int actualLength = actual.length;

    assertEquals( expectedLength, actualLength );

    for( int c = 0; c < expectedLength; c++ ) {

      double expectedReal = expected[ c ].getReal( );
      double expectedImag = expected[ c ].getImag( );

      double actualReal = actual[ c ].getReal( );
      double actualImag = actual[ c ].getImag( );

      assertEquals( expectedReal, actualReal, delta );
      assertEquals( expectedImag, actualImag, delta );

    } // c

  } // assertArray

  /**
   * assert an array of doubles.
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 09.01.2016 08:19:28
   * @param expected
   * @param actual
   * @param delta
   */
  protected void
      assertArray( double[ ] expected, double[ ] actual, double delta ) {
    for( int i = 0; i < expected.length; i++ )
      assertEquals( expected[ i ], actual[ i ], delta );
  } // assertMatrix

  /**
   * assert a matrix of doubles.
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 09.01.2016 08:19:44
   * @param expected
   * @param actual
   * @param delta
   */
  protected void assertMatrix( double[ ][ ] expected, double[ ][ ] actual,
      double delta ) {
    for( int i = 0; i < expected.length; i++ )
      for( int j = 0; j < expected[ i ].length; j++ )
        assertEquals( expected[ i ][ j ], actual[ i ][ j ], delta );
  } // assertMatrix

  /**
   * assert a space of doubles.
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 09.01.2016 08:20:12
   * @param expected
   * @param actual
   * @param delta
   */
  protected void assertSpace( double[ ][ ][ ] expected, double[ ][ ][ ] actual,
      double delta ) {
    for( int i = 0; i < expected.length; i++ )
      for( int j = 0; j < expected[ i ].length; j++ )
        for( int k = 0; k < expected[ i ][ j ].length; k++ )
          assertEquals( expected[ i ][ j ][ k ], actual[ i ][ j ][ k ], delta );
  } // assertSpace

  /**
   * show double in a certain format.
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 09.01.2016 08:20:32
   * @param value
   */
  protected void show( double value ) {
    System.out.printf( "%6.3f", value );
  } // show

  /**
   * show an array in time domain in a certain format.
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 09.01.2016 08:21:03
   * @param arrTime
   */
  protected void showTime( double[ ] arrTime ) {
    System.out.print( "time domain: " + "\t" + "\t" );
    for( int c = 0; c < arrTime.length; c++ ) {
      show( arrTime[ c ] );
      System.out.print( " " );
    }
    System.out.println( "" );
  } // showTime

  /**
   * show an array in frequency domain in a certain format.
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 09.01.2016 08:22:09
   * @param arrFreq
   */
  protected void showFreq( double[ ] arrFreq ) {
    System.out.print( "frequency domain: " + "\t" );
    for( int c = 0; c < arrFreq.length; c++ ) {
      show( arrFreq[ c ] );
      System.out.print( " " );
    }
    System.out.println( "" );
  } // showHilb

  /**
   * show an array in hilbert domain in a certain format.
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 09.01.2016 08:22:22
   * @param arrHilb
   */
  protected void showHilb( double[ ] arrHilb ) {
    System.out.print( "Hilbert domain: " + "\t" );
    for( int c = 0; c < arrHilb.length; c++ ) {
      show( arrHilb[ c ] );
      System.out.print( " " );
    }
    System.out.println( "" );
  } // showHilb

  /**
   * show an array of type Complex in time domain in a certain format.
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 09.01.2016 08:22:35
   * @param arrTime
   */
  protected void showTime( Complex[ ] arrTime ) {
    System.out.print( "time domain: " + "\t" + "\t" );
    for( int c = 0; c < arrTime.length; c++ )
      System.out.print( arrTime[ c ].toString( ) + " " );
    System.out.println( "" );
  } // showTime

  /**
   * show an array of type Complex in frequency domain in a certain format.
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 09.01.2016 08:22:58
   * @param arrFreq
   */
  protected void showHilb( Complex[ ] arrFreq ) {
    System.out.print( "frequency domain: " + "\t" );
    for( int c = 0; c < arrFreq.length; c++ )
      System.out.print( arrFreq[ c ].toString( ) + " " );
    System.out.println( "" );
  } // showHilb

  /**
   * show a matrix in time domain in a certain format.
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 09.01.2016 08:23:14
   * @param matrixTime
   */
  protected void showTime( double[ ][ ] matrixTime ) {
    System.out.println( "time domain: " + "\t" );
    for( int i = 0; i < matrixTime.length; i++ ) {
      for( int j = 0; j < matrixTime[ i ].length; j++ ) {
        show( matrixTime[ i ][ j ] );
        System.out.print( " " );
      }
      System.out.println( "" );
    }
    System.out.println( "" );
  } // showTime

  /**
   * show a matrix in frequency domain in a certain format.
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 09.01.2016 08:23:29
   * @param matrixFreq
   */
  protected void showFreq( double[ ][ ] matrixFreq ) {
    System.out.println( "frequency domain: " + "\t" );
    for( int i = 0; i < matrixFreq.length; i++ ) {
      for( int j = 0; j < matrixFreq[ i ].length; j++ ) {
        show( matrixFreq[ i ][ j ] );
        System.out.print( " " );
      }
      System.out.println( "" );
    }
    System.out.println( "" );
  } // showFreq

  /**
   * show a matrix in hilbert domain in a certain format.
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 09.01.2016 08:24:04
   * @param matrixHilb
   */
  protected void showHilb( double[ ][ ] matrixHilb ) {
    System.out.println( "Hilbert domain: " + "\t" );
    for( int i = 0; i < matrixHilb.length; i++ ) {
      for( int j = 0; j < matrixHilb[ i ].length; j++ ) {
        show( matrixHilb[ i ][ j ] );
        System.out.print( " " );
      }
      System.out.println( "" );
    }
    System.out.println( "" );
  } // showHilb

  /**
   * show a space in time domain in a certain format.
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 09.01.2016 08:24:18
   * @param spaceTime
   */
  protected void showTime( double[ ][ ][ ] spaceTime ) {
    System.out.println( "time domain: " + "\t" );
    for( int i = 0; i < spaceTime.length; i++ ) {
      for( int j = 0; j < spaceTime[ i ].length; j++ ) {
        for( int k = 0; k < spaceTime[ i ][ j ].length; k++ ) {
          show( spaceTime[ i ][ j ][ k ] );
          System.out.print( " " );
        }
        System.out.println( "" );
      }
      System.out.println( "" );
    }
    System.out.println( "" );
  } // showTime

  /**
   * show a space in frequency domain in a certain format.
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 09.01.2016 08:24:34
   * @param spaceFreq
   */
  protected void showFreq( double[ ][ ][ ] spaceFreq ) {
    System.out.println( "frequency domain: " + "\t" );
    for( int i = 0; i < spaceFreq.length; i++ ) {
      for( int j = 0; j < spaceFreq[ i ].length; j++ ) {
        for( int k = 0; k < spaceFreq[ i ][ j ].length; k++ ) {
          show( spaceFreq[ i ][ j ][ k ] );
          System.out.print( " " );
        }
        System.out.println( "" );
      }
      System.out.println( "" );
    }
    System.out.println( "" );
  } // showFreq

  /**
   * show a space in hilbert domain in a certain format.
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 09.01.2016 08:24:47
   * @param spaceTime
   */
  protected void showHilb( double[ ][ ][ ] spaceTime ) {
    System.out.println( "Hilbert domain: " + "\t" );
    for( int i = 0; i < spaceTime.length; i++ ) {
      for( int j = 0; j < spaceTime[ i ].length; j++ ) {
        for( int k = 0; k < spaceTime[ i ][ j ].length; k++ ) {
          show( spaceTime[ i ][ j ][ k ] );
          System.out.print( " " );
        }
        System.out.println( "" );
      }
      System.out.println( "" );
    }
    System.out.println( "" );
  } // showHilb

} // class
