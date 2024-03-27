package com.prigby;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class TomTomParser {
    private String baseURL, versionNumber, query, ext, key;
    private String request, freeFormAddress;
    private double lat, lon;

    public TomTomParser() {
        this.baseURL = "api.tomtom.com";
        this.versionNumber = "2";
        this.query = "";
        this.ext = "json";
        this.key = System.getenv("TOMTOM");
        this.request = "";
        this.freeFormAddress = "--";
    }
   
    public void setQuery(String userQuery) {
        this.query = userQuery;
    }

    public double getLon() {
        return this.lon;
    }

    public double getLat() {
        return this.lat;
    }

    public String getAddress() {
        return this.freeFormAddress;
    }

    public void parseJSON() {
        JsonObject jsonObject = new JsonObject();
        String jsonString = "";

        try {
            // Set up http request
            URI uri = new URI(this.request);
            HttpURLConnection connection = (HttpURLConnection)uri.toURL().openConnection();
            connection.setRequestMethod("GET");

            // grab http response
            InputStreamReader isr = new InputStreamReader(connection.getInputStream());
            BufferedReader br = new BufferedReader(isr);
        
            // format http response
            String line = "";
            while ((line = br.readLine()) != null) {
                jsonString += line; 
            }
        } catch (Exception e) {
            System.out.print("Failed to parse ");
            System.out.println("@ TomTomParser.parseJSON()");
        }
        // navigate json from api and assign properties
        jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
        
        JsonObject address = jsonObject.getAsJsonArray("results").get(0)
                                        .getAsJsonObject().get("address").getAsJsonObject();
        JsonObject position = jsonObject.getAsJsonArray("results").get(0)
                                        .getAsJsonObject().get("position").getAsJsonObject();
       
        this.freeFormAddress = address.get("freeformAddress").getAsString();
        this.lat = Double.parseDouble(position.get("lat").getAsString());
        this.lon = Double.parseDouble(position.get("lon").getAsString());

        // TODO remove later
        System.out.println(freeFormAddress + "\n" + lat + " " + lon);
    }

    public void buildRequest() {
        this.request = "https://" + this.baseURL + "/search/" + this.versionNumber + 
                            "/geocode/" + this.query + "." + this.ext + "?key=" + this.key;
    }
}
