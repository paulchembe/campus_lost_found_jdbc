package com.example.campuslostfound.api;

import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    // For emulator use 10.0.2.2; for phone on same Wi-Fi use your PC IP
    public static final String BASE_URL = "http://10.0.2.2:8080/api/";

    private static Retrofit retrofit;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }


        public static String post(String url, Map<String, String> params) {
            // Your implementation here
            return "response";
        }

}
