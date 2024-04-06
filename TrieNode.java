import java.util.ArrayList;
import java.util.Collections;

public class TrieNode {

    private boolean terminal;                          // whether node indicates a word
    private char key;                                  // the character of the node
    private TrieNode[] nextChars;                      // array of all potential characters following the curr
    private int nextCount;                             // how many potential chars there are
    private TrieNode parent;                           // the previous letter in the string
    private ArrayList<Integer> terminalIndexes;        // array of all the indices of terminals following cur (used for probability)
    private ArrayList<Integer> charIndexes;            // indicies of all possible chars following curr (used for probability)

    public TrieNode(char inKey, TrieNode parent) {
        key = inKey;
        nextChars = new TrieNode[26];                  // only storing lowercase letters, so only need 26 spots
        nextCount = 0;
        terminal = false;
        this.parent = parent;
        terminalIndexes = new ArrayList<Integer>();
        charIndexes = new ArrayList<Integer>();

        // no parent for the root
        if (parent != null ) {
            parent.trackChar(inKey);
        }
    }

    // keep index
    public void trackChar(char inKey) {
        charIndexes.add(inKey - 'a');
        Collections.sort(charIndexes);
    }

    // word is made
    public void setTerminal() {
        terminal = true;
        parent.trackTerminal(key);
    }

    // keep track of where the word is for the parent
    public void trackTerminal(char inKey) {
        terminalIndexes.add(inKey - 'a');
        Collections.sort(terminalIndexes);
    }

    // used when a word is being removed
    public void unsetTerminal() {
        parent.forgetTerminal(key);
        terminal = false;
    }

    // remove the index
    public void forgetTerminal(char inKey) {
        terminalIndexes.remove(inKey - 'a');
    }

    // getter for terminal
    public boolean isWord() {
        return terminal;
    }

    // getter for TrieNode character array
    public TrieNode[] getNodes() {
        return nextChars;
    }

    // getter for character key
    public char getKey() {
        return key;
    }

    // getter for children count
    public int getCount() {
        return nextCount;
    }

    // increase count
    public void incrCount() {
        nextCount++;
    }

    // decrease count
    public void decCount() {
        nextCount--;
    }

    public ArrayList<Integer> terminalList() {
        return terminalIndexes;
    }
    
    public ArrayList<Integer> charIndexes() {
        return charIndexes;
    }

    // jump up to the parent (used for backtracking)
    public TrieNode jumpUp() {
        return parent;
    }

}
