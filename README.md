An Autocomplete Trie:

The purpose of this project was to explore the Abstract Data Type of a Trie. We wanted to create an application that could try and predict the next character of a string.

This project created a trie with every word in the English dictionary. We also created a GUI which shows the next likely character. These combined create an interactive user interface to show live predictions on what the next letter could be. This project used Visual Studio Code, JavaFX, and an online dictionary for creating the trie. One of the challenges we faced was using JavaFX in an interesting way. We were able to adjust it to our satisfaction after some time.

To use this program you need to make sure to have JavaFX downloaded. Once that has happened, you can download the files. To run the program, you compile the startingPoint.java and start typing away. As you type into the box you should see live updates on the next potential words. Additionally, the testTrie.java has many assert statements to show how the program is working in different ways. To run that code, just hit run and compile. The code is relatively simple to understand. The program is easy to run and make work.

For the FinalTest.java, you need to make sure that the file path is accurate for your computer. You can do this via Eclipse by right-clicking on the download and then copying the file path into where it is read. The FinalTest adds every word in the English dictionary (greater than two characters for convenience) to the trie. It then runs an assert statement on every word to see if it is in the trie. It also deletes every word from the trie also checks to see if it was properly deleted. This proves that our Trie works on a large scale fast and accurately.

Some things to be aware of are that when using the .csv file, you might not need to, but you may need to copy the path on your end into where the file is opened (lines 23 and 66 in FinalTest.java and line 186 in AutoCompleteUI.java). Also, we commented out a UI feature that was in the actual AutoCompleteTrie.java file to make sure that would be able to run on its own. JavaFX will obviously be needed to test out the UI, but if that is not going to be done, the presentation video should clearly show how it works. If using the UI, please only type in lowercase, spacebar, and the backspace button.

Collaborators: Kory Smith, Jay Whitney, and Nathan Oswald

