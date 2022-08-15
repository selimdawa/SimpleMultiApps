package com.flatcode.simplemultiapps.MainApp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import com.flatcode.simplemultiapps.R;
import com.flatcode.simplemultiapps.Unit.CLASS;
import com.flatcode.simplemultiapps.Unit.THEME;
import com.flatcode.simplemultiapps.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private ActivityMainBinding binding;

    List<Main> list;
    MainAdapter adapter;

    Context context = MainActivity.this;

    private static final int SETTINGS_CODE = 234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        PreferenceManager.getDefaultSharedPreferences(getBaseContext())
                .registerOnSharedPreferenceChangeListener(this);
        THEME.setThemeOfApp(context);
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // Color Mode ----------------------------- Start
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new MainActivity.SettingsFragment())
                .commit();
        // Color Mode -------------------------------- End

        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getBaseContext());
        if (sharedPreferences.getString("color_option", "ONE").equals("ONE") ||
                sharedPreferences.getString("color_option", "TWO").equals("TWO") ||
                sharedPreferences.getString("color_option", "THREE").equals("THREE") ||
                sharedPreferences.getString("color_option", "FOUR").equals("FOUR") ||
                sharedPreferences.getString("color_option", "FIVE").equals("FIVE") ||
                sharedPreferences.getString("color_option", "SIX").equals("SIX") ||
                sharedPreferences.getString("color_option", "SEVEN").equals("SEVEN") ||
                sharedPreferences.getString("color_option", "EIGHT").equals("EIGHT") ||
                sharedPreferences.getString("color_option", "NINE").equals("NINE") ||
                sharedPreferences.getString("color_option", "TEEN").equals("TEEN")) {
            binding.toolbar.mode.setBackgroundResource(R.drawable.sun);
        } else if (sharedPreferences.getString("color_option", "NIGHT_ONE").equals("NIGHT_ONE") ||
                sharedPreferences.getString("color_option", "NIGHT_TWO").equals("NIGHT_TWO") ||
                sharedPreferences.getString("color_option", "NIGHT_THREE").equals("NIGHT_THREE") ||
                sharedPreferences.getString("color_option", "NIGHT_FOUR").equals("NIGHT_FOUR") ||
                sharedPreferences.getString("color_option", "NIGHT_FIVE").equals("NIGHT_FIVE") ||
                sharedPreferences.getString("color_option", "NIGHT_SIX").equals("NIGHT_SIX") ||
                sharedPreferences.getString("color_option", "NIGHT_SEVEN").equals("NIGHT_SEVEN")) {
            binding.toolbar.mode.setBackgroundResource(R.drawable.moon);
        }

        binding.recyclerView.setHasFixedSize(true);
        list = new ArrayList<>();
        adapter = new MainAdapter(context, list);
        binding.recyclerView.setAdapter(adapter);

        IdeaPosts(1, 1, 1, 2, 4, 1, 4, 2, 2, 3, 2, 3);
    }

    private void IdeaPosts(int I1, int I2, int I3, int I4, int I5, int I6,
                           int I7, int I8, int I9, int I10, int I11, int I12) {
        list.clear();
        Main item1 = new Main(R.drawable.ic_stop_watch, "Stop Watch", I1, CLASS.STOP_WATCH);
        Main item2 = new Main(R.drawable.ic_candy_cruch, "Candy Crush Game", I2, CLASS.CANDY_CRUSH_GAME);
        Main item3 = new Main(R.drawable.ic_multi_delete, "Multiple Delete", I3, CLASS.MULTIPLE_DELETE);
        Main item4 = new Main(R.drawable.ic_random, "Random Img Generating", I4, CLASS.RANDOM_IMG_GENERATING);
        Main item5 = new Main(R.drawable.ic_blogger, "Blogger App", I5, CLASS.BLOGGER_APP);
        Main item6 = new Main(R.drawable.ic_joke, "Joke App", I6, CLASS.JOKE_APP);
        Main item7 = new Main(R.drawable.ic_live_tv, "Live TV", I7, CLASS.LIVE_TV);
        Main item8 = new Main(R.drawable.ic_news, "News App", I8, CLASS.NEWS_APP);
        Main item9 = new Main(R.drawable.ic_pdf_reader, "Pdf Reader", I9, CLASS.PDF_READER);
        Main item10 = new Main(R.drawable.ic_video_player, "Video Player", I10, CLASS.VIDEO_PLAYER);
        Main item11 = new Main(R.drawable.ic_web, "Web App", I11, CLASS.WEB_APP);
        Main item12 = new Main(R.drawable.ic_wordpress, "Wordpress Blog", I12, CLASS.WORDPRESS);
        list.add(item1);
        list.add(item2);
        list.add(item3);
        list.add(item4);
        list.add(item5);
        list.add(item6);
        list.add(item7);
        list.add(item8);
        list.add(item9);
        list.add(item10);
        list.add(item11);
        list.add(item12);
        adapter.notifyDataSetChanged();
        binding.bar.setVisibility(View.GONE);
        binding.recyclerView.setVisibility(View.VISIBLE);
    }

    // Color Mode ----------------------------- Start
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("color_option")) {
            this.recreate();
        }
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SETTINGS_CODE) {
            this.recreate();
        }
    }
    // Color Mode -------------------------------- End
}