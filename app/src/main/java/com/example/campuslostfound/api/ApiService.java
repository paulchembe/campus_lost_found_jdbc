package com.example.campuslostfound.api;

import com.example.campuslostfound.models.LoginResponse;
import com.example.campuslostfound.models.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {

    @FormUrlEncoded
    @POST("login.php")
    Call<LoginResponse> loginUser(
            @Field("identifier") String identifier,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("register.php")
    Call<RegisterResponse> registerUser(
            @Field("first_name") String firstName,
            @Field("last_name") String lastName,
            @Field("nrc") String nrc,
            @Field("student_id") String studentId,
            @Field("phone") String phone,
            @Field("password") String password
    );
}
