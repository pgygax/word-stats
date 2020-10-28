package gygax.net;

/**
 * 
 * Functional interface implemented by Summary::apply
 * 
 * @author pgygax
 *
 */
@FunctionalInterface
public interface ISummaryReducer {

	/**
	 * Algorithm: copy the existing map entry values into a local map, iterate the
	 * passed in summary map and with each entry, capture the key and values,
	 * increment value by the value in the local map increment the frequency of
	 * words in the local map. return a summary object having the combined map
	 * values.
	 */
	Summary apply(Summary summary);
}
