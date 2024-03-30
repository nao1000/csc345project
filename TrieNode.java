public class TrieNode {

    private boolean terminal;
    private String key;
    private TrieNode[] nextChars;
    private int nextCount;

    public TrieNode(String inKey) {
        key = inKey;
        nextChars = new TrieNode[26];
        nextCount = 0;
        terminal = false;
    }

    public void growArray() {
        if (nextCount == nextChars.length) {
            TrieNode[] temp = new TrieNode[nextChars.length * 2];
            for (int i = 0; i < nextChars.length * 2; i++) {
                temp[i] = nextChars[i];
            }
            nextChars = temp;
        }
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

    public String getKey() {
        return key;
    }

    public int getCount() {
        return nextCount;
    }

    public void incrCount() {
        nextCount++;
    }

}
