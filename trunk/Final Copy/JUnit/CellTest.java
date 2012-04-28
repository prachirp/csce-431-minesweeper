import static org.junit.Assert.*;

import org.junit.Test;


public class CellTest {

	@Test
	public void testGetValue() {
		Cell tester = new Cell();
		tester.setValue(9);
		assertEquals("Result", 8, tester.getValue());
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
