package com.prigby;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
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
    private String baseURL, versionNumber, query, ext, key, request;
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
    
    public void setLatLon(JsonReader jsonReader) throws IOException {
        // TODO
    }

    public double getLon() {
        // TODO
        return 0;
    }

    public double getLat() {
        // TODO
        return 0;
    }

    public void parseJSON() throws IOException, URISyntaxException {
        JsonObject jsonObject = new JsonObject();
        String jsonString = "";

        URI uri = new URI(this.request);
        HttpURLConnection connection = (HttpURLConnection)uri.toURL().openConnection();
        connection.setRequestMethod("GET");

        InputStreamReader isReader = new InputStreamReader(connection.getInputStream());
        BufferedReader reader = new BufferedReader(isReader);
        
        String line = "";
        while ((line = reader.readLine()) != null) {
            jsonString += line; 
        }

        // TODO Porperly access elements
        jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
        System.out.println(jsonObject.getAsJsonObject("results").getAsJsonArray("position"));
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
