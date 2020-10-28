package gygax.net;

import static org.junit.Assert.assertTrue;

import java.io.InputStream;

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemErrRule;

public class MainTest {
	
    @Rule
    public final SystemErrRule systemErrRule = new SystemErrRule().enableLog();

	
	private static final String[] books = {
			"empty.txt",
			"example.txt",
			"numbers.txt",
			"bible_daily.txt"					
			};
	
	@Test
	public void testFiles() throws Exception {
		for (String filename : books) {
			try (InputStream is = MainTest.class.getClassLoader().getResourceAsStream(filename)) {
				Main.run(is);
			}
		}
	}
	
	@Test
	public void testNoArgCallToMain() throws Exception {
		systemErrRule.clearLog();
		String[] args = {};
		Main.main(args);
		String log = systemErrRule.getLog();
		assertTrue(log.contains(Main.NO_ARGS_MSG));
	}
	
	@Test
	public void testCallToMissingFile() throws Exception {
		systemErrRule.clearLog();
		String[] args = {"missing.txt"};
		Main.main(args);
		String log = systemErrRule.getLog();
		assertTrue(log.contains(String.format(Main.FILE_NOT_FOUND_MSG, "missing.txt")));
	}
	
	@Test
	public void testCallToExampleFile() throws Exception {
		
		String filename = MainTest.class.getResource("/example.txt").getFile();
		String[] args = {filename};
		Main.main(args);
	}
	
	
}
