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

public class VideoFolderAdapter extends RecyclerView.Adapter<VideoFolderAdapter.ViewHolder> {

    private ItemVideoBinding binding;
    private Context context;
    public static ArrayList<VideoFiles> folderVideoFiles;

    public VideoFolderAdapter(Context context, ArrayList<VideoFiles> folderVideoFiles) {
        this.context = context;
        this.folderVideoFiles = folderVideoFiles;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemVideoBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(folderVideoFiles.get(position).getTitle());
        holder.duration.setText(folderVideoFiles.get(position).getDuration());
        Glide.with(context).load(new File(folderVideoFiles.get(position).getPath())).into(holder.image);

        String duration = VOID.convertDuration(Long.parseLong(folderVideoFiles.get(position).getDuration()));
        holder.duration.setText(duration);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, PlayerActivity.class);
            intent.putExtra("position", position);
            intent.putExtra("sender", "FolderIsSending");
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return folderVideoFiles.size();
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