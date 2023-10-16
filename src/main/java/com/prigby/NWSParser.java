package com.prigby;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import com.google.gson.stream.JsonReader;

public class NWSParser {
    
    private String temperature;
    private String lat, lon;
    private String nwsForecastURL;
    private String shortForecast;

    public NWSParser(String lat, String lon) throws IOException, URISyntaxException {
        temperature = "";
        nwsForecastURL = "";
        shortForecast = "";
        this.lat = lat;
        this.lon = lon;

        evalutaWeather();
    }

    public void evalutaWeather() throws IOException, URISyntaxException { // sets variables according to forecast of location
        JsonReader jsonReader = fetchGridJSON(lat, lon);
        readJSON(jsonReader);
        jsonReader = fetchForecastJSON(nwsForecastURL);
        readJSON(jsonReader);
    }

    public void setLocation(String lat, String lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public String getTemperature() { // returns temperature for specified location
        return this.temperature;
    }

    public String getShortForecast() {
        return this.shortForecast;
    }
    
    private String getForcastURL() { // returns forecastURL for specified location
        return this.nwsForecastURL;
    }
    
    private void readJSON(JsonReader jsonReader) throws IOException { // general branching logic for reading
        jsonReader.beginObject();                                       // json from nws api

        while (jsonReader.hasNext()) {
            String name = jsonReader.nextName();
            if (name.equals("properties")) {
                readProperties(jsonReader);
            } else {
                jsonReader.skipValue();
            }
        }
    }

    private void readProperties(JsonReader jsonReader) throws IOException { // branch of readJSON
        jsonReader.beginObject();

        while (jsonReader.hasNext()) {
            String name = jsonReader.nextName();
            if (name.equals("forecastHourly")) {
                this.nwsForecastURL = jsonReader.nextString();
            } else if (name.equals("periods")) {
                readPeriods(jsonReader);
            } else {
                jsonReader.skipValue();
            }
        }
    }

    private void readPeriods(JsonReader jsonReader) throws IOException { // branch of readJSON
        jsonReader.beginArray();
        jsonReader.beginObject();

        while (jsonReader.hasNext()) {
            String period = jsonReader.nextName();
            if (period.equals("temperature")) {
                this.temperature = jsonReader.nextString();
            } else if (period.equals("shortForecast")) {
                this.shortForecast =  jsonReader.nextString();
            } else {
                jsonReader.skipValue();
            }
        }
    }

    private JsonReader fetchGridJSON(String lat, String lon) throws IOException, URISyntaxException {   // fetches JSON from nws api
        JsonReader jsonReader;                                                      // for lat-lon location
        URI nwsLocation = new URI("https://api.weather.gov/points/" + lat + "," + lon);
        InputStream inputStream = nwsLocation.toURL().openStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        jsonReader = new JsonReader(inputStreamReader);
        return jsonReader;
    }

    private JsonReader fetchForecastJSON(String nwsForecastURL) throws IOException, URISyntaxException {    // fetches forecast JSON for
        JsonReader jsonReader;                                                      // location from nws api
        URI nwsForecast = new URI(nwsForecastURL);
        InputStream inputStream = nwsForecast.toURL().openStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        jsonReader = new JsonReader(inputStreamReader);
        return jsonReader;
    }
}