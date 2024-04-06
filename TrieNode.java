import java.util.ArrayList;

public class TrieNode {

    private boolean terminal;
    private char key;
    private TrieNode[] nextChars;
    private int nextCount;
    private TrieNode parent;
    private ArrayList<Integer> terminalIndexes;
    private ArrayList<Integer> charIndexes;

    public TrieNode(char inKey, TrieNode parent) {
        key = inKey;
        nextChars = new TrieNode[26];
        nextCount = 0;
        terminal = false;
        this.parent = parent;
        terminalIndexes = new ArrayList<Integer>();
        charIndexes = new ArrayList<Integer>();
        if (parent != null ) {
            parent.trackChar(inKey);
        }
    }

    public void trackChar(char inKey) {
        charIndexes.add(inKey - 'a');
    }

    public void setTerminal() {
        terminal = true;
        parent.trackTerminal(key);
    }

    public void trackTerminal(char inKey) {
        terminalIndexes.add(inKey - 'a');
    }

    public void unsetTerminal() {
        parent.forgetTerminal(key);
        terminal = false;
    }

    public void forgetTerminal(char inKey) {
        terminalIndexes.remove(inKey - 'a');
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

    public void decCount() {
        nextCount--;
    }

    public TrieNode jumpUp() {
        return parent;
    }

}
