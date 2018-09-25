import java.util.logging.Level;
import java.util.logging.Logger;


public class SimpleLogging {
	static long TotalTime = 0;

	public void logging(String message) {
		System.setProperty("java.util.logging.config.file",
				"./logging.properties");
		Logger logger = Logger.getLogger("myLog");

		logger.log(Level.INFO, message);
	}

	public static void main(String[] args) {

		final long startTime = System.currentTimeMillis();

		SimpleLogging log = new SimpleLogging();

		DirWalker.createDirWalker(); //invoke createDirWalker method in DirWalker class

		final long endTime = System.currentTimeMillis();
		TotalTime = endTime - startTime;

		String logMsg = "Total Execution Time(ms): " + TotalTime + " ms" +
				"\n Total Number of Valid Rows: " + SimpleCsvParser.getValidRecord() +
				"\n Total Number of Skipped Rows: " + SimpleCsvParser.getSkippedRow();

		log.logging(logMsg);
	}
}
