package com.example.campuslostfound.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(
                    DBConfig.getJdbcUrl(),
                    DBConfig.DB_USER,
                    DBConfig.DB_PASS
            );
            System.out.println("✅ Database connected successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}
