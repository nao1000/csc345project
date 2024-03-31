import javafx.event.ActionEvent;
//small change
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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

	@Override
	public void start(Stage primaryStage) {
		BorderPane window = new BorderPane();
		GridPane gPane = new GridPane();
		gPane.setHgap(10);
		gPane.setVgap(10);
		gPane.add(textField, 2, 1);
		gPane.add(typeHere, 1, 1);
		gPane.add(suggestions, 1, 2);
		gPane.add(sugs, 2, 2);
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
			if (c.equals("cat")) {
				sugs.setText("cattle, cats, catalyst");
			}
			if (c.equals("pla")) {
				sugs.setText("plane, place, plank");
			}
			if (c.equals("lan")) {
				sugs.setText("landfall, lantern, lanky");
			}
		}
	}
}
