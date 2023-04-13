package com.prigby;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import com.google.gson.stream.JsonReader;

public class JSONParser {
    
    private String forecast, forecastURL, temperature;
    private JsonReader jsonReader;
    private URL nwsURL;

    public JSONParser() throws IOException {
        temperature = "";
        String lat, lon;
        lat = "43.6628";
        lon = "-116.6879";
       
        setURL("https://api.weather.gov/points/" + lat + "," + lon);
        InputStream inputStream = nwsURL.openStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        jsonReader = new JsonReader(inputStreamReader);

        forecast(lat, lon);

        // forecast = readJSON();


        jsonReader.close();
    }

    public void updateForcast() throws IOException {
        forecast = readJSON();
    }

    private String readJSON() throws IOException {
        jsonReader.beginObject();
        String result = "";

        while (jsonReader.hasNext()) {
            String name = jsonReader.nextName();
            if (name.equals("properties")) {
                result = readNames();
            } else {
                jsonReader.skipValue();
            }
        }
        return result;
    }

    private String readNames() throws IOException {
        jsonReader.beginObject();
        String result = "";

        while (jsonReader.hasNext()) {
            String name = jsonReader.nextName();
            if (name.equals("forecast")) {
                result = jsonReader.nextString();
            } else if (name.equals("periods")) {
                result = readPeriods();
            } else {
                jsonReader.skipValue();
            }
        }

        return result;
    }

    private String readPeriods() throws IOException {
        jsonReader.beginObject();
        String result = "";

        while (jsonReader.hasNext()) {
            String period = jsonReader.nextName();
            if (period.equals("0")) {
                result = readPeriodData();
            } else {
                jsonReader.skipValue();
            }
        }

        return result;
    }

    private String readPeriodData() throws IOException {
        jsonReader.beginObject();
        String temperature = "";

        while (jsonReader.hasNext()) {
            String name = jsonReader.nextName();
            if (name.equals("temperature")) {
                temperature = jsonReader.nextString();
            } else {
                jsonReader.skipValue();
            }
        }

        return temperature;
    }

    public void forecast(String lat, String lon) throws IOException {
        setURL("https://api.weather.gov/points/" + lat + "," + lon);
        forecast = readJSON();
        jsonReader.endObject();
        setURL(forecast);
        temperature = readJSON();
        jsonReader.endObject();

    }

    public void setURL(String URLString) throws IOException {
        nwsURL = new URL(URLString);
    }

    public String getForcast() {
        return forecast;
    }

    public String getTemperature() {
        return temperature;
    }
}