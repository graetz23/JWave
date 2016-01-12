/**
 * Testing SuperLineObjects
 *
 * @author Christian Scheiblich (cscheiblich@gmail.com)
 * @date 12.01.2016 23:56:32 
 *
 * SuperLineTest.java
 */
package jwave.datatypes;

import static org.junit.Assert.*;
import jwave.datatypes.lines.Line;
import jwave.exceptions.JWaveException;

import org.junit.Test;

/**
 * Testing SuperLine objects
 * 
 * @author Christian Scheiblich (cscheiblich@gmail.com)
 * @date 12.01.2016 23:56:32
 */
public class SuperLineTest {

  /**
   * Test method for
   * {@link jwave.datatypes.SuperLine#init(jwave.datatypes.lines.Line)}.
   * 
   * @throws JWaveException
   */
  @Test public void testInit( ) throws JWaveException {

    int setNoOfRows = 1023;
    int setMaxBlockSize = 256;

    SuperLine superLine = new SuperLine( setNoOfRows, setMaxBlockSize );
    superLine.init( );

    int noOfRows = superLine.getNoOfRows( );

    assertEquals( setNoOfRows, noOfRows );

    int noOfLines = superLine.getNoOfLines( );

    assertEquals( 11, noOfLines );

    for( int l = 0; l < superLine.getNoOfLines( ); l++ ) {

      Line line = (Line)superLine.get( l );

      System.out.println( "offset: " + line.getOffSetRow( ) + " size: "
          + line.getNoOfRows( ) );

    } // l

  } // testInit

} // SuperLineTest
