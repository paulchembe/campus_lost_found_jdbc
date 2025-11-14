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

/**
 * Retrofit API interface for Campus Lost & Found backend endpoints.
 */
public interface ApiService {

    // =====================================================
    // USER AUTHENTICATION
    // =====================================================

    @FormUrlEncoded
    @POST("login.php")
    Call<ApiResponse> login(
            @Field("identifier") String identifier,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("register.php")
    Call<ApiResponse> registerUser(
            @Field("first_name") String firstName,
            @Field("last_name") String lastName,
            @Field("nrc") String nrc,
            @Field("student_no") String studentNo,
            @Field("phone") String phone,
            @Field("password") String password
    );

    // =====================================================
    // ITEMS MANAGEMENT
    // =====================================================

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

    @GET("getItemsByType.php")
    Call<List<ItemPost>> getItemsByType(@Query("type") String type);

    // =====================================================
    // MARK ITEM AS RETURNED
    // =====================================================

    @FormUrlEncoded
    @POST("markReturned.php")
    Call<ApiResponse> markReturned(
            @Field("id") long id
    );
}
