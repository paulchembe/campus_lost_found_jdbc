package com.example.campuslostfound.api;

public class User {
    public int id;
    public String first_name;
    public String last_name;
    public String email; // optional if you used email in DB
    public String phone;
    public String role;
    public String student_no; // ensure field present if backend returns student_no
    public String created_at;
}
