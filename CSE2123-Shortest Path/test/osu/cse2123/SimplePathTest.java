package osu.cse2123;
/**
 * Test cases for the Simple Path object.
 * 
 * @name Zina Pichkar
 * @version 11292021
 * 
 */
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class SimplePathTest {
	
	static SimplePath[] test;
	
	@BeforeAll
	static void initialize() {
		test = new SimplePath[5];
		test[0] = new SimplePath("Columbus", 2.0);
		test[1] = new SimplePath("Columbus", 2.0);
		test[2] = new SimplePath("AnnArbor", 3.0);
		test[3] = new SimplePath("Miami", 5.0);
		test[4] = new SimplePath("Dallas", 5.0);

		}
	
	@Test
	void testToString1() {
		assertEquals("(Columbus:2.0)",test[0].toString());
	}
	
	@Test
	void testToString2() {
		assertEquals("(Dallas:5.0)",test[4].toString());
	}
	
	@Test
	void testGetEndpoint1() {
		assertEquals("Columbus", test[0].getEndpoint());
	}
	
	@Test
	void testGetEndpoint2() {
		assertEquals("AnnArbor", test[2].getEndpoint());
	}
	
	@Test
	void testGetCost1() {
		assertEquals(2.0, test[0].getCost(), 0.001);
	}
	
	@Test
	void testGetCost2() {
		assertEquals(5.0, test[4].getCost(), 0.001);
	}
		
	@Test
	void testEqualsSame() {
		assertEquals(test[0],test[1]);
	}

	@Test
	void testNotEquals() {
		assertNotEquals(test[0],test[2]);
	}
	
	@Test
	void testNotEqualsEndpoint1() {
		assertNotEquals(test[0],test[4]);
	}
	
	@Test
	void testNotEqualsEndpoint2() {
		assertNotEquals(test[3],test[4]);
	}
	
	@Test
	void testHashCodeSameValue() {
		assertEquals(test[0].hashCode(),test[1].hashCode());
	}
	
	@Test
	void testHashCodeAtLeastOneDifferent() {
		int i = 2;
		boolean same = true;
		while (i < test.length && same) {
			same = test[0].hashCode() == test[i].hashCode();
		}
		assertTrue(!same);
	}
	
	
	@Test
	void testCompareToSame1() {
		assertEquals(0, test[0].compareTo(test[1]));
	}
	
	@Test
	void testCompareToSame2() {
		assertEquals(0, test[3].compareTo(test[4]));
	}
	
	@Test
	void testCompareToNotSame1() {
		assertEquals(1, test[3].compareTo(test[2]));
	}
	
	@Test
	void testCompareToNotSame2() {
		assertEquals(-1, test[2].compareTo(test[3]));
	}
	
	
	

}
