package org.nanocode.evolutionApp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root;
        Scene scene;
        URL url = getClass().getResource("evolutionApp.fxml");
        root = FXMLLoader.load(url);

        primaryStage.setTitle("Evolution");
        scene= new Scene(root, 700, 600);
        scene.getStylesheets().add(getClass().getResource("graph.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();

    }


    public static void main(String[] args) {

        launch(args);
    }
}
