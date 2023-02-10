package com.flatcode.simplemultiapps.Unit;

import android.Manifest;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceManager;

import com.bumptech.glide.Glide;
import com.flatcode.simplemultiapps.LiveTV.Model.Category;
import com.flatcode.simplemultiapps.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

public class VOID {

    public static void IntentClear(Context context, Class c) {
        Intent intent = new Intent(context, c);
        context.startActivity(intent);
    }

    public static void Intent(Context context, Class c) {
        Intent intent = new Intent(context, c);
        context.startActivity(intent);
    }

    public static void IntentExtra(Context context, Class c, String key, String value) {
        Intent intent = new Intent(context, c);
        intent.putExtra(key, value);
        context.startActivity(intent);
    }

    public static void IntentSerializable(Context context, Class c, String key, Serializable value) {
        Intent intent = new Intent(context, c);
        intent.putExtra(key, value);
        context.startActivity(intent);
    }

    public static void IntentExtraChannel(Context context, Class c, String key, Category value) {
        Intent intent = new Intent(context, c);
        intent.putExtra("categoryName", DATA.EMPTY);
        intent.putExtra(key, value);
        context.startActivity(intent);
    }

    public static void IntentExtra2(Context context, Class c, String key, String value, String key2, String value2) {
        Intent intent = new Intent(context, c);
        intent.putExtra(key, value);
        intent.putExtra(key2, value2);
        context.startActivity(intent);
    }

    public static void Glide(Context context, String Url, ImageView Image) {
        try {
            Glide.with(context).load(Url).placeholder(R.color.image_profile).into(Image);
        } catch (Exception e) {
            Image.setImageResource(R.color.image_profile);
        }
    }

    public static void Intro(Context context, ImageView background, ImageView backWhite, ImageView backDark) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        if ((sharedPreferences.getString("color_option", "ONE").equals("ONE")) ||
                (sharedPreferences.getString("color_option", "TWO").equals("TWO")) ||
                (sharedPreferences.getString("color_option", "THREE").equals("THREE")) ||
                (sharedPreferences.getString("color_option", "FOUR").equals("FOUR")) ||
                (sharedPreferences.getString("color_option", "FIVE").equals("FIVE")) ||
                (sharedPreferences.getString("color_option", "SIX").equals("SIX")) ||
                (sharedPreferences.getString("color_option", "SEVEN").equals("SEVEN")) ||
                (sharedPreferences.getString("color_option", "EIGHT").equals("EIGHT")) ||
                (sharedPreferences.getString("color_option", "NINE").equals("NINE")) ||
                (sharedPreferences.getString("color_option", "TEEN").equals("TEEN"))) {
            background.setImageResource(R.drawable.background_day);
            backWhite.setVisibility(View.VISIBLE);
            backDark.setVisibility(View.GONE);
        } else if ((sharedPreferences.getString("color_option", "NIGHT_ONE").equals("NIGHT_ONE")) ||
                (sharedPreferences.getString("color_option", "NIGHT_TWO").equals("NIGHT_TWO")) ||
                (sharedPreferences.getString("color_option", "NIGHT_THREE").equals("NIGHT_THREE")) ||
                (sharedPreferences.getString("color_option", "NIGHT_FOUR").equals("NIGHT_FOUR")) ||
                (sharedPreferences.getString("color_option", "NIGHT_FIVE").equals("NIGHT_FIVE")) ||
                (sharedPreferences.getString("color_option", "NIGHT_SIX").equals("NIGHT_SIX")) ||
                (sharedPreferences.getString("color_option", "NIGHT_SEVEN").equals("NIGHT_SEVEN"))) {
            background.setImageResource(R.drawable.background_night);
            backWhite.setVisibility(View.GONE);
            backDark.setVisibility(View.VISIBLE);
        }
    }

    public static void Logo(Context context, ImageView background) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        if ((sharedPreferences.getString("color_option", "ONE").equals("ONE")) ||
                (sharedPreferences.getString("color_option", "TWO").equals("TWO")) ||
                (sharedPreferences.getString("color_option", "THREE").equals("THREE")) ||
                (sharedPreferences.getString("color_option", "FOUR").equals("FOUR")) ||
                (sharedPreferences.getString("color_option", "FIVE").equals("FIVE")) ||
                (sharedPreferences.getString("color_option", "SIX").equals("SIX")) ||
                (sharedPreferences.getString("color_option", "SEVEN").equals("SEVEN")) ||
                (sharedPreferences.getString("color_option", "EIGHT").equals("EIGHT")) ||
                (sharedPreferences.getString("color_option", "NINE").equals("NINE")) ||
                (sharedPreferences.getString("color_option", "TEEN").equals("TEEN")))
            background.setImageResource(R.drawable.logo);
        else if ((sharedPreferences.getString("color_option", "NIGHT_ONE").equals("NIGHT_ONE")) ||
                (sharedPreferences.getString("color_option", "NIGHT_TWO").equals("NIGHT_TWO")) ||
                (sharedPreferences.getString("color_option", "NIGHT_THREE").equals("NIGHT_THREE")) ||
                (sharedPreferences.getString("color_option", "NIGHT_FOUR").equals("NIGHT_FOUR")) ||
                (sharedPreferences.getString("color_option", "NIGHT_FIVE").equals("NIGHT_FIVE")) ||
                (sharedPreferences.getString("color_option", "NIGHT_SIX").equals("NIGHT_SIX")) ||
                (sharedPreferences.getString("color_option", "NIGHT_SEVEN").equals("NIGHT_SEVEN")))
            background.setImageResource(R.drawable.logo_night);
    }

    public static Intent plainTextShareIntent(String chooserTitle, String text) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, text);
        return Intent.createChooser(intent, chooserTitle);
    }

    public static Intent fileShareIntent(String chooserTitle, String fileName, Uri fileUri) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("application/pdf");
        intent.putExtra(Intent.EXTRA_STREAM, fileUri);
        intent.setClipData(new ClipData(fileName, new String[]{"application/pdf"}, new ClipData.Item(fileUri)));
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        return Intent.createChooser(intent, chooserTitle);
    }

    public static boolean canWriteToDownloadFolder(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            return true;
        }
        return ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED;
    }

    public static byte[] readBytesToEnd(InputStream inputStream) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[8 * 1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }
        return output.toByteArray();
    }

    public static void writeBytesToFile(File directory, String fileName, byte[] fileContent) throws IOException {
        File file = new File(directory, fileName);
        try (FileOutputStream stream = new FileOutputStream(file)) {
            stream.write(fileContent);
        }
    }

    public static String convertDuration(long duration) {
        long minutes = (duration / 1000) / 60;
        long seconds = (duration / 1000) % 60;
        String converted = String.format("%d:%02d", minutes, seconds);
        return converted;
    }
}