/*
 * Authors: Nathan Oswald, Jay Whitney, Kory Smith
 * 
 * Description:
 * 				This application creates a UI where the user can type
 * 				and be given suggestions for the next words to type
 * 				in real time.
 */

import javafx.event.ActionEvent;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

//small change
import javafx.application.Application;
import javafx.event.EventHandler;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AutoCompleteUI extends Application {

	public static void main(String[] args) throws Exception {
		launch(args);
	}

	// set the varioues fields
	private TextField textField = new TextField();
	private Label sugs = new Label();
	private Label suggestionBox = new Label("Suggestions");
	private Label typeHere = new Label("Type Here");
	private Label completedText = new Label("Text Terminal");
	private Label completed = new Label();
	private static Label notInDict = new Label("The current word is not in the dictionary.");

	private AutoCompleteUI acUI;

	@Override
	public void start(Stage primaryStage) throws IOException {

		// set up the interface
		Font font = new Font("Gabriola", 24);
		acUI = new AutoCompleteUI();
		acUI.addDict();
		BorderPane window = new BorderPane();
		GridPane gPane = new GridPane();
		gPane.setHgap(10);
		gPane.setVgap(10);
		typeHere.setFont(font);
		suggestionBox.setFont(font);
		completedText.setFont(font);
		gPane.add(typeHere, 0, 0);
		gPane.add(textField, 1, 0);
		textField.setPrefHeight(150);
		textField.setMaxWidth(150);
		gPane.add(suggestionBox, 0, 1);
		gPane.add(notInDict, 1, 2);
		notInDict.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");

		notInDict.setVisible(false);

		gPane.add(sugs, 1, 1);
		sugs.setPrefWidth(150);
		sugs.setPrefHeight(75);
		sugs.setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-border-style: solid;");
		gPane.add(completedText, 0, 10);
		gPane.add(completed, 1, 10);
		completed.setPrefWidth(500);
		completed.setPrefHeight(75);
		completed.setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-border-style: solid;");

		window.setCenter(gPane);
		Scene scene = new Scene(window, 640, 480);

		window.setStyle("-fx-background-color: lightblue;");

		// Allow for a text field to type in that will handle the event and retrieve
		// suggestions
		textField.setOnKeyReleased(new TypeThis());
		primaryStage.setScene(scene);
		primaryStage.setTitle("Default");
		primaryStage.show();

	}

	private class TypeThis implements EventHandler<KeyEvent> {

		@Override
		public void handle(KeyEvent ae) {

			// get the string from the text field
			String c = textField.getText();
			completed.setText(c);
			c = c.toLowerCase();

			boolean backspace = false;
			boolean space = false;

			// note if there is backspace pressed
			if (ae.getCode() == KeyCode.BACK_SPACE) {
				backspace = true;
			}

			// note if there is a space pressed
			if (ae.getCode() == KeyCode.SPACE) {
				space = true;
			}

			String suggestions = "";

			// if there was a space, we just run the getSugs to set it properly (new string
			// will be typed)
			if (c.length() == 0 || space) {
				acUI.getSugs(" ", backspace, space);

				// first thing to be typed
			} else if (c.lastIndexOf(" ") == -1) {
				for (String item : acUI.getSugs(c, backspace, space)) {
					suggestions += item + " \n";
				}

				// any word after
			} else {
				for (String item : acUI.getSugs(c.substring(c.lastIndexOf(" ") + 1), backspace, space)) {
					suggestions += item + " \n";
				}
			}

			// set the suggestions
			sugs.setText(suggestions);
		}
	}

	private AutoCompleteTrie act = new AutoCompleteTrie();

	public AutoCompleteUI() throws IOException {
		addDict();
	}

	public void addDict() {
		// This stores the File path into the file
		File file = new File(
				"C:/Users/oswal/OneDrive/Documents/School_Folders/csc345/gitrepo345/345workspace/ImportProject/src/englishDictionary.csv");
		// This creates a BufferedReader for the file
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String st;

			// This iterates through the dictionary file line by line
			while ((st = br.readLine()) != null) {
				boolean flag = true;
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
					if (test[0].length() > 1 && !act.isWord(test[0].toLowerCase())) {
						act.addWord(test[0].toLowerCase());
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

	// return the suggestions by calling the autoComplete method
	public String[] getSugs(String str, boolean backspace, boolean space) {
		return act.autoComplete(str, backspace, space);
	}

	public static void hideAdd2Dict() {
		notInDict.setVisible(false);
	}

	public static void showAdd2Dict() {
		notInDict.setVisible(true);
	}

}