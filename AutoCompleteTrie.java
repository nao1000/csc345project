import java.util.Random;
import java.util.ArrayList;

public class AutoCompleteTrie implements Trie {

	// https://en.wikipedia.org/wiki/Letter_frequency
	// The array is used as a range for when a random double is generated, if the
	// value is between adjusted[i-1]
	// and adjusted[i]... the corresponding nextChar[i] is the probabilistically
	// chosen next letter
	// The letter 'a' is 8.2% common in English, 'b' is about 1.5. The numbers are
	// just added together to form ranges
	// Refer to wikipedia page to see where the numbers came from

	private double[] freqs = new double[] { 8.2, 1.5, 2.8, 4.3, 12.7, 2.2, 2.0, 6.1, 7.0, 0.15, 0.77, 4.0, 2.4, 6.7,
			7.5, 1.9, 0.095, 6.0, 6.3, 9.1, 2.8, 0.98, 2.4, 0.15, 2.0, 0.074 };

	private Random rand = new Random(); // initialize a random
	private TrieNode root; // the root of the trie pointer
	private TrieNode curr; // curr pointer that follows the path of the inputted string
	private int charNAs; // counter for when characters that shouldn't be typed are typed

	// constructor for ACT with ' ' TrieNode
	public AutoCompleteTrie() {
		root = new TrieNode(' ', null);
		curr = root;
		charNAs = 0;
	}

	// getter to curr's key
	public String curr() {
		return String.valueOf(curr.getKey());
	}

	// getter that returns the parent of the current
	public TrieNode parent() {
		return curr.jumpUp();
	}

	// Add a word to the Trie
	public void addWord(String word) {

		// once whole word is stored in Trie
		if (word.length() == 0) {

			// set the terminal
			curr.setTerminal();
			// reset curr
			curr = root;
			return;
		}

		TrieNode[] nextChars = curr.getNodes();

		// if the character doesn't exist for this path, create the new node for it
		if (nextChars[word.charAt(0) - 'a'] == null) {
			TrieNode newNode = new TrieNode(word.charAt(0), curr);
			nextChars[word.charAt(0) - 'a'] = newNode;

			// and then jump down to it
			curr = newNode;

			// otherwise, just jump down to the node
		} else {
			curr = nextChars[word.charAt(0) - 'a'];
		}

		// recursively add the rest of the string to the ACT
		addWord(word.substring(1, word.length()));

	}

	// Delete a word from the Trie
	// Deleting is tricky so nothing is actually deleted from memory.
	// If "cattle" was in the ACT but we deleted "cat", we'd be also deleting
	// "cattle"
	// Therefore, we unset the terminal saying it is no longer a word and keep the
	// characters there.
	public boolean delWord(String word) {

		// if we get down to an actual word
		if (word.length() == 0 && curr.isWord()) {

			// unset the terminal
			curr.unsetTerminal();
			// reset curr
			curr = root;
			return true;

			// if it is not a word, just reset curr, nothing to delete
		} else if (word.length() == 0 && !curr.isWord()) {
			curr = root;
			return false;
		}

		// identical to how addWord adds the character TrieNodes, just traverseing
		// instead
		TrieNode[] nextChars = curr.getNodes();
		if (nextChars[word.charAt(0) - 'a'] == null) {
			curr = root;
			return false;
		} else {
			curr = nextChars[word.charAt(0) - 'a'];
		}

		return delWord(word.substring(1, word.length()));

	}

	// check if a word is in the Trie
	public boolean isWord(String word) {

		// if a word is found, return true and reset curr
		if (word.length() == 0 && curr.isWord()) {
			curr = root;
			return true;

			// otherwise return false and reset curr
		} else if (word.length() == 0 && !curr.isWord()) {
			curr = root;
			return false;
		}

		// Again, identical idea to how addWord and delWord work, just traversing
		TrieNode[] nextChars = curr.getNodes();
		if (nextChars[word.charAt(0) - 'a'] == null) {
			curr = root;
			return false;
		} else {
			curr = nextChars[word.charAt(0) - 'a'];
		}
		return isWord(word.substring(1, word.length()));
	}

	// APPLICATION METHODS\\\

