import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

public class FinalTest {
	
	// This test adds EVERY word in the English dictionary to our Trie and then
	// checks to see if that word was actually added
	@Test
	public void testAddWord() throws IOException {
		AutoCompleteTrie trie = new AutoCompleteTrie();
		// This stores the File path into the file
		File file = new File("/Users/jaywhitney/eclipse-workspace/projectfolder/src/englishDictionary.csv");

		// This creates a BufferedReader for the file
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String st;
			boolean flag = true;
			// This iterates through the dictionary file line by line
			while ((st = br.readLine()) != null) {
				// Splits by the commas and then on the hyphen
				String[] test = st.split(",");
				test = test[0].split("-");

				// This checks to see if there is any not alphabetical characters in the string
				for (int i = 0; i < test[0].length(); i++) {
					if (!Character.isLetter(test[0].charAt(i))) {
						flag = false;
					}
				}
				// If all numeric add to the trie
				if (flag) {
					if (test[0].length() > 1 && !trie.isWord(test[0].toLowerCase())) {
						trie.addWord(test[0].toLowerCase());
						assertTrue(trie.isWord(test[0].toLowerCase()));
					}
				}
			}
			// Catches for the in case the file is not found
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// This test deletes EVERY word in the english dictionary from our trie. It
	// uses an assert statement to make sure it was deleted
	@Test
	public void testDeleteWord() throws IOException {
		AutoCompleteTrie trie = new AutoCompleteTrie();
		// The file path
		File file = new File("/Users/jaywhitney/eclipse-workspace/projectfolder/src/englishDictionary.csv");

		// This gets a BufferedReader for the file
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String st;
			boolean flag = true;
			// This iterates through the dictionary line by line
			while ((st = br.readLine()) != null) {
				String[] test = st.split(",");
				test = test[0].split("-");

				// Checks to see if there is any non alphabetical characters in it
				for (int i = 0; i < test[0].length(); i++) {
					if (!Character.isLetter(test[0].charAt(i))) {
						flag = false;
					}
				}
				// If non alphabetical delete from the trie
				if (flag) {
					if (test[0].length() > 1 && !trie.isWord(test[0].toLowerCase())) {

						trie.delWord(test[0].toLowerCase());
						assertFalse(trie.isWord(test[0].toLowerCase()));
					}
				}
			}
			// Catch statements for if the file is not found
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
