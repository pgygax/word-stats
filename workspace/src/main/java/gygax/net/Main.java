package gygax.net;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 * This is the main entry point for the application.
 * 
 * @author pgygax
 *
 */
public class Main {

	private static final Logger LOGGER = LogManager.getLogger(Main.class);

	public static final String NO_ARGS_MSG = "Please supply the path to the filename";
	public static final String FILE_NOT_FOUND_MSG = "File not found: %s";

	public static void main(String[] args) throws IOException {

		if (args.length == 0) {
			System.err.println(NO_ARGS_MSG);
		} else {
			String filename = args[0];

			try (InputStream is = new FileInputStream(filename)) {
				LOGGER.info("using: " + filename);
				run(is);
			} catch (FileNotFoundException e) {
				System.err.println(String.format(FILE_NOT_FOUND_MSG, filename));
			}
		}
	}

	public static void run(InputStream is) throws IOException {

		long start = System.currentTimeMillis();

		try {
			Book book = new Book();
			Summary summary = book.read(is, StandardCharsets.UTF_8);
			LOGGER.info(summary);
			summary.output(System.out);
		} finally {
			long end = System.currentTimeMillis();
			long duration = end - start;
			LOGGER.info(String.format("Execution time: %d ms", duration));
		}

	}
}
