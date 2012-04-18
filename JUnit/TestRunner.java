import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Formatter;

import org.junit.Test;


import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {
	File file;

	@Test
	public void RunAll() {
		
		Formatter inputfile;
		int totalcounter = 0;
		int failcounter = 0;
		file = new File("ErrorReport.txt");
		
		try{
			
			if(!file.exists())
				inputfile = new Formatter("ErrorReport.txt");
		
		Result result = JUnitCore.runClasses(CellTest.class); //Test cell class
	
		inputfile = new Formatter("ErrorReport.txt");
		for (Failure failure : result.getFailures()) {
			inputfile.format("Error:  %s \n", failure.toString());
			failcounter++;
		}
		totalcounter += result.getRunCount();
		
		result = JUnitCore.runClasses(BeginnerGridTest.class); //Test beginner grid

		for (Failure failure : result.getFailures()) {
			inputfile.format("Error:  %s \n", failure.toString());
			failcounter++;
		}
		totalcounter += result.getRunCount();
		
		inputfile.format("%d tests completed, %d tests failed", totalcounter,failcounter);
		inputfile.close();
		} 
		catch (FileNotFoundException e) {
				e.printStackTrace();
			}	
	}
}