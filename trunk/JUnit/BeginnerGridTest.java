import static org.junit.Assert.*;

import javax.swing.JFrame;
import javax.swing.JLabel;

import org.junit.Test;


public class BeginnerGridTest extends BeginnerGrid {
	
	@Test
	public void testBeginnerGrid() {
		JLabel statusBar;
		statusBar = new JLabel("");
		StopWatch s = null;
		BeginnerGrid tester = new BeginnerGrid(statusBar, 19, 20, 5, s);
		assertEquals("Result", tester.statusBar, statusBar);
		assertEquals("Result", tester.gridDimX, 19);
		assertEquals("Result", tester.gridDimY, 20);
		assertEquals("Result", tester.numMines, 5);
		assertEquals("Result", tester.s, s);
	}

	@Test(timeout = 500)
	public void testFindEmptyCells() {
		JLabel statusBar;
		statusBar = new JLabel("");
		StopWatch s = null;
		BeginnerGrid tester = new BeginnerGrid(statusBar, 19, 19, 5,s);
		tester.findEmptyCells(0,0);
	}

	@Test(timeout = 500)
	public void testInitializeBoard() {
		JLabel statusBar;
		statusBar = new JLabel("");
		StopWatch s = null;
		BeginnerGrid tester = new BeginnerGrid(statusBar, 19, 19, 5,s);
		tester.initializeBoard(5);
	}

	@Test
	public void testGameOverLose() {
		JLabel statusBar;
		JFrame window = new JFrame();
		statusBar = new JLabel("");
		StopWatch s = new StopWatch(window);
		BeginnerGrid tester = new BeginnerGrid(statusBar, 19, 19, 5,s);
		tester.gameOver(false);
		assertEquals("Result", "Oh no! You lost!", tester.message);
		assertEquals("Result", "D:", tester.title);
	}
	
	@Test
	public void testGameOverWin() {
		JLabel statusBar;
		JFrame window = new JFrame();
		statusBar = new JLabel("");
		StopWatch s = new StopWatch(window);
		BeginnerGrid tester = new BeginnerGrid(statusBar, 19, 19, 5,s);
		tester.gameOver(true);
		assertEquals("Result", "Hooray! You Won!", tester.message);
		assertEquals("Result", ":D", tester.title);
	}

}
