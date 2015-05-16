/**
 * TODO Comment me please!
 *
 * @author Christian Scheiblich (cscheiblich@gmail.com)
 * @date 16.05.2015 16:22:33 
 *
 * SpaceFullTest.java
 */
package math.jwave.datatypes.spaces;

import static org.junit.Assert.*;
import math.jwave.datatypes.blocks.Block;
import math.jwave.datatypes.blocks.BlockFull;
import math.jwave.exceptions.JWaveException;

import org.junit.Test;

/**
 * @author Christian Scheiblich (cscheiblich@gmail.com)
 * @date 16.05.2015 16:22:33
 */
public class SpaceFullTest {

  private int _noOfRows = 100; // adjust runtime of test
  private int _noOfCols = 100; // adjust runtime of test
  private int _noOfLvls = 100; // adjust runtime of test

  /**
   * Generates a Space object already keeping with data: i + j + k!
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 16.05.2015 16:24:42
   * @param noOfRows
   *          0 ..
   * @param noOfCols
   *          0 ..
   * @param noOfLvls
   *          0 ..
   * @return
   * @throws JWaveException
   */
  private Space genSpaceFullObject( int noOfRows, int noOfCols, int noOfLvls )
      throws JWaveException {

    Space space = new SpaceFull( noOfRows, noOfCols, noOfLvls );

    for( int i = 0; i < space.getNoOfRows( ); i++ )
      for( int j = 0; j < space.getNoOfCols( ); j++ )
        for( int k = 0; k < space.getNoOfLvls( ); k++ )
          space.set( i, j, k, (double)( i + j + k ) );

    return space;

  } // genBlockFullObject

  /**
   * Test method for
   * {@link math.jwave.datatypes.spaces.SpaceFull#get(int, int, int)}.
   */
  @Test public void testGet( ) {

    try {

      Space space = genSpaceFullObject( _noOfRows, _noOfCols, _noOfLvls );

      for( int i = 0; i < space.getNoOfRows( ); i++ )
        for( int j = 0; j < space.getNoOfCols( ); j++ )
          for( int k = 0; k < space.getNoOfLvls( ); k++ )
            assertEquals( (double)( i + j + k ), space.get( i, j, k ), 0. );

    } catch( JWaveException e ) {
      fail( "caught exception" );
      e.printStackTrace( );
    } // try 

  }

  /**
   * Test method for
   * {@link math.jwave.datatypes.spaces.SpaceFull#set(int, int, int, double)}.
   */
  @Test public void testSet( ) {

    try {

      Space space = genSpaceFullObject( _noOfRows, _noOfCols, _noOfLvls );

      for( int i = 0; i < space.getNoOfRows( ); i++ )
        for( int j = 0; j < space.getNoOfCols( ); j++ )
          for( int k = 0; k < space.getNoOfLvls( ); k++ )
            space.set( i, j, k, (double)( i + j + k + 1 ) );

      for( int i = 0; i < space.getNoOfRows( ); i++ )
        for( int j = 0; j < space.getNoOfCols( ); j++ )
          for( int k = 0; k < space.getNoOfLvls( ); k++ )
            assertEquals( (double)( i + j + k + 1 ), space.get( i, j, k ), 0. );

    } catch( JWaveException e ) {
      fail( "caught exception" );
      e.printStackTrace( );
    } // try 

  }

}
