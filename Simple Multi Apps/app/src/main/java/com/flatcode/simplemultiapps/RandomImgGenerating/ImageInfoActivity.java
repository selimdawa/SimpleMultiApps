package com.flatcode.simplemultiapps.RandomImgGenerating;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.flatcode.simplemultiapps.R;
import com.flatcode.simplemultiapps.Unit.THEME;
import com.flatcode.simplemultiapps.Unit.VOID;
import com.flatcode.simplemultiapps.databinding.ActivityImageInfoBinding;

public class ImageInfoActivity extends AppCompatActivity {

    private ActivityImageInfoBinding binding;

    Context context = ImageInfoActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        THEME.setThemeOfApp(context);
        super.onCreate(savedInstanceState);
        binding = ActivityImageInfoBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.toolbar.nameSpace.setText(getString(R.string.image_info));

        Intent data = getIntent();

        // set the data to ui
        binding.catName.setText(data.getStringExtra("name"));
        binding.catOrigin.setText(data.getStringExtra("origin"));
        binding.catDescription.setText(data.getStringExtra("desc"));
        binding.catTemperament.setText(data.getStringExtra("temp"));
        VOID.Glide(context, data.getStringExtra("imageUrl"), binding.catImage);

        binding.wikiBtn.setOnClickListener(v -> {
            Uri catUri = Uri.parse(data.getStringExtra("wikiUrl"));
            Intent browser = new Intent(Intent.ACTION_VIEW, catUri);
            startActivity(browser);
        });

        binding.moreInfoBtn.setOnClickListener(v -> {
            Uri catUri = Uri.parse(data.getStringExtra("moreLink"));
            Intent browser = new Intent(Intent.ACTION_VIEW, catUri);
            startActivity(browser);
        });
    }
}