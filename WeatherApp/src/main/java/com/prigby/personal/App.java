package com.prigby.personal;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application{
    public static void main( String[] args ) {
        launch(args);    
    }

    @Override
    public void start(Stage stage) throws Exception {
        // TODO Auto-generated method stub
        VBox vbox = new VBox();
        vbox.setPrefSize(200, 200);

        Scene scene = new Scene(vbox, 400, 400);
        

        stage.setTitle("Weather App");
        stage.setScene(scene);
        stage.show();
    }
}
