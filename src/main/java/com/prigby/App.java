package com.prigby;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application{
    public static void main( String[] args ) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        MacroPane pane = new MacroPane();
        Scene scene = new Scene(pane, 400, 400);

        stage.setResizable(false);
        stage.setTitle("FWWeather");
        stage.setScene(scene);
        stage.show();
    }
}
