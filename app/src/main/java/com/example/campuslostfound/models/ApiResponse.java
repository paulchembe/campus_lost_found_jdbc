package com.example.campuslostfound.models;

import com.example.campuslostfound.api.User;

public class ApiResponse {
    public boolean success;
    public String message;
    public Long id;   // For created item ID (if backend returns it)
    public User user; // For login responses

    // ✅ Optional: Add getter and setter methods for convenience
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
