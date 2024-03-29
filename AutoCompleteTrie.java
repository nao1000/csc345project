public class AutoCompleteTrie implements Trie {

    private TrieNode root;
    private TrieNode curr;
    private int wordCount;

    public AutoCompleteTrie() {
        root = new TrieNode("");
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
        TrieNode[] nodeArr = curr.getNodes();
        for (TrieNode node : nodeArr) {
            if (node == null) {
                break;
            }
            if (word.substring(0, 1).equals(node.getKey())) {
                this.moveCurr(node);
                if (word.length() == 1){ 
                    addWord("");
                }
                else {
                    addWord(word.substring(1, word.length()));
                }
            }
        }

        TrieNode newWord = new TrieNode(word.substring(0, 1));
        int index = curr.getCount();
        nodeArr[index] = newWord;
        curr.incrCount();
        curr = newWord;
        if (word.length() == 1){ 
            addWord("");
        }
        else {
            addWord(word.substring(1, word.length()));
        }

    }

    // Delete a word from the Trie
    public boolean delWord(String word) {
        return true;
    }

    public void moveCurr(TrieNode newCurr) {
        curr = newCurr;
    }
    
    // check if a word is in the Trie
    public boolean isWord(String word) {
        if (word.length() == 0 && curr.isWord()) {
            return true;
        }

        TrieNode[] nodeArr = curr.getNodes();
        for (TrieNode node : nodeArr) {
            if (word.substring(0, 1).equals(curr.getKey())) {
                curr = node;
                if (word.length() == 1){ 
                    this.isWord("");
                }
                else {
                    this.isWord(word.substring(1, word.length()));
                }
                
            }
        }

        return false;
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
