package com.flatcode.simplemultiapps.BloggerApp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.flatcode.simplemultiapps.BloggerApp.Model.Label;
import com.flatcode.simplemultiapps.databinding.ItemBloggerLabelBinding;

import java.util.ArrayList;

public class LabelAdapter extends RecyclerView.Adapter<LabelAdapter.ViewHolder> {

    private ItemBloggerLabelBinding binding;
    private Context context;
    public ArrayList<Label> labels;

    public LabelAdapter(Context context, ArrayList<Label> labels) {
        this.context = context;
        this.labels = labels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemBloggerLabelBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Label list = labels.get(position);
        String label = list.getLabel();

        holder.label.setText(label);
    }

    @Override
    public int getItemCount() {
        return labels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView label;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            label = binding.label;
        }
    }
}