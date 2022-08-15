package com.flatcode.simplemultiapps.PdfReader.Activity;

import android.Manifest;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.flatcode.simplemultiapps.R;
import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.github.paolorotolo.appintro.model.SliderPage;

public class PdfReaderIntroActivity extends AppIntro {
    int bg = Color.parseColor("#000000");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().hide();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            SliderPage one = new SliderPage();
            one.setTitle(getString(R.string.title_permission));
            one.setDescription(getString(R.string.description__permission));
            one.setImageDrawable(R.drawable.patterns_permissions);
            one.setBgColor(bg);
            addSlide(AppIntroFragment.newInstance(one));
            askForPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }

        showSkipButton(false);
        showStatusBar(false);
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        finish();
    }
}