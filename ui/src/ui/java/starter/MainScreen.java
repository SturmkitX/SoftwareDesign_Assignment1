package starter;

import javafx.application.Application;
import javafx.stage.Stage;
import scenes.layouts.LogInPane;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class MainScreen extends Application {
	
	private static Stage stage;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Window title");
		
		setStage(stage);
		
		Pane layout = new LogInPane();
		
		Scene scene = new Scene(layout, 1024, 768);
		stage.setScene(scene);
		stage.show();
		
	}
	
	public static void setScene(Scene scene) {
		stage.setScene(scene);
	}
	
	private static void setStage(Stage arg0) {
		stage = arg0;
	}
	
	public static Stage getStage() {
		return stage;
	}

}
