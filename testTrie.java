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

        assertTrue(trie.isWord("cat"));
        assertTrue(trie.isWord("cacti"));
       
        assertTrue(trie.isWord("do"));
        assertTrue(!trie.isWord("done"));
        assertTrue(!trie.isWord("at"));
        assertTrue(!trie.isWord("don"));
        assertTrue(trie.isWord("cattle"));
        assertTrue(!trie.isWord("ready"));

        trie.delWord("cattle");
        assertTrue(!trie.isWord("cattle"));
        assertTrue(trie.isWord("cat"));
    }

    public static void main(String[] args) {
        AutoCompleteTrie trie = new AutoCompleteTrie();

        trie.addWord("cat");
        trie.addWord("cattle");
        trie.addWord("cactus");
        trie.addWord("cacti");
        trie.addWord("do");
        assertTrue(trie.isWord("cat"));
        System.out.print("leo");
        //assertTrue(trie.isWord("cattle"));
    }
}