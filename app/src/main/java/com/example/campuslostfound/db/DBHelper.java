package com.example.campuslostfound.db;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.campuslostfound.api.ApiService;
import com.example.campuslostfound.models.ApiResponse;
import com.example.campuslostfound.models.ItemPost;
import com.example.campuslostfound.api.User;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DBHelper {

    private static final String BASE_URL = "http://10.0.2.2/campuslostfound/"; // Emulator localhost
    private static final String TAG = "DBHelper";

    private static final HttpLoggingInterceptor logging =
            new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

    private static final OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(chain -> {
                Request original = chain.request();
                Request req = original.newBuilder()
                        .header("Accept", "application/json")
                        .method(original.method(), original.body())
                        .build();
                return chain.proceed(req);
            })
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .build();

    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private static final ApiService api = retrofit.create(ApiService.class);
    private static final Handler mainHandler = new Handler(Looper.getMainLooper());

    // ===============================
    // LOGIN
    // ===============================
    public interface LoginCallback {
        void onResult(boolean success, String message, User user);
    }

    public static void loginAsync(String identifier, String password, LoginCallback cb) {
        api.login(identifier, password).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse body = response.body();
                    Log.d(TAG, "Login Response: " + body.message);
                    mainHandler.post(() -> cb.onResult(body.success && body.user != null,
                            body.message != null ? body.message : "Login failed",
                            body.user));
                } else {
                    mainHandler.post(() -> cb.onResult(false, "Server error", null));
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.e(TAG, "Login failed", t);
                mainHandler.post(() -> cb.onResult(false, t.getMessage(), null));
            }
        });
    }

    // ===============================
    // REGISTER
    // ===============================
    public interface RegisterCallback {
        void onResult(boolean success, String message);
    }

    public static void registerUser(String first_name, String last_name, String nrc,
                                    String student_no, String phone, String password,
                                    RegisterCallback cb) {
        api.registerUser(first_name, last_name, nrc, student_no, phone, password)
                .enqueue(new Callback<ApiResponse>() {
                    @Override
                    public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            ApiResponse body = response.body();
                            Log.d(TAG, "Register Response: " + body.message);
                            mainHandler.post(() -> cb.onResult(body.success,
                                    body.message != null ? body.message : "Registration failed"));
                        } else {
                            mainHandler.post(() -> cb.onResult(false, "Server error"));
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiResponse> call, Throwable t) {
                        Log.e(TAG, "Registration failed", t);
                        mainHandler.post(() -> cb.onResult(false, t.getMessage()));
                    }
                });
    }

    // ===============================
    // FETCH ITEMS
    // ===============================
    public interface ItemsCallback {
        void onResult(List<ItemPost> items);
    }

    public static void getItemsByType(String type, ItemsCallback cb) {
        api.getItemsByType(type).enqueue(new Callback<List<ItemPost>>() {
            @Override
            public void onResponse(Call<List<ItemPost>> call, Response<List<ItemPost>> response) {
                List<ItemPost> items = response.isSuccessful() && response.body() != null ? response.body() : null;
                mainHandler.post(() -> cb.onResult(items));
            }

            @Override
            public void onFailure(Call<List<ItemPost>> call, Throwable t) {
                Log.e(TAG, "Fetch items failed", t);
                mainHandler.post(() -> cb.onResult(null));
            }
        });
    }

    // ===============================
    // CREATE ITEM POST
    // ===============================
    public interface PostItemCallback {
        void onResult(boolean success, String message, Long id);
    }

    public static void createItem(ItemPost item, PostItemCallback cb) {
        // Convert date to YYYY-MM-DD format for PHP
        String formattedDate = "";
        try {
            if (item.date != null && !item.date.isEmpty()) {
                long millis = Long.parseLong(item.date);
                formattedDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                        .format(millis);
            }
        } catch (NumberFormatException e) {
            formattedDate = item.date; // assume already formatted
        }

        Log.d(TAG, "Creating item: type=" + item.type + ", title=" + item.title + ", date=" + formattedDate);

        api.createItem(
                item.type != null ? item.type : "",
                item.title != null ? item.title : "",
                item.description != null ? item.description : "",
                item.category != null ? item.category : "",
                item.location != null ? item.location : "",
                formattedDate,
                item.contact != null ? item.contact : "",
                item.photoUri != null ? item.photoUri : ""
        ).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse b = response.body();
                    Log.d(TAG, "Create Item Response: " + b.message);
                    mainHandler.post(() -> cb.onResult(b.success, b.message, b.id));
                } else {
                    String msg = "Server error";
                    try {
                        if (response.errorBody() != null) msg = response.errorBody().string();
                    } catch (Exception ignored) {}
                    String finalMsg = msg;
                    mainHandler.post(() -> cb.onResult(false, finalMsg, null));
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.e(TAG, "Create item failed", t);
                mainHandler.post(() -> cb.onResult(false, t.getMessage(), null));
            }
        });
    }

    // ===============================
    // MARK ITEM AS RETURNED
    // ===============================
    public interface BiCallback {
        void onResult(boolean success, String message);
    }

    public static void markReturned(long id, BiCallback cb) {
        api.markReturned(id).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse b = response.body();
                    Log.d(TAG, "Mark Returned Response: " + b.message);
                    mainHandler.post(() -> cb.onResult(b.success, b.message));
                } else {
                    String msg = "Server error";
                    try {
                        if (response.errorBody() != null) msg = response.errorBody().string();
                    } catch (Exception ignored) {}
                    String finalMsg = msg;
                    mainHandler.post(() -> cb.onResult(false, finalMsg));
                }
            }



            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.e(TAG, "Mark returned failed", t);
                mainHandler.post(() -> cb.onResult(false, t.getMessage()));
            }
        });
    }
}
