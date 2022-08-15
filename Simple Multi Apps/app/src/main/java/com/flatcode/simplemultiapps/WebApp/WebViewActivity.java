package com.flatcode.simplemultiapps.WebApp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.flatcode.simplemultiapps.Unit.DATA;
import com.flatcode.simplemultiapps.Unit.THEME;
import com.flatcode.simplemultiapps.databinding.ActivityWebViewBinding;

public class WebViewActivity extends AppCompatActivity {

    private ActivityWebViewBinding binding;

    String webName;

    Context context = WebViewActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        THEME.setThemeOfApp(context);
        super.onCreate(savedInstanceState);
        binding = ActivityWebViewBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Intent data = getIntent();
        webName = data.getStringExtra(DATA.WEB_NAME);

        binding.webView.getSettings().setLoadsImagesAutomatically(true);
        binding.webView.getSettings().setJavaScriptEnabled(true);
        binding.webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        if (webName.equals(DATA.WEBSITE))
            binding.webView.loadUrl(DATA.mySite);
        else if (webName.equals(DATA.INSTAGRAM))
            binding.webView.loadUrl(DATA.myInstagram);
        else if (webName.equals(DATA.FACEBOOK))
            binding.webView.loadUrl(DATA.myFacebook);
        else if (webName.equals(DATA.TWITTER))
            binding.webView.loadUrl(DATA.myTwitter);
        else binding.webView.loadUrl(DATA.mySite);
    }
}