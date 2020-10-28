package gygax.net;

/**
 * 
 * A utility helper class focused on the cleansing of individual words so that
 * word length frequencies are captured correctly.
 * 
 * @author pgygax
 *
 */
public class WordHelper {

	private static final char[] PUNCTUATION_CHARS = { ',', '.', ';', ':', '!', '?' };

	/**
	 * do not instantiate this class as it only contains stateless helper methods.
	 */
	private WordHelper() {
	}

	/**
	 * What constitutes a word? ... the boundary of a word is its surrounding
	 * whitespace. Additionally a word may end up surrounded by a punctuation
	 * character such as a comma, fullstop or a colon.
	 * 
	 * This method is likely to need some tweaking in order to obtain the desired
	 * results. Perhaps it ought to be written in terms of a regular expression.
	 * One would wish to check the performance overhead of applying a regex to all
	 * or vast numbers of words in a book.
	 * 
	 * Rules / assumptions ... 1. Only strip undesired characters from the end of a
	 * word; 2. An apostrophe should always be retained; 3. A leading dot should be
	 * retained if at the start of a word (considering numbers);
	 * 
	 * @param word that is to be cleansed
	 * @return the resultant word cleansed of punctuation etc.
	 */
	public static String cleanse(String word) {

		if (isCompletelyNonAlphanumeric(word)) {
			return "";
		}

		while (!word.isEmpty()) {
			char last = word.charAt(word.length() - 1);
			if (isPunctuation(last)) {
				word = word.substring(0, word.length() - 1);
			} else {
				break;
			}
		}

		return word;
	}

	private static boolean isCompletelyNonAlphanumeric(String word) {
		boolean noAlpha = word.codePoints().noneMatch(cp -> Character.isAlphabetic(cp));
		boolean noNumeric = word.codePoints().noneMatch(cp -> Character.isDigit(cp));
		boolean noAmpersand = !word.contains("&");
		return noAlpha && noNumeric && noAmpersand;
	}

	private static boolean isPunctuation(char ch) {
		return new String(PUNCTUATION_CHARS).contains(String.valueOf(ch));
	}
}
