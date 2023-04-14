package com.prigby;

import java.io.IOException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class MacroPane extends VBox {

    public MacroPane() throws IOException {
        this.setAlignment(Pos.CENTER);
        this.setHeight(BASELINE_OFFSET_SAME_AS_HEIGHT);

        // set bg color, border rounding(??), and border insets(??)
        setBackground(new Background((
                        new BackgroundFill(Color.DEEPSKYBLUE, CornerRadii.EMPTY, Insets.EMPTY))));

        // fetch API data
        JSONParser parser = new JSONParser("43.6628", "-116.6879");
        String temp = parser.getTemperature();

        //XXX TOP OF VBOX
        Label temperature = new Label(temp + "F");
        temperature.setAlignment(Pos.CENTER);
        temperature.setStyle(String.format("-fx-font-size: %d", 42));

        //XXX BOTTOM OF VBOX
        HBox hBox = new HBox();
        

        getChildren().addAll(temperature, hBox);
    }
}
