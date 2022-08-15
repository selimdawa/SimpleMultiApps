package com.flatcode.simplemultiapps.VideoPlayer.Activity;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.flatcode.simplemultiapps.Unit.THEME;
import com.flatcode.simplemultiapps.VideoPlayer.Adapter.VideoFolderAdapter;
import com.flatcode.simplemultiapps.VideoPlayer.VideoFiles;
import com.flatcode.simplemultiapps.databinding.ActivityVideoFolderBinding;

import java.util.ArrayList;

public class VideoFolderActivity extends AppCompatActivity {

    private ActivityVideoFolderBinding binding;
    private Context context = VideoFolderActivity.this;

    VideoFolderAdapter adapter;
    String myFolderName;
    ArrayList<VideoFiles> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        THEME.setThemeOfApp(context);
        super.onCreate(savedInstanceState);
        binding = ActivityVideoFolderBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        myFolderName = getIntent().getStringExtra("folderName");
        if (myFolderName != null) {
            list = getAllVideos(context, myFolderName);
        }
        if (list.size() > 0) {
            adapter = new VideoFolderAdapter(context, list);
            binding.recyclerView.setAdapter(adapter);
            binding.recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        }
    }

    public ArrayList<VideoFiles> getAllVideos(Context context, String folderName) {
        ArrayList<VideoFiles> tempVideoFiles = new ArrayList<>();
        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {
                MediaStore.Video.Media._ID,
                MediaStore.Video.Media.DATA,
                MediaStore.Video.Media.TITLE,
                MediaStore.Video.Media.SIZE,
                MediaStore.Video.Media.DATE_ADDED,
                MediaStore.Video.Media.DURATION,
                MediaStore.Video.Media.DISPLAY_NAME,
                MediaStore.Video.Media.BUCKET_DISPLAY_NAME
        };
        String selection = MediaStore.Video.Media.DATA + " like?";
        String[] selectionArgs = new String[]{"%" + folderName + "%"};
        Cursor cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String id = cursor.getString(0);
                String path = cursor.getString(1);
                String title = cursor.getString(2);
                String size = cursor.getString(3);
                String dateAdded = cursor.getString(4);
                String duration = cursor.getString(5);
                String fileName = cursor.getString(6);
                String bucket_name = cursor.getString(7);
                VideoFiles videoFiles = new VideoFiles(id, path, title, fileName, size, dateAdded, duration);
                if (folderName.endsWith(bucket_name))
                    tempVideoFiles.add(videoFiles);
            }
            cursor.close();
        }
        return tempVideoFiles;
    }
}