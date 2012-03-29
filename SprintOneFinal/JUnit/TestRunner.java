import static org.junit.Assert.*;

import org.junit.Test;


import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {

	@Test
	public void RunAll() {
		
		Result result = JUnitCore.runClasses(CellTest.class);
		for (Failure failure : result.getFailures()) {
			System.out.println(failure.toString());
		}
		result = JUnitCore.runClasses(BeginnerGridTest.class);
		for (Failure failure : result.getFailures()) {
			System.out.println(failure.toString());
		}
	}
}