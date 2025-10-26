package com.example.campuslostfound.api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

public class ApiClient {

    // 👇 Change this to your actual server IP or domain
    // 10.0.2.2 = localhost for Android emulator (XAMPP or WAMP)
    private static final String BASE_URL = "http://10.0.2.2/campuslostfound/";

    public static String post(String endpoint, Map<String, String> params) {
        try {
            URL url = new URL(BASE_URL + endpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            // Build form data (x-www-form-urlencoded)
            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (postData.length() != 0) postData.append('&');
                postData.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            }

            // Send request
            try (OutputStream os = conn.getOutputStream()) {
                os.write(postData.toString().getBytes());
            }

            // Read response
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream())
            );
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            conn.disconnect();
            return response.toString();

        } catch (Exception e) {
            // Return error as JSON string
            return "{\"success\":false,\"message\":\"" + e.getMessage() + "\"}";
        }
    }
}
