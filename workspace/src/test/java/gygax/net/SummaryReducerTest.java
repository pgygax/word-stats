package gygax.net;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class SummaryReducerTest {

	private ISummaryCalcs sc;
	
	@Before
	public void setup() {
		String[] words1 = {"1", "22", "333"};
		Summary summary1 = getSummary(words1);
		String[] words2 = {"333", "4444"};
		Summary summary2 = getSummary(words2);		
		sc = summary1.apply(summary2);
	}
	
	
	@Test
	public void testWordCount() {
		assertEquals(5, sc.getWordCount());
	}
	
	@Test
	public void testGetAverageWordLength() {
		assertEquals((1 + 2 + 2*3 + 4) / 5.0, sc.getAverageWordLength(), 0.001);
	}
	
	@Test
	public void testGetNumberOfWordsOfLength() {
		Map<Integer, Long> map = sc.getWordsOfLength();
		assertEquals(4, map.size());
		assertEquals(Long.valueOf(1), map.get(1));
		assertEquals(Long.valueOf(1), map.get(2));
		assertEquals(Long.valueOf(2), map.get(3));
		assertEquals(Long.valueOf(1), map.get(4));
	}
	
	@Test
	public void testGetMostFrequentlyOccurringWordLength() {
		Map<Integer, Long> map = sc.getMostFrequentlyOccurringWordLength();
		assertEquals(1, map.size());
		assertEquals(Long.valueOf(2), map.get(3));		
	}
	
	private Summary getSummary(String[] words) {
		List<String> list = Arrays.asList(words);
		return new Summary(list);
	}
}
