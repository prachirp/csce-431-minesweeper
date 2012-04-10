import static org.junit.Assert.*;

import org.junit.Test;


import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {
	File file;

	@Test
	public void RunAll() {
		
		Formatter inputfile;
		int counter = 0;
		file = new File("ErrorReport.txt");
		
		if(!file.exists()){	
			try {
				inputfile = new Formatter("ErrorReport.txt");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
			return;
		}
		
		Result result = JUnitCore.runClasses(CellTest.class); //Test cell class
		

			try {
				inputfile = new Formatter("ErrorReport.txt");
				for (Failure failure : result.getFailures()) {
					//System.out.println(failure.toString());
					inputfile.format("%s", failure.toString());
					counter += result.getRunCount();
				}

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

		
		result = JUnitCore.runClasses(BeginnerGridTest.class); //Test beginner grid

			try {
				inputfile = new Formatter("ErrorReport.txt");
				for (Failure failure : result.getFailures()) {
					inputfile.format("%s", failure.toString());
					counter += result.getRunCount();
				}

			} catch (FileNotFoundException e) {
				e.printStackTrace();
		}
		
			try {
				inputfile = new Formatter("ErrorReport.txt");
				inputfile.format("%x tests completed", counter);
				inputfile.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
	}
}