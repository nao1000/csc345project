import java.util.Random;

public class AutoCompleteTrie implements Trie {

    private Random rand = new Random();
    private double[] adjusted = new double[] {0,8.2, 9.7, 12.5, 16.8, 29.5, 31.7, 33.7, 39.8,
         46.8, 46.95, 47.72, 51.72, 54.12, 60.82, 68.32, 70.22, 70.32, 76.32, 82.62, 91.72, 94.52, 95.5, 97.9, 98.05, 100.05, 100.12};




    private TrieNode root;
    private TrieNode curr;
    private int wordCount;

    public AutoCompleteTrie() {
        root = new TrieNode(' ', null);
        curr = root;
        wordCount = 0;
    }

    // Keeps track of how many total words the Trie has stored
    public int totalWords() {
        return wordCount;
    }

    // Add a word to the Trie
    public void addWord(String word) {
        if (word.length() == 0) {
            curr.setTerminal();
            curr = root;
            return;
        }
        TrieNode[] nextChars = curr.getNodes();
        if (nextChars[word.charAt(0) - 'a'] == null) {
            TrieNode newNode = new TrieNode(word.charAt(0), curr);
            nextChars[word.charAt(0) - 'a'] = newNode;
            curr = newNode;
        } else {
            curr = nextChars[word.charAt(0) - 'a'];
        }
        addWord(word.substring(1, word.length()));

    }

    // Delete a word from the Trie
    public boolean delWord(String word) {
        if (word.length() == 0 && curr.isWord()) {
            curr.unsetTerminal();
            curr = root;
            return true;
        }
        else if (word.length() == 0 && !curr.isWord()) {
                curr = root;
                return false;
            }
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
        if (word.length() == 0 && curr.isWord()) {
            curr = root;
            return true;
        }
        else if (word.length() == 0 && !curr.isWord()) {
            curr = root;
            return false;
        }
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
        
        String[] suggestions = new String[3];
        // if user hits the spacebar, reset curr
        if (space) {
            curr = root;
        }

        // if user hits backspace, go up to parent
        if (backspace && curr.jumpUp() != null) {
            curr = curr.jumpUp();
        }

        // suggest smaller words intially, "h" -> "hi", "hey"
        //                                 "a" -> "an", "am"
        if (str.length() == 1) {
            String lowered = str.toLowerCase();
            curr = curr.getNodes()[lowered.charAt(0) - 'a'];
            for (int i = 0; i<3; i++) {
                int k = 0;
                String sug = str;
                while (!curr.isWord() && k < 2) {
                    curr = this.probableChar();
                    sug += curr.getKey();
                    k++;
                }
                suggestions[i] = sug;
                while (k > 0) {
                    k--;
                    curr = curr.jumpUp();
                }

            }
        }

        return suggestions;
    }

    private TrieNode probableChar() {

        double randomprob = rand.nextDouble(0,100.121);
        for (int i = 1; i < 27; i++) {
            if (adjusted[i-1] <= randomprob && adjusted[i] >= randomprob) {
                if (curr.getNodes()[i-1] == null) {
                    for (int k = 0; k < 26; k ++) {
                        if (curr.getNodes()[k] != null) {
                            return curr.getNodes()[k];
                        }
                    }
                }
                return curr.getNodes()[i-1];
            }
        }
        return null;
    }
}
