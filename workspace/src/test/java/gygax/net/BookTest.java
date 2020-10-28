package gygax.net;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.Test;

public class BookTest {
	
	@Test
	public void testParsingOfNumbers() throws Exception {
		try (InputStream is = BookTest.class.getClassLoader().getResourceAsStream("numbers.txt")) {
			Book book = new Book();
			Summary summary = book.read(is, StandardCharsets.UTF_8);
			ISummaryCalcs summaryCalcs = summary;
			assertEquals(21, summaryCalcs.getWordCount());
		}
	}
}
