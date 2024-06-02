package com.example.skylight.models;

import java.util.ArrayList;
import java.util.List;

public class Airline {
    private int id;
    private String name;
    private String country;
    private String founded;
    private String website;
    private List<Destination> destinations;

    public Airline(int id, String name, String country, String founded, List<Destination> destinations, String website) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.founded = founded;
        this.website = website;
        this.destinations = destinations;
    }

    public Airline() {
    }


    public Airline(int id, String title, String country, String founded, String destinations, String website) {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getFounded() {
        return founded;
    }

    public void setFounded(String founded) {
        this.founded = founded;
    }

    public List<Destination> getDestinations() {
        return destinations;
    }

    public void setDestinations(List<Destination> destinations) {
        this.destinations = destinations;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getDestinationString() {
        // Check if destinations is null
        if (destinations == null) {
            return "No destinations available";
        }
        StringBuilder destinationString = new StringBuilder();
        for (Destination destination : destinations) {
            destinationString.append(destination).append(", ");
        }
        // Remove trailing comma and space
        if (destinationString.length() > 0) {
            destinationString.setLength(destinationString.length() - 2);
        }
        return destinationString.toString();
    }

    public void setDestinationString(String destinationString) {
        if (destinationString == null || destinationString.isEmpty()) {
            this.destinations = new ArrayList<>();
            return;
        }

        String[] destinationArray = destinationString.split(", ");
        List<Destination> destinationList = new ArrayList<>();
        for (String destinationName : destinationArray) {
            destinationList.add(new Destination(destinationName));
        }
        this.destinations = destinationList;
    }
}

