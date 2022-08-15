package com.flatcode.simplemultiapps.NewsApp.Activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.flatcode.simplemultiapps.NewsApp.Model.NewsHeadlines;
import com.flatcode.simplemultiapps.R;
import com.flatcode.simplemultiapps.Unit.DATA;
import com.flatcode.simplemultiapps.Unit.THEME;
import com.flatcode.simplemultiapps.Unit.VOID;
import com.flatcode.simplemultiapps.databinding.ActivityNewsAppDetailsBinding;

public class NewsAppDetailsActivity extends AppCompatActivity {

    private ActivityNewsAppDetailsBinding binding;

    private NewsHeadlines headlines;

    Context context = NewsAppDetailsActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        THEME.setThemeOfApp(context);
        super.onCreate(savedInstanceState);
        binding = ActivityNewsAppDetailsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        headlines = (NewsHeadlines) getIntent().getSerializableExtra(DATA.DATA);

        binding.toolbar.nameSpace.setText(R.string.post_details);
        binding.toolbar.back.setOnClickListener(v-> onBackPressed());

        binding.title.setText(headlines.getTitle());
        binding.author.setText(headlines.getAuthor());
        binding.time.setText(headlines.getPublishedAt());
        binding.detail.setText(headlines.getDescription());
        binding.content.setText(headlines.getContent());
        VOID.Glide(context, headlines.getUrlToImage(), binding.image);
    }
}