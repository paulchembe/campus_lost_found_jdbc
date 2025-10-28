package com.example.campuslostfound.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.campuslostfound.R;
import com.example.campuslostfound.models.ItemPost;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private final Context context;
    private final List<ItemPost> itemList;

    public ItemAdapter(Context context, List<ItemPost> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_lost_found, parent, false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        ItemPost item = itemList.get(position);

        holder.tvTitle.setText(item.title);
        holder.tvCategory.setText("Category: " + item.category);
        holder.tvLocation.setText("Location: " + item.location);

        String dateStr = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(item.date);
        holder.tvDate.setText("Date: " + dateStr);

        if (item.photoUri != null && !item.photoUri.isEmpty()) {
            holder.imgItem.setImageURI(Uri.parse(item.photoUri));
        } else {
            holder.imgItem.setImageResource(R.drawable.ic_camera_placeholder);
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView imgItem;
        TextView tvTitle, tvCategory, tvLocation, tvDate;

        public ItemViewHolder(View itemView) {
            super(itemView);
            imgItem = itemView.findViewById(R.id.imgItem);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            tvDate = itemView.findViewById(R.id.tvDate);
        }
    }
}
