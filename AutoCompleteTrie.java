import java.util.Random;
import java.util.ArrayList;

public class AutoCompleteTrie implements Trie {

	// https://en.wikipedia.org/wiki/Letter_frequency
	// The array is used as a range for when a random double is generated, if the
	// value is between adjusted[i-1]
	// and adjusted[i]... the corresponding nextChar[i] is the probabilistcally
	// choosen next letter
	// The letter 'a' is 8.2% common in English, 'b' is about 1.5. The numbers are
	// just added together to form ranges
	// Refer to wikipedia page to see where the numbers came from
	private double[] adjusted1 = new double[] { 0, 8.2, 9.7, 12.5, 16.8, 29.5, 31.7, 33.7, 39.8, 46.8, 46.95, 47.72,
			51.72, 54.12, 60.82, 68.32, 70.22, 70.32, 76.32, 82.62, 91.72, 94.52, 95.5, 97.9, 98.05, 100.05, 100.12 };

	private double[] freqs = new double[] { 8.2, 1.5, 2.8, 4.3, 12.7, 2.2, 2.0, 6.1, 7.0, 0.15, 0.77, 4.0, 2.4, 6.7,
			7.5, 1.9, 0.095, 6.0, 6.3, 9.1, 2.8, 0.98, 2.4, 0.15, 2.0, 0.074 };

	private Random rand = new Random();
	private TrieNode root;
	private TrieNode curr;
	private int wordCount;
	private int charNAs;

	// constructor for ACT with ' ' TrieNode
	public AutoCompleteTrie() {
		root = new TrieNode(' ', null);
		curr = root;
		wordCount = 0;
		charNAs = 0;
	}

	// Keeps track of how many total words the Trie has stored
	public int totalWords() {
		return wordCount;
	}
	
	public String curr() {
		return String.valueOf(curr.getKey());
	}
	
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
	// Deleting is tricking so nothing is actully deleted per se
	// If "cattle" was in the ACT but we deleted "cat", we'd be also deleting
	// "cattle"
	// Therefore, we unset the terminal saying it is no longer a word and keep the
	// characters
	// there.
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

	// returns an array of potential strings that come from a certain input
	public String[] autoComplete(String str, boolean backspace, boolean space) {
		
		if (str.length() == 1) {
			curr = root;
			curr = curr.getNodes()[str.charAt(0) - 'a'];
		}

		else if (!backspace && !space) {
			if (curr.getNodes()[str.charAt(str.length()-1) - 'a'] == null) {
				charNAs++;
				return new String[] { "Check", "your", "spelling" };
			}
			curr = curr.getNodes()[str.charAt(str.length()-1) - 'a'];
		}
		
		
		else if (backspace && charNAs > 0) {
			charNAs--;
			return new String[] { "Check", "your", "spelling" };
		}
		
		else if (str.length() > 0 && curr == root) {
			for (int i = 0; i < str.length(); i++) {
				curr = curr.getNodes()[str.charAt(i) - 'a'];
			}
			
		}
		
		else if (backspace && charNAs == 0 && curr.jumpUp() != null) { 
			curr = curr.jumpUp();
		}
		
		String[] suggestions = new String[3];
		boolean terminal = false;
		int sugLimit = 10;
		// if user hits the spacebar, reset curr

		if (space || str.length() == 0) {
			curr = root;
			return new String[] { "", "", "" };
		}

//		if (!backspace) {
//			if (curr.getNodes()[str.charAt(str.length() - 1) - 'a'] == null) {
//				return suggestions;
//			}
//			curr = curr.getNodes()[str.charAt(str.length() - 1) - 'a'];
//		}

		for (int i = 0; i < 3; i++) {
			if (str.length() == 1) {
				if (rand.nextDouble(0, 100.0) < 50) {
					terminal = true;
				}
				sugLimit = 3;
			}

			if (str.length() < 5) {
				if (rand.nextDouble(0, 100.0) < 75) {
					sugLimit = 5;
				} else {
					sugLimit = 7;
				}
			}
			int k = 0;
			String sug = str;
			while (!curr.isWord() || sug.equals(str)) {
				if (sug.length() == sugLimit - 1 && curr.terminalList().size() > 0) {
					terminal = true;
				}
				
				if (! curr.charIndexes().isEmpty()) {
					if (curr.terminalList().isEmpty()) {
						terminal = false;
					}
					curr = this.probableChar2(terminal);
					sug += curr.getKey();
					k++;
				}
				else {
					suggestions = new String[] { "Check", "your", "spelling" };
					break;
				}
			}
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
		double adjustedAmount = 0;
		for (Integer i : possibles) {
			adjustedAmount += freqs[i];
		}
		double[] adjusted = new double[possibles.size() + 1];
		adjusted[0] = 0;
		int k = 1;
		for (Integer i : possibles) {
			double hold = (freqs[i] / adjustedAmount) * 100;
			adjusted[k] = adjusted[k - 1] + hold;
			k++;
		}

		k = 0;

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

	// don't delete, just pushed away, scratch work
	// probabilistic random character generator
	// Method takes into account the frequency of each character in English and
	// determines
	// which path to follow and what word to build
	// private TrieNode probableChar() {

	// // get a random double
	// double randomprob = rand.nextDouble(0, 100.121);

	// // iterate through ranges
	// for (int i = 1; i < 27; i++) {

	// // if random is between a range, return that TrieNode associated with the
	// character
	// if (adjusted1[i - 1] <= randomprob && adjusted1[i] >= randomprob) {
	// if (curr.getNodes()[i - 1] == null) {
	// for (int k = 0; k < 26; k++) {
	// if (curr.getNodes()[k] != null) {
	// return curr.getNodes()[k];
	// }
	// }
	// }
	// return curr.getNodes()[i - 1];
	// }
	// }
	// return null;
	// }
}
