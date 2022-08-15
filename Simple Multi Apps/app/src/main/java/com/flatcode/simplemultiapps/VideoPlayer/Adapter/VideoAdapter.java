package com.flatcode.simplemultiapps.VideoPlayer.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.flatcode.simplemultiapps.Unit.VOID;
import com.flatcode.simplemultiapps.VideoPlayer.Activity.PlayerActivity;
import com.flatcode.simplemultiapps.VideoPlayer.VideoFiles;
import com.flatcode.simplemultiapps.databinding.ItemVideoBinding;

import java.io.File;
import java.util.ArrayList;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {

    private ItemVideoBinding binding;
    private Context context;
    public static ArrayList<VideoFiles> videoFiles;

    public VideoAdapter(Context context, ArrayList<VideoFiles> videoFiles) {
        this.context = context;
        this.videoFiles = videoFiles;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemVideoBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(videoFiles.get(position).getTitle());
        holder.duration.setText(videoFiles.get(position).getDuration());
        Glide.with(context).load(new File(videoFiles.get(position).getPath())).into(holder.image);

        String duration = VOID.convertDuration(Long.parseLong(videoFiles.get(position).getDuration()));
        holder.duration.setText(duration);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, PlayerActivity.class);
            intent.putExtra("position", position);
            intent.putExtra("sender", "FilesIsSending");
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return videoFiles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name, duration;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = binding.image;
            name = binding.name;
            duration = binding.duration;
        }
    }
}