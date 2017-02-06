package engine.ReadWrite;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * this was made to log exceptions to a file instead of into terminal, this
 * Feature is not done yet!
 * 
 * @author Daniel Amos Grenehed
 * 
 */
public class ExceptionLogger {

	String LogName = "Exception.log";

	/***/
	public ExceptionLogger() {
		// Error = new PrintStream();
		// ups = new OutputStream();
		Error = new PrintStream(ups);
	}

	/***/
	public ExceptionLogger(File output) {
		try {
			ups = new FileOutputStream(output, true);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Error = new PrintStream(ups, true);
	}

	OutputStream ups;

	PrintStream Error;

	/**
	 * enable exception logging
	 */
	public void setLoggOn() {
		System.setErr(Error);

	}

	/**
	 * closes the current exception stream
	 */
	public void Close() {
		Error.flush();
		Error.close();
		try {
			ups.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			ups.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}