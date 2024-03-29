import org.junit.Test;
import static org.junit.Assert.assertTrue;


public class testTrie {

    @Test
    public void testAddWord() {
        AutoCompleteTrie trie = new AutoCompleteTrie();
        trie.addWord("cat");
        trie.addWord("cattle");
        trie.addWord("cactus");
        trie.addWord("cacti");
        trie.addWord("do");

        assertTrue(trie.isWord("cattle"));
        assertTrue(!trie.isWord("done"));
    }

    public static void main(String[] args) {
        AutoCompleteTrie trie = new AutoCompleteTrie();

        trie.addWord("cat");
        trie.addWord("cattle");
        assertTrue(trie.isWord("cattle"));
    }
}