package com.example.campuslostfound.api;

public class ItemPost {
    public int id;             // auto-increment from DB
    public String type;        // LOST or FOUND (from RadioGroup)
    public String title;
    public String description;
    public String category;
    public String location;
    public String date;        // YYYY-MM-DD
    public String contact;
    public String photoUri;    // optional
    public String created_at;  // timestamp returned by backend

    public ItemPost() {}

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
