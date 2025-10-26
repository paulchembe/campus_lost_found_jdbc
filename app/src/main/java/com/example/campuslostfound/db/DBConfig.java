package com.example.campuslostfound.db;

public class DBConfig {
    public static final String DB_HOST = "localhost";
    public static final int DB_PORT = 3306;
    public static final String DB_NAME = "campus_lost_found";
    public static final String DB_USER = "root";
    public static final String DB_PASS = "";

    public static String getConnectionUrl() {
        return "jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME;
    }
}