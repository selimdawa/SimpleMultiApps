package com.flatcode.simplemultiapps.WebApp;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.flatcode.simplemultiapps.R;
import com.flatcode.simplemultiapps.Unit.CLASS;
import com.flatcode.simplemultiapps.Unit.DATA;
import com.flatcode.simplemultiapps.Unit.THEME;
import com.flatcode.simplemultiapps.Unit.VOID;
import com.flatcode.simplemultiapps.databinding.ActivityWebAppBinding;

public class WebAppActivity extends AppCompatActivity {

    private ActivityWebAppBinding binding;

    private final Context context = WebAppActivity.this;
    public static WebAppActivity instance = null;

    AlertDialog alertDialog;

    public WebAppActivity() {
        instance = WebAppActivity.this;
    }

    public static synchronized WebAppActivity getInstance() {
        if (instance == null) {
            instance = new WebAppActivity();
        }
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        THEME.setThemeOfApp(context);
        super.onCreate(savedInstanceState);
        binding = ActivityWebAppBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.toolbar.nameSpace.setText(R.string.web_app);

        if (ActivityCompat.checkSelfPermission(WebAppActivity.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(WebAppActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
        }

        binding.webSite.setOnClickListener(v -> VOID.IntentExtra(context, CLASS.WEB_VIEW, DATA.WEB_NAME, DATA.WEBSITE));
        binding.instagram.setOnClickListener(v -> VOID.IntentExtra(context, CLASS.WEB_VIEW, DATA.WEB_NAME, DATA.INSTAGRAM));
        binding.twitter.setOnClickListener(v -> VOID.IntentExtra(context, CLASS.WEB_VIEW, DATA.WEB_NAME, DATA.TWITTER));
        binding.facebook.setOnClickListener(v -> VOID.IntentExtra(context, CLASS.WEB_VIEW, DATA.WEB_NAME, DATA.FACEBOOK));

        binding.aboutUs.setOnClickListener(v -> {
            final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);

            View dialogView = LayoutInflater.from(context).inflate(R.layout.item_web_card, null);
            dialogBuilder.setView(dialogView);

            RelativeLayout about_app = dialogView.findViewById(R.id.about_app);
            about_app.setVisibility(View.VISIBLE);
            RelativeLayout contact = dialogView.findViewById(R.id.contact);
            contact.setVisibility(View.GONE);
            TextView about_us = dialogView.findViewById(R.id.about_us);
            TextView close = dialogView.findViewById(R.id.close);
            alertDialog = dialogBuilder.create();

            close.setOnClickListener(v1 -> alertDialog.dismiss());
            about_us.setVisibility(View.VISIBLE);
            about_us.setText(DATA.aboutUs);
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            alertDialog.show();
        });

        binding.support.setOnClickListener(v -> {
            final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);

            View dialogView = LayoutInflater.from(context).inflate(R.layout.item_web_card, null);
            dialogBuilder.setView(dialogView);

            RelativeLayout about_app = dialogView.findViewById(R.id.about_app);
            about_app.setVisibility(View.GONE);
            RelativeLayout contact = dialogView.findViewById(R.id.contact);
            contact.setVisibility(View.VISIBLE);
            TextView about_us = dialogView.findViewById(R.id.about_us);
            about_us.setVisibility(View.GONE);
            ImageView email = dialogView.findViewById(R.id.email);
            ImageView phone = dialogView.findViewById(R.id.phone);
            alertDialog = dialogBuilder.create();

            TextView close = dialogView.findViewById(R.id.close);
            close.setOnClickListener(v14 -> alertDialog.dismiss());
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            email.setOnClickListener(v13 -> {
                Intent emailSelectorIntent = new Intent(Intent.ACTION_SENDTO);
                emailSelectorIntent.setData(Uri.parse("mailto:"));

                final Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{DATA.myEmail});
                emailIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                emailIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                emailIntent.setSelector(emailSelectorIntent);
                startActivity(emailIntent);
            });

            phone.setOnClickListener(v12 -> {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + DATA.myMobileNumber));
                startActivity(callIntent);
            });

            alertDialog.show();
        });

        binding.shareApp.setOnClickListener(v -> {
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("text/plain");
            share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            share.putExtra(Intent.EXTRA_TEXT, "Share App with" + "\n" + "https://play.google.com/store/apps/details?id=" + context.getPackageName());
            startActivity(Intent.createChooser(share, "Share link!"));

        });

        binding.rateApp.setOnClickListener(v -> {
            Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            try {
                startActivity(goToMarket);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id=" + context.getPackageName())));
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Permission granted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }
}