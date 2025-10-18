package com.example.campuslostfound.db;

public class DBConfig {
    // <-- UPDATE THESE BEFORE RUNNING THE APP -->
    public static final String DB_HOST = "192.168.0.100"; // your MariaDB host (IP or hostname)
    public static final int DB_PORT = 3306;
    public static final String DB_NAME = "campus_lost_found";
    public static final String DB_USER = "api_user";
    public static final String DB_PASS = "strongpassword";

    // JDBC URL
    public static String getJdbcUrl() {
        return String.format("jdbc:mariadb://%s:%d/%s", DB_HOST, DB_PORT, DB_NAME);
    }
}
