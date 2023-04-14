package com.prigby;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import com.google.gson.stream.JsonReader;

public class JSONParser {
    
    private String temperature;
    private String lat, lon;
    private String nwsForecastURL;

    public JSONParser(String lat, String lon) throws IOException {
        temperature = "";
        nwsForecastURL = "";
        this.lat = "43.6628";
        this.lon = "-116.6879";

        evalutaWeather();
    }

    public void evalutaWeather() throws IOException {
        JsonReader jsonReader = fetchGridJSON(lat, lon);
        nwsForecastURL = readJSON(jsonReader);
        jsonReader = fetchForecast(nwsForecastURL);
        temperature = readJSON(jsonReader);
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getForcastURL() {
        return nwsForecastURL;
    }

    public String getTemperature() {
        return temperature;
    }
    
    private String readJSON(JsonReader jsonReader) throws IOException {
        jsonReader.beginObject();
        String result = "";

        while (jsonReader.hasNext()) {
            String name = jsonReader.nextName();
            if (name.equals("properties")) {
                result = readProperties(jsonReader);
            } else {
                jsonReader.skipValue();
            }
        }
        return result;
    }

    private String readProperties(JsonReader jsonReader) throws IOException {
        jsonReader.beginObject();
        String result = "";

        while (jsonReader.hasNext()) {
            String name = jsonReader.nextName();
            if (name.equals("forecast")) {
                result = jsonReader.nextString();
                return result;
            } else if (name.equals("periods")) {
                result = readPeriods(jsonReader);
                return result;
            } else {
                jsonReader.skipValue();
            }
        }

        return "-2";
    }

    private String readPeriods(JsonReader jsonReader) throws IOException {
        jsonReader.beginArray();
        jsonReader.beginObject();
        String result = "";

        while (jsonReader.hasNext()) {
            String period = jsonReader.nextName();
            if (period.equals("temperature")) {
                result = jsonReader.nextString();
                return result;
            } else {
                jsonReader.skipValue();
            }
        }

        return "-3";
    }

    private JsonReader fetchGridJSON(String lat, String lon) throws IOException {
        JsonReader jsonReader;
        URL nwsLocation = new URL("https://api.weather.gov/points/" + lat + "," + lon);
        InputStream inputStream = nwsLocation.openStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        jsonReader = new JsonReader(inputStreamReader);
        return jsonReader;
    }

    private JsonReader fetchForecast(String nwsForecastURL) throws IOException {
        JsonReader jsonReader;
        URL nwsForecast = new URL(nwsForecastURL);
        InputStream inputStream = nwsForecast.openStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        jsonReader = new JsonReader(inputStreamReader);
        return jsonReader;
    }
}