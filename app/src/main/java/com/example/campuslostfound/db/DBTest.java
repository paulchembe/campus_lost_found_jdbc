package com.example.campuslostfound.db;

import java.sql.Connection;

public class DBTest {
    public static void main(String[] args) {
        Connection conn = DBConnection.getConnection();
        if (conn != null) {
            System.out.println("✅ Test passed: DB connection working!");
        } else {
            System.out.println("❌ Test failed: Could not connect to DB!");
        }
    }
}
