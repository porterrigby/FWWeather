package com.prigby;

import java.io.IOException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class MacroPane extends VBox {

    public MacroPane() throws IOException {
        this.setAlignment(Pos.TOP_CENTER);
        this.setHeight(BASELINE_OFFSET_SAME_AS_HEIGHT);

        // set bg color, border rounding(??), and border insets(??)
        setBackground(new Background((
                        new BackgroundFill(Color.DEEPSKYBLUE, CornerRadii.EMPTY, Insets.EMPTY))));

        // fetch API data
        JSONParser parser = new JSONParser("43.6628", "-116.6879");
        String temp = parser.getTemperature();

        //XXX Top of VBOX
        HBox topBox = new HBox();
        Border topBoxBorder = new Border(new BorderStroke(Color.DEEPPINK, BorderStrokeStyle.NONE, 
                                            CornerRadii.EMPTY, new BorderWidths(10)));
        topBox.setBorder(topBoxBorder);

        Label location = new Label("Caldwell, ID");
        location.setAlignment(Pos.TOP_LEFT);
        location.setStyle(String.format("-fx-font-size: " + 22));

        topBox.getChildren().add(location);

        //XXX Middle OF VBOX
        HBox middleBox = new HBox();
        middleBox.setAlignment(Pos.TOP_CENTER);
        Border middleBoxBorder = new Border(new BorderStroke(Color.DEEPPINK, BorderStrokeStyle.NONE, 
                                            CornerRadii.EMPTY, new BorderWidths(10)));
        middleBox.setBorder(middleBoxBorder);

        Label temperature = new Label(temp + "F");
        temperature.setAlignment(Pos.CENTER);
        temperature.setStyle(String.format("-fx-font-size: %d", 42));

        middleBox.getChildren().addAll(temperature);

        //XXX BOTTOM OF VBOX
        HBox bottomBox = new HBox();




        bottomBox.getChildren().addAll();
        
        // Add Boxes To Pane
        getChildren().addAll(topBox, middleBox, bottomBox);
    }
}
