import org.junit.Test;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;


public class testTrie {




    
    @Test
    public void testAddWord() {
        AutoCompleteTrie trie = new AutoCompleteTrie();
        trie.addWord("cat");
        trie.addWord("cattle");
        trie.addWord("cactus");
        trie.addWord("cacti");
        trie.addWord("do");

        trie.addWord("1");
        trie.addWord("2");
        trie.addWord("3");
        trie.addWord("4");
        trie.addWord("5");

        trie.addWord("6");
        trie.addWord("7");
        trie.addWord("8");
        trie.addWord("9");
        trie.addWord("10");

        trie.addWord("cat");
        trie.addWord("cattle");
        trie.addWord("cactus");
        trie.addWord("cacti");
        trie.addWord("do");

        trie.addWord("1");
        trie.addWord("2");
        trie.addWord("3");
        trie.addWord("4");
        trie.addWord("5");

        trie.addWord("6");
        trie.addWord("7");
        trie.addWord("8");
        trie.addWord("9");
        trie.addWord("10");
        trie.addWord("cat");
        trie.addWord("cattle");
        trie.addWord("cactus");
        trie.addWord("cacti");
        trie.addWord("do");

        trie.addWord("1");
        trie.addWord("2");
        trie.addWord("3");
        trie.addWord("4");
        trie.addWord("5");

        trie.addWord("6");
        trie.addWord("7");
        trie.addWord("8");
        trie.addWord("9");
        trie.addWord("10");
        trie.addWord("cat");
        trie.addWord("cattle");
        trie.addWord("cactus");
        trie.addWord("cacti");
        trie.addWord("do");

        trie.addWord("1");
        trie.addWord("2");
        trie.addWord("3");
        trie.addWord("4");
        trie.addWord("5");

        trie.addWord("6");
        trie.addWord("7");
        trie.addWord("8");
        trie.addWord("9");
        trie.addWord("10");


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


    // BEWARE!!!
    // This mf almost blew up my computer
    @Test
    public void testDictwords() throws IOException{
        File file = new File("englishDictionary.csv");
        List<String> lines = Files.readAllLines(file.toPath(),StandardCharsets.UTF_8);
        lines.stream().forEach(l -> {
            String[] array =  l.split(",", 2);
                System.out.println(array[0]);
        });
    }

    @Test
    public void testaddDictwords() throws IOException{
        AutoCompleteTrie trie = new AutoCompleteTrie();
        File file = new File("englishDictionary.csv");
        List<String> lines = Files.readAllLines(file.toPath(),StandardCharsets.UTF_8);
        lines.stream().forEach(l -> {
            String[] array =  l.split(",", 2);
                // System.out.println(array[0]);
                trie.addWord(array[0]);
        });
    }

}