	// returns an array of potential strings that come from a certain input
	public String[] autoComplete(String str, boolean backspace, boolean space) {
		// AutoCompleteUI.hideAdd2Dict();

		// if a misspelled word was being typed, there is a counter until a viable
		// string
		if (backspace && charNAs > 0) {
			charNAs--;
			if (charNAs != 0) {
				// AutoCompleteUI.showAdd2Dict();
				return new String[] { "Check", "your", "spelling" };
			}

			// if we are in the middle of a string, we just jump down to the last typed char
		} else if (!backspace && !space && !str.equals(" ")) {

			// indicates that a char that isn't a child is typed
			if (curr.getNodes()[str.charAt(str.length() - 1) - 'a'] == null) {

				// keeps track of how many
				charNAs++;
				// AutoCompleteUI.showAdd2Dict();
				return new String[] { "Check", "your", "spelling" };
			}

			// otherwise, jump down to correct child
			curr = curr.getNodes()[str.charAt(str.length() - 1) - 'a'];
		}

		// if we back space and delete a full word, reset curr
		else if (backspace && str.equals("")) {
			curr = root;
			return new String[] { "", "", "" };

			// if we space after a string, reset curr
		} else if (space) {
			curr = root;
			return new String[] { "", "", "" };
		}

		// we backspace onto a previously typed word, move curr down to the end of that
		// string
		else if (backspace && curr == root && str.length() > 0) {
			for (int i = 0; i < str.length(); i++) {
				curr = curr.getNodes()[str.charAt(i) - 'a'];
			}

			// one backspace on a string jumps us up
		} else if (backspace && !str.equals(" ")) {
			curr = curr.jumpUp();
		}

		// return three suggestions
		String[] suggestions = new String[3];
		boolean terminal = false;
		int sugLimit = 10;

		for (int i = 0; i < 3; i++) {

			// small input string gets small suggestions
			if (str.length() == 1) {
				if (rand.nextDouble(0, 100.0) < 50) {
					terminal = true;
				}
				sugLimit = 3;
			}

			// longer strings get longer potential suggestions
			else if (str.length() < 5) {
				if (rand.nextDouble(0, 100.0) < 75) {
					sugLimit = 5;
				} else {
					sugLimit = 7;
				}
			}

			// k keeps track of how far curr went down
			int k = 0;
			String sug = str;

			// if you don't have a word or you do have a word but it has potential children
			while (!curr.isWord() || sug.equals(str)) {

				// stay within suggestion limit and there are words available
				if (sug.length() == sugLimit - 1 && curr.terminalList().size() > 0) {
					terminal = true;
				}

				// if there are characters
				if (!curr.charIndexes().isEmpty()) {

					// make sure there are terminals in case terminal was set
					if (curr.terminalList().isEmpty()) {
						terminal = false;
					}

					// get random character
					curr = this.probableChar2(terminal);

					// build suggestions
					sug += curr.getKey();
					k++;
				} else {

					// no possible suggestions
					return new String[] { "", "", "" };
				}
			}

			// add suggestions and jump curr back to end of string
			suggestions[i] = sug;
			while (k > 0) {
				k--;
				curr = curr.jumpUp();
			}

		}
		return suggestions;
	}

	private TrieNode probableChar2(boolean terminal) {
		ArrayList<Integer> possibles;
		if (terminal) {
			possibles = curr.terminalList();
		} else {
			possibles = curr.charIndexes();
		}

		// adjust frequencies to deal with missing characters
		double adjustedAmount = 0;
		for (Integer i : possibles) {
			adjustedAmount += freqs[i];
		}
		double[] adjusted = new double[possibles.size() + 1];
		adjusted[0] = 0;
		int k = 1;

		// create the new ranges
		for (Integer i : possibles) {
			double hold = (freqs[i] / adjustedAmount) * 100;
			adjusted[k] = adjusted[k - 1] + hold;
			k++;
		}

		k = 0;

		// find a random character
		double randomprob = rand.nextDouble(100);
		for (int i = 1; i < possibles.size() + 1; i++) {

			// if random is between a range, return that TrieNode associated with the
			// character
			if (adjusted[i - 1] <= randomprob && adjusted[i] >= randomprob) {
				return curr.getNodes()[possibles.get(k)];
			}
			k++;
		}
		return null;
	}
}
