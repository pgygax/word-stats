package gygax.net;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class SummaryCalcsTest {

	private ISummaryCalcs sc;
	
	@Before
	public void setup() {
		String[] words = {"Hello", "world", "&", "good", "morning", "The", "date", "is", "18/05/2016"};
		List<String> list = Arrays.asList(words);
		sc= new Summary(list);
	}
	
	@Test
	public void testWordCount() {
		assertEquals(9, sc.getWordCount());
	}
	
	@Test
	public void testGetAverageWordLength() {
		assertEquals(4.556, sc.getAverageWordLength(), 0.001);
	}
	
	@Test
	public void testGetNumberOfWordsOfLength() {
		Map<Integer, Long> map = sc.getWordsOfLength();
		assertEquals(7, map.size());
		assertEquals(Long.valueOf(1), map.get(1));
		assertEquals(Long.valueOf(1), map.get(2));
		assertEquals(Long.valueOf(1), map.get(3));
		assertEquals(Long.valueOf(2), map.get(4));
		assertEquals(Long.valueOf(2), map.get(5));
		assertEquals(Long.valueOf(1), map.get(7));
		assertEquals(Long.valueOf(1), map.get(10));
	}
	
	@Test
	public void testGetMostFrequentlyOccurringWordLength() {
		Map<Integer, Long> map = sc.getMostFrequentlyOccurringWordLength();
		assertEquals(2, map.size());
		assertEquals(Long.valueOf(2), map.get(4));
		assertEquals(Long.valueOf(2), map.get(5));		
	}
}
