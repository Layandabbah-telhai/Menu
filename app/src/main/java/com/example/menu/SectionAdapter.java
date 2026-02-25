package com.example.menu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.menu.models.Section;

import java.util.List;

public class SectionAdapter extends RecyclerView.Adapter<SectionAdapter.VH> {

    public interface OnSectionClick {
        void onClick(Section section);
    }

    private final List<Section> data;
    private final OnSectionClick listener;

    public SectionAdapter(List<Section> data, OnSectionClick listener) {
        this.data = data;
        this.listener = listener;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Section s = data.get(position);
        holder.title.setText(s.getName());
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onClick(s);
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView title;
        VH(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(android.R.id.text1);
        }
    }
}