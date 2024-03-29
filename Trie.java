public interface Trie {

    // Keeps track of how many total words the Trie has stored
    public int totalWords();

    // Add a word to the Trie
    public void addWord(String word);

    // Delete a word from the Trie
    public boolean delWord(String word);
    
    // check if a word is in the Trie
    public boolean isWord(String str);

    // returns an array of potential strings that come from a certain input
    public String[] autoComplete(String str);


    // returns an array of potential strings that come from a certain input
    // potential strings are relevant to the tone of the current input
    public String[] autoComplete(String str, float sent);
}