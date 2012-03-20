import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Suite.class)
@SuiteClasses({ CellTest.class })
public class AllTests {
	
	@Test
	public void testGetValue() {
		Cell tester = new Cell();
		tester.setValue(9);
		assertEquals("Result", 9, tester.getValue());
	}

	@Test
	public void testGetImageIndex() {
		Cell tester = new Cell();
		tester.setImageIndex(2);
		assertEquals("Result", 2, tester.getImageIndex());
	}

	@Test
	public void testGetIsUncovered() {
		Cell tester = new Cell();
		tester.setIsUncovered(true);
		assertTrue("Result", tester.getIsUncovered());
	}

	@Test
	public void testGetIsFlagged() {
		Cell tester = new Cell();
		tester.setIsFlagged(true);
		assertTrue("Result", tester.getIsFlagged());
	}
	
}
