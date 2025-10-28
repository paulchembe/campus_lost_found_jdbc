package com.example.campuslostfound.db;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.campuslostfound.api.ApiInterface;
import com.example.campuslostfound.models.ApiResponse;
import com.example.campuslostfound.models.ItemPost;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DBHelper {

    // 🔗 Change this to your local API URL (for emulator use 10.0.2.2)
    private static final String BASE_URL = "http://10.0.2.2/campuslostfound/";
    // 👉 For a physical device, use your PC's IP:
    // private static final String BASE_URL = "http://192.168.x.x/campuslostfound/";

    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private static final ApiInterface api = retrofit.create(ApiInterface.class);

    // ---- Callback Interfaces ----
    public interface LoginCallback {
        void onResult(boolean ok, String message);
    }

    public interface InsertCallback {
        void onInserted(boolean ok, long id);
    }

    public interface ItemsCallback {
        void onResult(List<ItemPost> items);
    }

    public interface BiCallback {
        void onResult(boolean ok, String message);
    }

    // ---- FETCH ITEMS ----
    public static void getItemsByTypeAsync(String type, ItemsCallback cb) {
        api.getItemsByType(type).enqueue(new Callback<List<ItemPost>>() {
            @Override
            public void onResponse(Call<List<ItemPost>> call, Response<List<ItemPost>> response) {
                List<ItemPost> items = response.isSuccessful() && response.body() != null ? response.body() : List.of();
                new Handler(Looper.getMainLooper()).post(() -> cb.onResult(items));
            }

            @Override
            public void onFailure(Call<List<ItemPost>> call, Throwable t) {
                t.printStackTrace();
                new Handler(Looper.getMainLooper()).post(() -> cb.onResult(List.of()));
            }
        });
    }

    // ---- CREATE ITEM (UPDATED to send @FormUrlEncoded data) ----
    public static void createItemAsync(ItemPost item, InsertCallback cb) {
        Call<ApiResponse> call = api.createItem(
                item.type,
                item.title,
                item.description,
                item.category,
                item.location,
                String.valueOf(item.date),
                item.contact,
                item.photoUri
        );

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                boolean ok = response.isSuccessful() && response.body() != null && response.body().success;
                long id = (response.body() != null && response.body().id != null) ? response.body().id : -1;

                // Log full error message if something fails
                if (!ok) {
                    try {
                        Log.e("DBHelper", "Create item failed: " +
                                (response.errorBody() != null ? response.errorBody().string() : response.message()));
                    } catch (Exception e) {
                        Log.e("DBHelper", "Error reading error body", e);
                    }
                }

                new Handler(Looper.getMainLooper()).post(() -> cb.onInserted(ok, id));
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.e("DBHelper", "Network error while creating item", t);
                new Handler(Looper.getMainLooper()).post(() -> cb.onInserted(false, -1));
            }
        });
    }

    // ---- MARK RETURNED ----
    public static void markReturnedAsync(long id, BiCallback cb) {
        api.markReturned(id).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                boolean ok = response.isSuccessful() && response.body() != null && response.body().success;
                String msg = (response.body() != null) ? response.body().message : "Server error";
                new Handler(Looper.getMainLooper()).post(() -> cb.onResult(ok, msg));
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                new Handler(Looper.getMainLooper()).post(() -> cb.onResult(false, t.getMessage()));
            }
        });
    }
}
