package com.example.campuslostfound.db;

public class DBConfig {

    // ⚙️ Database connection details
    // These are used by your PHP backend, NOT directly by Android.
    // Keep them for reference or future backend connection scripts.

    // Hostname or IP of your MariaDB/MySQL server
    public static final String DB_HOST = "localhost"; // or your PC IP if accessed remotely

    // Port number (default MySQL/MariaDB port is 3306, change if needed)
    public static final int DB_PORT = 3306;

    // Database name
    public static final String DB_NAME = "campus_lost_found";

    // Database user credentials
    public static final String DB_USER = "root";
    public static final String DB_PASS = "";

    // ✅ JDBC connection URL (for backend tools, not Android)
    public static String getJdbcUrl() {
        return String.format("jdbc:mariadb://%s:%d/%s", DB_HOST, DB_PORT, DB_NAME);
    }
}
