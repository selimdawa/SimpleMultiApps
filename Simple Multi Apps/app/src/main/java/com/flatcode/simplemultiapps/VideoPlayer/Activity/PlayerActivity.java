package com.flatcode.simplemultiapps.VideoPlayer.Activity;

import static com.flatcode.simplemultiapps.VideoPlayer.Adapter.VideoAdapter.videoFiles;
import static com.flatcode.simplemultiapps.VideoPlayer.Adapter.VideoFolderAdapter.folderVideoFiles;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.flatcode.simplemultiapps.Unit.THEME;
import com.flatcode.simplemultiapps.VideoPlayer.VideoFiles;
import com.flatcode.simplemultiapps.databinding.ActivityPlayerBinding;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;

public class PlayerActivity extends AppCompatActivity {

    private ActivityPlayerBinding binding;
    private Context context = PlayerActivity.this;

    SimpleExoPlayer simpleExoPlayer;
    int position = -1;
    ArrayList<VideoFiles> myFiles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        THEME.setThemeOfApp(context);
        super.onCreate(savedInstanceState);
        binding = ActivityPlayerBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setFullScreen();
        setContentView(view);

        position = getIntent().getIntExtra("position", -1);
        String sender = getIntent().getStringExtra("sender");
        if (sender.equals("FolderIsSending")) {
            myFiles = folderVideoFiles;
        } else {
            myFiles = videoFiles;
        }

        String path = myFiles.get(position).getPath();
        if (path != null) {
            Uri uri = Uri.parse(path);
            simpleExoPlayer = new SimpleExoPlayer.Builder(context).build();
            DataSource.Factory factory = new DefaultDataSourceFactory(context, Util.getUserAgent(context, "My App Name"));
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
            MediaSource mediaSource = new ProgressiveMediaSource.Factory(factory, extractorsFactory).createMediaSource(uri);
            binding.expo.setPlayer(simpleExoPlayer);
            binding.expo.setKeepScreenOn(true);
            simpleExoPlayer.prepare(mediaSource);
            simpleExoPlayer.setPlayWhenReady(true);
        }
    }

    private void setFullScreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    protected void onPause() {
        simpleExoPlayer.pause();
        super.onPause();
    }

    @Override
    protected void onStop() {
        simpleExoPlayer.stop();
        super.onStop();
    }
}