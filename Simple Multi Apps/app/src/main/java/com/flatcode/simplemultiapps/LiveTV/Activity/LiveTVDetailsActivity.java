package com.flatcode.simplemultiapps.LiveTV.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.flatcode.simplemultiapps.LiveTV.Model.Channel;
import com.flatcode.simplemultiapps.R;
import com.flatcode.simplemultiapps.Unit.THEME;
import com.flatcode.simplemultiapps.databinding.ActivityLiveTvDetailsBinding;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

public class LiveTVDetailsActivity extends AppCompatActivity {

    private ActivityLiveTvDetailsBinding binding;

    ImageView fullScreen;
    boolean isFullScreen = false;
    SimpleExoPlayer player;

    Context context = LiveTVDetailsActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        THEME.setThemeOfApp(context);
        super.onCreate(savedInstanceState);
        binding = ActivityLiveTvDetailsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Channel channel = (Channel) getIntent().getSerializableExtra("channel");
        binding.toolbar.nameSpace.setText(channel.getName());
        binding.toolbar.back.setOnClickListener(v -> onBackPressed());

        fullScreen = binding.playerView.findViewById(R.id.exo_fullscreen_icon);

        fullScreen.setOnClickListener(v -> {
            if (isFullScreen) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);

                if (getSupportActionBar() != null) {
                    getSupportActionBar().show();
                }
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

                ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) binding.playerView.getLayoutParams();
                params.width = params.MATCH_PARENT;
                params.height = (int) (200 * getApplicationContext().getResources().getDisplayMetrics().density);
                binding.playerView.setLayoutParams(params);

                //Toast.makeText(Details.this, "We are Now going back to normal mode.", Toast.LENGTH_SHORT).show();
                isFullScreen = false;
            } else {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

                if (getSupportActionBar() != null) {
                    getSupportActionBar().hide();
                }
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

                ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) binding.playerView.getLayoutParams();
                params.width = params.MATCH_PARENT;
                params.height = params.MATCH_PARENT;
                binding.playerView.setLayoutParams(params);

                //Toast.makeText(Details.this, "We are going to FullScreen Mode.", Toast.LENGTH_SHORT).show();
                isFullScreen = true;
            }
        });

        binding.description.setText(channel.getDescription());
        binding.facebookLink.setOnClickListener(v -> openLink(channel.getFacebook()));
        binding.twitterLink.setOnClickListener(v -> openLink(channel.getTwitter()));
        binding.youtubeLink.setOnClickListener(v -> openLink(channel.getYoutube()));
        binding.websiteLink.setOnClickListener(v -> openLink(channel.getWebsite()));
        playChannel(channel.getLive_url());
    }

    public void openLink(String url) {
        Intent open = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(open);
    }

    public void playChannel(String live_url) {
        player = new SimpleExoPlayer.Builder(this).build();
        binding.playerView.setPlayer(player);

        DataSource.Factory dataSourceFactory = new DefaultHttpDataSourceFactory();
        HlsMediaSource mediaSource = new HlsMediaSource.Factory(dataSourceFactory).
                createMediaSource(MediaItem.fromUri(live_url));
        player.setMediaSource(mediaSource);
        player.prepare();
        player.setPlayWhenReady(true);
        player.addListener(new Player.Listener() {
            @Override
            public void onPlaybackStateChanged(int state) {
                if (state == Player.STATE_READY) {
                    binding.progressBar.setVisibility(View.GONE);
                    player.setPlayWhenReady(true);
                } else if (state == Player.STATE_BUFFERING) {
                    binding.progressBar.setVisibility(View.VISIBLE);
                    binding.playerView.setKeepScreenOn(true);
                } else {
                    binding.progressBar.setVisibility(View.GONE);
                    player.setPlayWhenReady(true);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        player.seekToDefaultPosition();
        player.setPlayWhenReady(true);
    }

    @Override
    protected void onPause() {
        player.setPlayWhenReady(false);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        player.release();
        super.onDestroy();
    }
}