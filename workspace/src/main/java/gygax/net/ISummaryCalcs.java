package gygax.net;

import java.util.Map;

/**
 * This is a helper interface is for use by the {@code SummaryReport}.
 * 
 * 
 * @author pgygax
 *
 */
public interface ISummaryCalcs {

	/**
	 * 
	 * Returns the word count as a primitive long
	 * 
	 * @return word count
	 */
	long getWordCount();

	/**
	 * 
	 * Returns the average word length as a primitive double
	 * 
	 * @return average word length
	 */
	double getAverageWordLength();

	/**
	 * key is the word length and value is the frequency of the word-length
	 * 
	 * @return an ordered map of all entries
	 */
	Map<Integer, Long> getWordsOfLength();

	/**
	 * key is the word length and value is the frequency of the word-length
	 * 
	 * @return an ordered map containing only the entries having the highest
	 *         frequency (value).
	 */
	Map<Integer, Long> getMostFrequentlyOccurringWordLength();
}
