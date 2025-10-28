package com.example.campuslostfound.api;

import com.example.campuslostfound.models.ApiResponse;
import com.example.campuslostfound.models.ItemPost;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("login.php")
    Call<ApiResponse> loginUser(
            @Field("identifier") String identifier,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("register.php")
    Call<ApiResponse> registerUser(
            @Field("first_name") String firstName,
            @Field("last_name") String lastName,
            @Field("nrc") String nrc,
            @Field("student_no") String studentId,
            @Field("phone") String phone,
            @Field("password") String password
    );

    @GET("getItemsByType.php")
    Call<List<ItemPost>> getItemsByType(@Query("type") String type);

    // ✅ UPDATED: send form-encoded data instead of JSON body
    @FormUrlEncoded
    @POST("createItem.php")
    Call<ApiResponse> createItem(
            @Field("type") String type,
            @Field("title") String title,
            @Field("description") String description,
            @Field("category") String category,
            @Field("location") String location,
            @Field("date") String date,
            @Field("contact") String contact,
            @Field("photoUri") String photoUri
    );

    @FormUrlEncoded
    @POST("markReturned.php")
    Call<ApiResponse> markReturned(@Field("id") long id);
}
