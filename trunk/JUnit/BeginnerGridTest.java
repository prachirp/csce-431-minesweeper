import static org.junit.Assert.*;

import javax.swing.JLabel;

import org.junit.Test;


public class BeginnerGridTest {

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

	@Test(timeout = 500)
	public void testGameOver() {
		JLabel statusBar;
		statusBar = new JLabel("");
		StopWatch s = null;
		BeginnerGrid tester = new BeginnerGrid(statusBar, 19, 19, 5,s);
		tester.gameOver(false);
	}

}
