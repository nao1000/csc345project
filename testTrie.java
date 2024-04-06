import org.junit.Test;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

public class testTrie {

    int k = 0;

    @Test
    public void testAddWord() {
        AutoCompleteTrie trie = new AutoCompleteTrie();
        trie.addWord("cat");
        trie.addWord("cattle");
        trie.addWord("cactus");
        trie.addWord("cacti");
        trie.addWord("do");

        trie.addWord("cat");
        trie.addWord("cattle");
        trie.addWord("cactus");
        trie.addWord("cacti");
        trie.addWord("do");

        trie.addWord("cat");
        trie.addWord("cattle");
        trie.addWord("cactus");
        trie.addWord("cacti");
        trie.addWord("do");

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

    public static void main(String[] args) throws IOException {
        testTrie tryMe = new testTrie();
        tryMe.addDict();
        String[] suggs = tryMe.getSugs("lank", false, false);
        for (String str : suggs) {
            System.out.println(str);
        }
        suggs= tryMe.getSugs("lan", true, false);
        for (String str : suggs) {
            System.out.println(str);
        }

    }

    private AutoCompleteTrie act;

    public testTrie() {
        act = new AutoCompleteTrie();
    }

    public void addDict() throws IOException {
        File file = new File("C:/Users/oswal/OneDrive/Documents/School_Folders/csc345/gitrepo345/345workspace/UIwork/src/englishDictionary.csv");
        List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
        lines.stream().forEach(l -> {
            String[] array = l.split(",", 2);
            boolean flag = true;
            if (Character.isLowerCase(array[0].charAt(0))) {
                flag = false;
            } else {
                for (int i = 0; i < array[0].length(); i++) {
                    if (!Character.isLetter(array[0].charAt(i))) {
                        flag = false;
                    }
                }
            }
            if (flag) {
                if (array[0].length() > 1 && !act.isWord(array[0].toLowerCase())) {
                    act.addWord(array[0].toLowerCase());
                    assertTrue(act.isWord(array[0].toLowerCase()));
                }

            }
        });
    }

    public String[] getSugs(String str, boolean backspace, boolean space) {
        return act.autoComplete(str, backspace, space);
    }

    @Test
    public void testaddDictwords() throws IOException {
        AutoCompleteTrie trie = new AutoCompleteTrie();

        File file = new File("englishDictionary.csv");
        List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
        lines.stream().forEach(l -> {
            String[] array = l.split(",", 2);
            boolean flag = true;
            for (int i = 0; i < array[0].length(); i++) {
                if (!Character.isLetter(array[0].charAt(i))) {
                    flag = false;
                }
            }
            if (flag) {
                if (array[0].length() > 1 && !trie.isWord(array[0].toLowerCase())) {

                    trie.addWord(array[0].toLowerCase());
                    assertTrue(trie.isWord(array[0].toLowerCase()));
                }

            }
        });

        // break point for debug to look at trie
        int k = 0;
    }

}