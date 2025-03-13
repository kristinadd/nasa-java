package com.example;

import com.fasterxml.jackson.databind.JsonNode;

public class Main {
  public static void main(String[] args) {

    String key = "000000";

    try {
        NasaClient client = new NasaClient(key);
        JsonNode todayImage = client.getImage("", false);
        System.out.println("Today's APOD:\n" + todayImage.toPrettyString());

        JsonNode datedImage = client.getImage("2012-07-07", true);
        System.out.println("APOD for 2012-07-07:\n" + datedImage.toPrettyString());
    } catch (Exception e) {
        e.printStackTrace();
    }
  }
}
