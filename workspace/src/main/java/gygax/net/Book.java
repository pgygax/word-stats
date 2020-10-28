package gygax.net;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * 
 * The the book is parsed in as batches of BATCH_SIZE words collated into a
 * {@code List}. The summary records are then merged and a single summary record
 * for the book is produced.
 * 
 * @author pgygax
 *
 */
public class Book {
	private static final int BATCH_SIZE = 1000;

	/**
	 * reads in a file from an {@code InputStream}
	 * 
	 * @param is      {@code InputStream}
	 * @param charset {@code Charset}
	 * @return a {@code Summary} record ready to be output to {@code SummaryReport}
	 * @throws IOException in the event of an issue
	 */
	public Summary read(InputStream is, Charset charset) throws IOException {

		// Summary records are batched up into BATCH_SIZE words.
		// Theses summaries are then collated into a List
		List<Summary> list = read(is, charset, BATCH_SIZE);

		// The summary records are then reduced to a single record
		// containing the full summary details. The merging of the
		// summary records takes place in Summary::apply.
		return list.parallelStream().reduce(Summary::apply).orElse(new Summary(Collections.emptyList()));
	}

	private List<Summary> read(InputStream is, Charset charset, int batchSize) {
		try (Scanner scanner = new Scanner(is, charset.name())) {
			List<Summary> result = new ArrayList<>();
			List<String> words = new ArrayList<>();

			// while there are more words to be read
			while (scanner.hasNext()) {

				// reads in the next word using the scanner
				String word = scanner.next();

				// each word is cleansed if necessary
				String cleansed = WordHelper.cleanse(word);

				// if there is still content following the cleanse
				// then add the word to the list of words
				if (cleansed.length() > 0) {
					words.add(cleansed);
				}

				// when we've batched up enough words,
				// then summarise the batch of words
				if (words.size() == batchSize) {
					result.add(new Summary(words));
					words.clear();
				}
			}
			// summarise any remaining words
			if (!words.isEmpty()) {
				result.add(new Summary(words));
			}

			// return a list of all of the summarised entries
			return result;
		}
	}
}