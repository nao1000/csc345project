import javafx.event.ActionEvent;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Currency;
import java.util.List;

//small change
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class startingPoint extends Application {

	public static void main(String[] args) throws Exception {
		launch(args);
	}

	private TextField textField = new TextField();
	private Label sugs = new Label();
	private Label suggestions = new Label("Suggestions");
	private Label typeHere = new Label("Type Here");
	private startingPoint sp;

	private Label sanity = new Label("Why");
	private Label check = new Label("Fuck");

	@Override
	public void start(Stage primaryStage) throws IOException {

		sp = new startingPoint();
		sp.addDict();
		BorderPane window = new BorderPane();
		GridPane gPane = new GridPane();
		gPane.setHgap(10);
		gPane.setVgap(10);
		gPane.add(textField, 2, 1);
		gPane.add(typeHere, 1, 1);
		gPane.add(suggestions, 1, 2);
		gPane.add(sugs, 2, 2);
		gPane.add(sanity, 4, 1);
		gPane.add(check, 5, 7);
		window.setCenter(gPane);
		Scene scene = new Scene(window, 640, 480);
		textField.setOnKeyReleased(new TypeThis());
		primaryStage.setScene(scene);
		primaryStage.setTitle("Default");
		primaryStage.show();
	}

	private class TypeThis implements EventHandler<KeyEvent> {

		@Override
		public void handle(KeyEvent ae) {
			String c = textField.getText();
			boolean backspace = false;
			boolean space = false;

			if (ae.getCode() == KeyCode.BACK_SPACE) {
				backspace = true;
			}

			if (ae.getCode() == KeyCode.SPACE) {
				space = true;
			}
			String suggestions = "";

			if (c.length() != 0) {
				if (!backspace && c.length() != 0) {
					c = " " + c;
				}
				for (String item : sp.getSugs(c.substring(c.lastIndexOf(" ") + 1), backspace, space)) {

					suggestions += item + ", ";

				}
			} else if ((c.lastIndexOf(" ") == c.length() - 1 || backspace) && c.length() - 1 != -1) {
				for (String item : sp.getSugs("", backspace, space)) {

					suggestions += item + ", ";

				}
			}
			sanity.setText(act.curr());
			sugs.setText(suggestions);
		}
	}

	private AutoCompleteTrie act = new AutoCompleteTrie();

	public startingPoint() throws IOException {
		addDict();
	}

	public void addDict() throws IOException {
		String vowels = "AaEeIiOoUu";
		File file = new File(
				"C:/Users/oswal/OneDrive/Documents/School_Folders/csc345/gitrepo345/345workspace/ImportProject/src/englishDictionary.csv");
		List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
		lines.stream().forEach(l -> {
			String[] array = l.split(",");
			boolean flag = true;
			int vowCount = 0;
			for (int i = 0; i < array[0].length(); i++) {
				if (!Character.isLetter(array[0].charAt(i))) {
					flag = false;
				}
				if (vowels.indexOf(array[0].charAt(i)) != -1) {
					vowCount++;
				}
			}

			if (flag && vowCount > 0) {
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
}
