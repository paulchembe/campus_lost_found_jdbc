package com.example.campuslostfound.models;

import com.example.campuslostfound.api.User;

public class ApiResponse {
    public boolean success;
    public String message;
    public Long id;   // for created item id (if backend returns it)
    public User user; // for login
}
