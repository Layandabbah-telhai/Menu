package com.example.menu.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.menu.R;

import java.util.List;

public class ImagePagerAdapter extends RecyclerView.Adapter<ImagePagerAdapter.VH> {

    private final Context context;
    private final List<String> imageResNames;

    public ImagePagerAdapter(Context context, List<String> imageResNames) {
        this.context = context;
        this.imageResNames = imageResNames;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.page_image, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        String resName = imageResNames.get(position);
        int resId = context.getResources().getIdentifier(resName, "drawable", context.getPackageName());
        if (resId != 0) {
            holder.img.setImageResource(resId);
        } else {
            holder.img.setImageResource(android.R.color.darker_gray);
        }
    }

    @Override
    public int getItemCount() {
        return imageResNames == null ? 0 : imageResNames.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        ImageView img;
        VH(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
        }
    }
}