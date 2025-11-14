package com.example.campuslostfound.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.campuslostfound.R;
import com.example.campuslostfound.api.ApiClient;
import com.example.campuslostfound.api.ApiService;
import com.example.campuslostfound.models.ApiResponse;
import com.example.campuslostfound.models.ItemPost;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemDetailsActivity extends AppCompatActivity {

    private ImageView imgItem;
    private TextView tvTitle, tvCategory, tvLocation, tvDate, tvDescription, tvContact, tvStatus;
    private Button btnMarkReturned;

    private long itemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        // Initialize views
        imgItem = findViewById(R.id.imgItemDetail);
        tvTitle = findViewById(R.id.tvTitleDetail);
        tvCategory = findViewById(R.id.tvCategoryDetail);
        tvLocation = findViewById(R.id.tvLocationDetail);
        tvDate = findViewById(R.id.tvDateDetail);
        tvDescription = findViewById(R.id.tvDescriptionDetail);
        tvContact = findViewById(R.id.tvContactDetail);
        tvStatus = findViewById(R.id.tvStatusDetail);
        btnMarkReturned = findViewById(R.id.btnMarkReturned);

        // Receive item details via Intent (Option 2 – passed individually)
        itemId = getIntent().getLongExtra("item_id", -1);
        String title = getIntent().getStringExtra("title");
        String category = getIntent().getStringExtra("category");
        String location = getIntent().getStringExtra("location");
        String date = getIntent().getStringExtra("date");
        String description = getIntent().getStringExtra("description");
        String contact = getIntent().getStringExtra("contact");
        String photoUri = getIntent().getStringExtra("photoUri");
        String status = getIntent().getStringExtra("status");

        // Bind data
        tvTitle.setText(title);
        tvCategory.setText("Category: " + category);
        tvLocation.setText("Location: " + location);
        tvDate.setText("Date: " + date);
        tvDescription.setText(description);
        tvContact.setText("Contact: " + contact);
        tvStatus.setText("Status: " + (status != null ? status : "Not Returned"));

        if (photoUri != null && !photoUri.isEmpty()) {
            Picasso.get().load(photoUri).placeholder(R.drawable.ic_camera_placeholder).into(imgItem);
        } else {
            imgItem.setImageResource(R.drawable.ic_camera_placeholder);
        }

        // Handle "Mark as Returned"
        btnMarkReturned.setOnClickListener(v -> markAsReturned());
    }

    private void markAsReturned() {
        if (itemId == -1) {
            Toast.makeText(this, "Invalid item ID!", Toast.LENGTH_SHORT).show();
            return;
        }

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Updating status...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<ApiResponse> call = apiService.markReturned(itemId);

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse apiResponse = response.body();
                    if (apiResponse.isSuccess()) {
                        Toast.makeText(ItemDetailsActivity.this, "Marked as returned successfully!", Toast.LENGTH_LONG).show();
                        tvStatus.setText("Status: Returned");
                        btnMarkReturned.setEnabled(false);
                    } else {
                        Toast.makeText(ItemDetailsActivity.this, apiResponse.getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(ItemDetailsActivity.this, "Server error!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ItemDetailsActivity.this, "Failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
