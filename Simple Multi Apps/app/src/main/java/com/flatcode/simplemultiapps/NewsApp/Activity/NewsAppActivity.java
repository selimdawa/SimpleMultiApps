package com.flatcode.simplemultiapps.NewsApp.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.flatcode.simplemultiapps.NewsApp.Adapter.NewsAppAdapter;
import com.flatcode.simplemultiapps.NewsApp.Model.NewsApiResponse;
import com.flatcode.simplemultiapps.NewsApp.Model.NewsHeadlines;
import com.flatcode.simplemultiapps.NewsApp.OnFetchDataListener;
import com.flatcode.simplemultiapps.NewsApp.RequestManger;
import com.flatcode.simplemultiapps.NewsApp.SelectListener;
import com.flatcode.simplemultiapps.R;
import com.flatcode.simplemultiapps.Unit.CLASS;
import com.flatcode.simplemultiapps.Unit.DATA;
import com.flatcode.simplemultiapps.Unit.THEME;
import com.flatcode.simplemultiapps.Unit.VOID;
import com.flatcode.simplemultiapps.databinding.ActivityNewsAppBinding;

import java.util.List;

public class NewsAppActivity extends AppCompatActivity implements SelectListener, View.OnClickListener {

    private ActivityNewsAppBinding binding;
    Context context = NewsAppActivity.this;

    private NewsAppAdapter adapter;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        THEME.setThemeOfApp(context);
        super.onCreate(savedInstanceState);
        binding = ActivityNewsAppBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.toolbar.back.setVisibility(View.VISIBLE);
        binding.toolbar.back.setOnClickListener(v -> onBackPressed());
        binding.toolbar.nameSpace.setText(R.string.news_app);

        dialog = new ProgressDialog(context);
        dialog.setTitle("Fetching news Articles...");
        dialog.show();

        binding.search.search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                dialog.setTitle("Fetching news Articles of " + query);
                dialog.show();
                RequestManger manger = new RequestManger(context);
                manger.getNewsHeadlines(listener, "general", query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        binding.linearSwitchUser.business.setOnClickListener(this);
        binding.linearSwitchUser.entertainment.setOnClickListener(this);
        binding.linearSwitchUser.general.setOnClickListener(this);
        binding.linearSwitchUser.health.setOnClickListener(this);
        binding.linearSwitchUser.science.setOnClickListener(this);
        binding.linearSwitchUser.sports.setOnClickListener(this);
        binding.linearSwitchUser.technology.setOnClickListener(this);

        RequestManger manger = new RequestManger(context);
        manger.getNewsHeadlines(listener, "general", null);
    }

    private final OnFetchDataListener<NewsApiResponse> listener = new OnFetchDataListener<>() {
        @Override
        public void onFetchData(List<NewsHeadlines> list, String message) {
            if (list.isEmpty()) {
                Toast.makeText(context, "No data found! ", Toast.LENGTH_SHORT).show();
            } else {
                showNews(list);
                dialog.dismiss();
            }
        }

        @Override
        public void onError(String message) {
            Toast.makeText(context, "Error! ", Toast.LENGTH_SHORT).show();
        }
    };

    private void showNews(List<NewsHeadlines> list) {
        //binding.recyclerView.setHasFixedSize(true);
        adapter = new NewsAppAdapter(context, list, this);
        binding.recyclerView.setAdapter(adapter);
    }

    @Override
    public void onNewsClicked(NewsHeadlines headlines) {
        VOID.IntentSerializable(context, CLASS.NEWS_APP_DETAILS, DATA.DATA, headlines);
    }

    @Override
    public void onClick(View view) {
        TextView button = (TextView) view;
        String category = button.getText().toString();
        dialog.setTitle("Fetching news Articles of " + category);
        dialog.show();
        RequestManger manger = new RequestManger(context);
        manger.getNewsHeadlines(listener, category, null);
    }
}