package gygax.net;

import java.io.PrintStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 * This class represents a summary of the word count frequencies. Summaries can
 * be applied to a BATCH_SIZE of words and rolled up using Summary::apply to
 * apply to the whole book.
 * 
 * A long was chosen to that frequencies of word length above 2^31 could be
 * stored.
 * 
 * @author pgygax
 *
 */
public class Summary implements ISummaryReducer, ISummaryCalcs {

	private static final Logger LOGGER = LogManager.getLogger(Summary.class);

	// Map is immutable, assigned via Collections.unmodifiableMap by the
	// constructors.
	// The key of type Integer represents the word length
	// The value of type Long represents the frequency of the word-length
	private final Map<Integer, Long> map;

	/**
	 * Algorithm: iterate each word in the list, capture the word length and current
	 * frequency, increment the frequency by 1 and add back to the map
	 * 
	 * @param words the list of words to converted into word frequencies and added
	 *              to the map.
	 */
	public Summary(List<String> words) {
		Map<Integer, Long> map = new HashMap<>();
		words.stream().forEach(word -> {
			int len = word.length();
			long freq = map.getOrDefault(len, 0L);
			map.put(len, freq + 1);
		});
		this.map = Collections.unmodifiableMap(map);
		LOGGER.debug(this);
	}

	private Summary(Map<Integer, Long> map) {
		this.map = Collections.unmodifiableMap(map);
	}

	/**
	 * Implements the interface {@code ISummaryReducer}.
	 * 
	 * @param summary to be merged in with this summary
	 * @return merged {@code Summary} record
	 */
	@Override
	public Summary apply(Summary summary) {
		Map<Integer, Long> map = new HashMap<>(this.map);
		summary.map.entrySet().forEach(entry -> {
			Integer key = entry.getKey();
			Long value = entry.getValue() + map.getOrDefault(key, 0L);
			map.put(key, value);
		});
		return new Summary(map);
	}

	/**
	 * Render out the summary report to the {@code PrintStream}, which could be
	 * System.out
	 * 
	 * @param out the {@code PrintStream} to write to.
	 */
	public void output(PrintStream out) {
		new SummaryReport(this).output(out);
	}

	@Override
	public String toString() {
		return "Summary [map=" + map + "]";
	}

	@Override
	public long getWordCount() {
		return map.values().stream().reduce(0L, Long::sum);
	}

	@Override
	public double getAverageWordLength() {
		long total = map.entrySet().stream()
				.collect(Collectors.summingLong(entry -> entry.getKey() * entry.getValue()));
		long wordCount = getWordCount();
		return wordCount == 0L ? 0L : (double) total / wordCount;
	}

	@Override
	public Map<Integer, Long> getWordsOfLength() {
		return new TreeMap<>(map);
	}

	@Override
	public Map<Integer, Long> getMostFrequentlyOccurringWordLength() {
		Long max = map.isEmpty() ? 0L : Collections.max(map.values());
		Map<Integer, Long> freqMap = map.entrySet().stream().filter(entry -> entry.getValue().equals(max))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		return new TreeMap<>(freqMap);
	}
}
