public class TrieNode {

    private boolean terminal;
    private char key;
    private TrieNode[] nextChars;
    private int nextCount;
    private TrieNode parent;

    public TrieNode(char inKey, TrieNode parent) {
        key = inKey;
        nextChars = new TrieNode[26];
        nextCount = 0;
        terminal = false;
        this.parent = parent;
    }

    public void setTerminal() {
        terminal = true;
    }

    public void unsetTerminal() {
        terminal = false;
    }

    public boolean isWord() {
        return terminal;
    }

    public TrieNode[] getNodes() {
        return nextChars;
    }

    public char getKey() {
        return key;
    }

    public int getCount() {
        return nextCount;
    }

    public void incrCount() {
        nextCount++;
    }

    public TrieNode jumpUp() {
        return parent;
    }

}
