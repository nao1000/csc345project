public class AutoCompleteTrie implements Trie {

    private TrieNode root;
    private TrieNode curr;
    private int wordCount;

    public AutoCompleteTrie() {
        root = new TrieNode(' ');
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
            TrieNode newNode = new TrieNode(word.charAt(0));
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
    public String[] autoComplete(String str) {
        return new String[4];
    }

    // returns an array of potential strings that come from a certain input
    // potential strings are relevant to the tone of the current input
    public String[] autoComplete(String str, float sent) {
        return new String[4];
    }
}
