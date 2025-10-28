package com.example.campuslostfound.db;

import com.example.campuslostfound.api.ItemPost;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class DBConfig {


    // <-- UPDATE THESE BEFORE RUNNING THE APP -->
    public static final String DB_HOST = "DESKTOP-221LS3N"; // your MariaDB host (IP or hostname)
    public static final int DB_PORT = 3308;
    public static final String DB_NAME = "campus_lost_found";
    public static final String DB_USER = "root@localhost";
    public static final String DB_PASS = "don";

    // JDBC URL
    public static String getJdbcUrl() {
        return String.format("jdbc:mariadb://%s:%d/%s", DB_HOST, DB_PORT, DB_NAME);
    }
}
