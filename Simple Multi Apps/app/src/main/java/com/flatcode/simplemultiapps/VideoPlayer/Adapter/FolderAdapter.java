package com.flatcode.simplemultiapps.VideoPlayer.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.flatcode.simplemultiapps.VideoPlayer.Activity.VideoFolderActivity;
import com.flatcode.simplemultiapps.VideoPlayer.VideoFiles;
import com.flatcode.simplemultiapps.databinding.ItemVideoPlayerFolderBinding;

import java.util.ArrayList;

public class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.ViewHolder> {

    private ItemVideoPlayerFolderBinding binding;
    private Context context;
    private ArrayList<VideoFiles> videoFiles;
    private ArrayList<String> folderName;

    public FolderAdapter(Context context, ArrayList<VideoFiles> videoFiles, ArrayList<String> folderName) {
        this.context = context;
        this.videoFiles = videoFiles;
        this.folderName = folderName;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemVideoPlayerFolderBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int index = folderName.get(position).lastIndexOf("/");
        String folder = folderName.get(position).substring(index + 1);
        holder.name.setText(folder);
        holder.count.setText(String.valueOf(NumberOfFiles(folderName.get(position))));
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, VideoFolderActivity.class);
            intent.putExtra("folderName", folderName.get(position));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return folderName.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, count;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = binding.name;
            count = binding.count;
        }
    }

    int NumberOfFiles(String folderName) {
        int countFiles = 0;
        for (VideoFiles videoFiles : videoFiles) {
            if (videoFiles.getPath().substring(0, videoFiles.getPath().lastIndexOf("/")).endsWith(folderName)) {
                countFiles++;
            }
        }
        return countFiles;
    }
}