package starters;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import util.UserSession;


public class StartFXML extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        UserSession.setStage(stage);
        Parent root = FXMLLoader.load(getClass().getResource("signin.fxml"));

        Scene scene = new Scene(root);

        stage.setTitle("FXML Welcome");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        UserSession.setFactory("DAO");
        launch(args);
    }
}
