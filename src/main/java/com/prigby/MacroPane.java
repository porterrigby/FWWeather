package com.prigby;

import java.io.IOException;
import java.net.URISyntaxException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
import javafx.scene.text.Font;

public class MacroPane extends VBox {
    
    TomTomParser ttParser;
    NWSParser nwsParser;
    TextField searchBar;
    String lon, lat;
    Label location, temperature, shortForecast;

    public MacroPane() throws IOException, URISyntaxException {
        this.ttParser = new TomTomParser();
        this.setAlignment(Pos.TOP_CENTER);
        this.setHeight(10);

        // set bg color, border rounding(??), and border insets(??)
        setBackground(new Background((
                        new BackgroundFill(Color.DEEPSKYBLUE, CornerRadii.EMPTY, Insets.EMPTY))));

        // fetch TomTom API data (geocode)
        this.ttParser.setQuery("boise-id"); // Temp value for testing. Eventually will use user input.
        this.ttParser.buildRequest();
        this.ttParser.parseJSON();
        this.lon = this.ttParser.getLon() + "";
        this.lat = this.ttParser.getLat() + "";

        // fetch NWS API data (weather)
        this.nwsParser = new NWSParser(lat, lon);
        String temp = this.nwsParser.getTemperature();

        //XXX Top of VBOX
        HBox topBox = new HBox();
        Border topBoxBorder = new Border(new BorderStroke(Color.DEEPPINK, BorderStrokeStyle.NONE, 
                                            CornerRadii.EMPTY, new BorderWidths(10)));
        topBox.setBorder(topBoxBorder);

        this.location = new Label(this.ttParser.getAddress());
        this.location.setAlignment(Pos.TOP_LEFT);
        this.location.setFont(new Font("Helvetica", 28));

        topBox.getChildren().add(location);

        //XXX Middle OF VBOX
        VBox middleBox = new VBox();
        middleBox.setAlignment(Pos.TOP_CENTER);
        Border middleBoxBorder = new Border(new BorderStroke(Color.DEEPPINK, BorderStrokeStyle.NONE, 
                                            CornerRadii.EMPTY, new BorderWidths(10)));
        middleBox.setBorder(middleBoxBorder);

        this.temperature = new Label(temp + "F");
        this.temperature.setAlignment(Pos.CENTER);
        this.temperature.setFont(new Font("Helvetica", 48));

        this.shortForecast = new Label(nwsParser.getShortForecast());
        this.shortForecast.setFont(new Font("Helvetica", 24));
        this.shortForecast.setAlignment(Pos.TOP_CENTER);

        middleBox.getChildren().addAll(temperature, shortForecast);

        //XXX BOTTOM OF VBOX
        HBox bottomBox = new HBox();
        bottomBox.setPrefHeight(200);
        bottomBox.setAlignment(Pos.CENTER);

        Label info = new Label("Enter a location: ");
        info.setFont(new Font("Helvetica", 16));
        info.setAlignment(Pos.CENTER);

        // TODO add functionality to search bar
        EventHandler<ActionEvent> searchBarHandler = (ActionEvent event) -> handleSearchBar(event);
        this.searchBar = new TextField();
        this.searchBar.setOnAction(searchBarHandler);

        this.searchBar.setFont(new Font("Helvetica", 14));
        this.searchBar.setAlignment(Pos.CENTER_LEFT);

        bottomBox.getChildren().addAll(info, searchBar);
        
        // Add Boxes To Pane
        getChildren().addAll(topBox, middleBox, shortForecast, bottomBox);
    }

    // Handles search bar entries
    private void handleSearchBar(ActionEvent event) {
        try {
            updateQuery(this.searchBar.getText());
        } catch (Exception e) {
            System.out.println("Failed to parse.");
            System.out.println("MacroPane.handleSearchBar()");
        }
    }

    private void updateQuery(String query) throws IOException, URISyntaxException {
        this.ttParser.setQuery(query); 
        this.ttParser.buildRequest();
        this.ttParser.parseJSON();
        this.lon = this.ttParser.getLon() + "";
        this.lat = this.ttParser.getLat() + "";
        this.nwsParser.setLocation(lat, lon);
        this.nwsParser.evalutaWeather();
        this.temperature.setText(this.nwsParser.getTemperature() + "F");
        this.shortForecast.setText(this.nwsParser.getShortForecast());
        this.location.setText(this.ttParser.getAddress()); 
    }
}
