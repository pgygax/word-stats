package gygax.net;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class SummaryReportTest {
	
	@Test
	public void testExample() {
		String[] words = {"Hello", "world", "&", "good", "morning", "The", "date", "is", "18/05/2016"};
		String result = captureOutput(words);
		
		assertTrue(result.contains("Word count = 9"));
		assertTrue(result.contains("Average word length = 4.556"));
		assertTrue(result.contains("Number of words of length 1 is 1"));
		assertTrue(result.contains("Number of words of length 2 is 1"));
		assertTrue(result.contains("Number of words of length 3 is 1"));
		assertTrue(result.contains("Number of words of length 4 is 2"));
		assertTrue(result.contains("Number of words of length 5 is 2"));
		assertTrue(result.contains("Number of words of length 7 is 1"));
		assertTrue(result.contains("Number of words of length 10 is 1"));
		assertTrue(result.contains("The most frequently occurring word length is 2, for word lengths of 4 & 5"));		
	}
	
	@Test
	public void testNumbers() {
		String[] words = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten",
				"eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen", "twenty",
				"twenty-one"};
		String result = captureOutput(words);
		
		assertTrue(result.contains("Word count = 21"));
		assertTrue(result.contains("Average word length = 5.810"));
		assertTrue(result.contains("Number of words of length 3 is 4"));
		assertTrue(result.contains("Number of words of length 4 is 3"));
		assertTrue(result.contains("Number of words of length 5 is 3"));
		assertTrue(result.contains("Number of words of length 6 is 3"));
		assertTrue(result.contains("Number of words of length 7 is 2"));
		assertTrue(result.contains("Number of words of length 8 is 4"));
		assertTrue(result.contains("Number of words of length 9 is 1"));
		assertTrue(result.contains("Number of words of length 10 is 1"));
		assertTrue(result.contains("The most frequently occurring word length is 4, for word lengths of 3 & 8"));
	}
	
	private String captureOutput(String[] words) {
		List<String> list = Arrays.asList(words);
		Summary summary = new Summary(list);
		ByteArrayOutputStream osCaptor = new ByteArrayOutputStream();		
		PrintStream ps = new PrintStream(osCaptor);
		summary.output(ps);
		return osCaptor.toString();
	}
}
