package com.flatcode.simplemultiapps.NewsApp.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.flatcode.simplemultiapps.R;

public class NewsAppViewHolder extends RecyclerView.ViewHolder {

    TextView content, source;
    ImageView image;
    CardView card;

    public NewsAppViewHolder(@NonNull View itemView) {
        super(itemView);
        content = itemView.findViewById(R.id.content);
        source = itemView.findViewById(R.id.source);
        image = itemView.findViewById(R.id.image);
        card = itemView.findViewById(R.id.card);
    }
}