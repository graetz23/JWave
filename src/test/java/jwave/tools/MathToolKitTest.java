package jwave.tools;

import org.junit.Assert;
import org.junit.Test;

public class MathToolKitTest {
	
	private final int[] powersOfTwo= {1,2,4,8,16,32,64,128,256,512,1024,2048};
	private final int[] notPowersOfTwo= {0,3,7,9,21,43,63,65,90,1001,};

	/**
	 * NOTE: Not certain what versions of Java we are supporting so I will avoid the iteration/collects enhancements.
	 * 
	 * Test for jwave.tools.MathToolKit.isBinary(int) : boolean, function. This test
	 * attempts to verify that powers of two are correctly recognized.
	 * 
	 * The list of candidate numbers is infinite, making this test difficult to implement.
	 * Feel free to add more numbers to the list but please do not add so many that the
	 * test takes an unreasonable amount of time.
	 */
	@Test
	public void testPowersOfTwo() {
		MathToolKitTest.checkValue(powersOfTwo, true);
	}

	/**
	 * NOTE: Not certain what versions of Java we are supporting so I will avoid the iteration/collects enhancements.
	 * 
	 * Test for jwave.tools.MathToolKit.isBinary(int) : boolean, function. This test
	 * attempts to verify that powers of two are correctly recognized.
	 * 
	 * The list of candidate numbers is infinite, making this test difficult to implement.
	 * Feel free to add more numbers to the list but please do not add so many that the
	 * test takes an unreasonable amount of time.
	 */
	@Test
	public void testNotPowersOfTwo() {
		MathToolKitTest.checkValue(notPowersOfTwo, false);
	}
	
	/**
	 * 
	 * @param values the list of integers to check
	 * @param condition boolean indicating the expected result for ALL of the integers in the array.
	 */
	private static void checkValue(int[] values, boolean condition) {
		for(int i = 0; i < values.length; i++) {
			Assert.assertTrue("Value, " + values[i] +", failed test", condition == MathToolKit.isBinary(values[i]));
		}
	}
}
