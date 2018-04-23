package starters;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import util.UserSession;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class StartFXML extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        UserSession.setStage(stage);
        Parent root = FXMLLoader.load(getClass().getResource("signin.fxml"));

        Scene scene = new Scene(root);

        stage.setTitle("Sign In");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Scanner scanner = null;
        String connection;
        try {
            scanner = new Scanner(new File("connectionType"));
            connection = scanner.next();
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            connection = "Hibernate";
        }

        UserSession.setFactory(connection);
        launch(args);
    }
}
