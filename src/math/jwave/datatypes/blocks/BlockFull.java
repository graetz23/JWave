/**
 * TODO Comment me please!
 *
 * @author Christian Scheiblich (cscheiblich@gmail.com)
 * @date 16.05.2015 14:45:52 
 *
 * BlockFull.java
 */
package math.jwave.datatypes.blocks;

import math.jwave.datatypes.lines.Line;
import math.jwave.datatypes.lines.LineFull;
import math.jwave.exceptions.JWaveException;

/**
 * A block that uses a full array for storage of Line objects.
 * 
 * @author Christian Scheiblich (cscheiblich@gmail.com)
 * @date 16.05.2015 14:45:52
 */
public class BlockFull extends Block {

  /**
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 16.05.2015 15:26:11
   */
  protected Line[ ] _arrLines;

  /**
   * Constructor setting members for and allocating memory!
   * 
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 16.05.2015 14:45:52
   * @param noOfRows
   * @param noOfCols
   */
  public BlockFull( int noOfRows, int noOfCols ) {

    super( noOfRows, noOfCols ); // store does exclusively

    _arrLines = new Line[ _noOfCols ];
    for( int j = 0; j < _noOfCols; j++ )
      _arrLines[ j ] = new LineFull( noOfRows );

  } // BlockFull

  /*
   * Getter!
   * @author Christian Scheiblich (cscheiblich@gmail.com)
   * @date 16.05.2015 15:34:59 (non-Javadoc)
   * @see math.jwave.datatypes.blocks.Block#get(int, int)
   */
  @Override public double get( int i, int j ) throws JWaveException {

    // check( j );
    check( i, j );

    return _arrLines[ j ].get( i ); // checks i again

  } // get

  @Override public void set( int i, int j, double value ) throws JWaveException {

    // check( j );
    check( i, j );

    _arrLines[ j ].set( i, value ); // checks i again

  } // set

}
