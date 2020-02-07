package eu.nanocode.evolutionApp;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javafx.scene.control.*;

public class AlertBox {

    public static void display(String title, String message){
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMaxWidth(250);
        window.setMinHeight(250);

        Label label = new Label(message);
        Button closeButton = new Button("OK");
        closeButton.setOnAction(e-> window.close());

        VBox layout = new VBox(15);
        layout.getChildren().addAll(label,closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.show();
    }
}
