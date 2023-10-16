package com.prigby;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

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
    }
   
    public void setQuery(String query) {
        this.query = query;
    }

    public double getLon() {
        return this.lon;
    }

    public double getLat() {
        return this.lat;
    }

    public void parseJSON() throws IOException, URISyntaxException {
        JsonObject jsonObject = new JsonObject();
        String jsonString = "";

        // Set up http request
        URI uri = new URI(this.request);
        HttpURLConnection connection = (HttpURLConnection)uri.toURL().openConnection();
        connection.setRequestMethod("GET");

        // grab http response
        InputStreamReader isReader = new InputStreamReader(connection.getInputStream());
        BufferedReader reader = new BufferedReader(isReader);
       
        // format http response
        String line = "";
        while ((line = reader.readLine()) != null) {
            jsonString += line; 
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
        System.out.println(address + "\n" + position);
    }

    public JsonReader requestJSON() throws IOException, URISyntaxException, InterruptedException {
        JsonReader jsonReader = null;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(getURI()).GET().build();

        client.send(request, BodyHandlers.ofString());
        return jsonReader;
    }

    private URI getURI() throws URISyntaxException {
        return new URI(this.request);
    }

    public void buildRequest() {
        this.request = "https://" + this.baseURL + "/search/" + this.versionNumber + 
                            "/geocode/" + this.query + "." + this.ext + "?key=" + this.key;
    }
}
