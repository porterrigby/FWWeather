package com.prigby.personal;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class App {
    public static void main( String[] args ) {
        Stage stage = new Stage();
        Pane pane = new Pane();
        Scene scene = new Scene(pane);

        pane.setPrefSize(200, 200);
        pane.getChildren();
        
        
        stage.setScene(scene);
        stage.setTitle("Weather App");
        stage.show();
    }
}
