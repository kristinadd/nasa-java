package com.example;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class NasaClient {
    private static final String BASE_URL = "https://api.nasa.gov/planetary/apod";
    private final String apiKey;
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public NasaClient(String apiKey) {
        this.apiKey = apiKey;
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    public JsonNode getImage(String date, boolean hd) throws IOException, InterruptedException {
        // Build query params
        StringBuilder query = new StringBuilder("api_key=" + URLEncoder.encode(apiKey, StandardCharsets.UTF_8));
        query.append("&hd=").append(hd);

        if (date != null && !date.isEmpty()) {
            query.append("&date=").append(URLEncoder.encode(date, StandardCharsets.UTF_8));
        }

        // Construct the full URL
        String fullUrl = BASE_URL + "?" + query;

        // Build and send request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(fullUrl))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return objectMapper.readTree(response.body());
        } else {
            throw new RuntimeException("Request failed with status " + response.statusCode() + ": " + response.body());
        }
    }
}
