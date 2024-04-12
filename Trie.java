public interface Trie {

    // Add a word to the Trie
    public void addWord(String word);

    // Delete a word from the Trie
    public boolean delWord(String word);
    
    // check if a word is in the Trie
    public boolean isWord(String str);

    // returns an array of potential strings that come from a certain input
    public String[] autoComplete(String str, boolean backspace, boolean enter);    
}
