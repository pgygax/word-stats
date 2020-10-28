package gygax.net;

import java.io.PrintStream;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 
 * This class is responsible for the formatting of the summary report.
 * 
 * It uses the methods defined in the {@code ISummaryCalcs} interface to either
 * return a primitive in the case of word count or a subset of {@code Summary}'s
 * {@code Map}.
 * 
 * @author pgygax
 *
 */
public class SummaryReport {

	private final static String WORD_COUNT = "Word count = %d";

	private final static String AVG_WORD_LENGTH = "Average word length = %.3f";

	private final static String WORDS_OF_LENGTH = "Number of words of length %d is %d";

	private final static String MOST_FREQ_OCCURRING_WORD_LEN = "The most frequently occurring word length is %d, for word lengths of %s";

	private final ISummaryCalcs summaryCalcs;

	/**
	 * Constructor
	 * 
	 * @param summaryCalcs takes a {code ISummaryCalcs} interface as an arg.
	 */
	public SummaryReport(ISummaryCalcs summaryCalcs) {
		this.summaryCalcs = summaryCalcs;
	}

	/**
	 * Outputs the report to a PrintStream
	 * 
	 * @param out pass in System.out to send the report to standard-out.
	 */
	public void output(PrintStream out) {

		out.println(String.format(WORD_COUNT, summaryCalcs.getWordCount()));

		out.println(String.format(AVG_WORD_LENGTH, summaryCalcs.getAverageWordLength()));

		for (Entry<Integer, Long> entry : summaryCalcs.getWordsOfLength().entrySet()) {
			out.println(String.format(WORDS_OF_LENGTH, entry.getKey(), entry.getValue()));
		}

		Map<Integer, Long> map = summaryCalcs.getMostFrequentlyOccurringWordLength();
		if (!map.isEmpty()) {
			long freq = map.values().stream().findAny().get();
			Set<String> wordLengths = map.keySet().stream().map(v -> String.valueOf(v)).collect(Collectors.toSet());
			out.println(String.format(MOST_FREQ_OCCURRING_WORD_LEN, freq, getFormattedWordLengths(wordLengths)));
		}
	}

	private String getFormattedWordLengths(Set<String> ints) {
		if (ints.size() == 1) {
			return ints.stream().findFirst().get();
		} else {
			String joined = String.join(", ", ints);
			int last = joined.lastIndexOf(',');
			StringBuilder sb = new StringBuilder();
			sb.append(joined.substring(0, last)).append(" &").append(joined.substring(last + 1));
			return sb.toString();
		}
	}

}
