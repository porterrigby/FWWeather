package com.prigby;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import com.google.gson.stream.JsonReader;

public class JSONParser {
    
    private String forecast, forecastString;
    private JsonReader jsonReader;

    public JSONParser() throws IOException {
        forecast = "oops";
        
        URL nwsURL = new URL("https://api.weather.gov/points/38.8894,-77.0352");
        InputStream inputStream = nwsURL.openStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        jsonReader = new JsonReader(inputStreamReader);


        forecastString = readJSON();


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
                result = readProperties();
            } else {
                jsonReader.skipValue();
            }
        }

        return result;
    }

    private String readProperties() throws IOException {
        jsonReader.beginObject();
        String result = "";

        while (jsonReader.hasNext()) {
            String name = jsonReader.nextName();
            if (name.equals("forecast")) {
                result = jsonReader.nextString();
            } else {
                jsonReader.skipValue();
            }
        }

        return result;
    }

    public String getForcastString() {
        return forecastString;
    }
}
