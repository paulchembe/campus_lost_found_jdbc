package com.example.campuslostfound.models;

import java.io.Serializable;

public class ItemPost {
    public String type;
    public String title;
    public String description;
    public String category;
    public String location;
    public String date;       // Send as YYYY-MM-DD
    public String contact;
    public String photoUri;

    // Optional: default constructor
    public ItemPost() {}

    // Optional: constructor with all fields
    public ItemPost(String type, String title, String description, String category,
                    String location, String date, String contact, String photoUri) {
        this.type = type;
        this.title = title;
        this.description = description;
        this.category = category;
        this.location = location;
        this.date = date;
        this.contact = contact;
        this.photoUri = photoUri;
    }
}
