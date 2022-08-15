package com.flatcode.simplemultiapps.Wordpress.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.flatcode.simplemultiapps.R;
import com.flatcode.simplemultiapps.Unit.CLASS;
import com.flatcode.simplemultiapps.Unit.THEME;
import com.flatcode.simplemultiapps.Unit.VOID;
import com.flatcode.simplemultiapps.Wordpress.Adapter.WordpressAdapter;
import com.flatcode.simplemultiapps.Wordpress.Model.Post;
import com.flatcode.simplemultiapps.Wordpress.Util.InternetConnection;
import com.flatcode.simplemultiapps.Wordpress.Util.WPApiService;
import com.flatcode.simplemultiapps.Wordpress.Util.WordPressClient;
import com.flatcode.simplemultiapps.databinding.ActivityWordpressBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WordpressActivity extends AppCompatActivity {

    private ActivityWordpressBinding binding;
    Context context = WordpressActivity.this;

    private List<Post> postItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        THEME.setThemeOfApp(context);
        super.onCreate(savedInstanceState);
        binding = ActivityWordpressBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.toolbar.nameSpace.setText(R.string.wordpress_app);

        binding.swipeRefresh.setOnRefreshListener(() -> {
            binding.swipeRefresh.setRefreshing(true);
            new Handler().postDelayed(() -> {
                binding.swipeRefresh.setRefreshing(false);
                setListContent(false);
            }, 3000);
        });

        binding.toolbar.favorites.setOnClickListener(v -> VOID.Intent(context, CLASS.WORDPRESS_FAVORITES));

        setListContent(true);
    }

    public void setListContent(final boolean withProgress) {
        if (InternetConnection.checkInternetConnection(getApplicationContext())) {
            WPApiService api = WordPressClient.getApiService();
            Call<List<Post>> call = api.getPosts();

            final ProgressDialog progressDialog;
            progressDialog = new ProgressDialog(this);
            progressDialog.setTitle(getString(R.string.progressdialog_title));
            progressDialog.setMessage(getString(R.string.progressdialog_message));

            if (withProgress) {
                progressDialog.show();
            }

            call.enqueue(new Callback<>() {
                @Override
                public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                    postItemList = response.body();
                    //binding.recyclerView.setHasFixedSize(true);
                    binding.recyclerView.setAdapter(new WordpressAdapter(context, postItemList));
                    if (withProgress) {
                        progressDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<List<Post>> call, Throwable t) {
                    if (withProgress) {
                        progressDialog.dismiss();
                    }
                }
            });
        } else {
            Snackbar.make(binding.swipeRefresh, "Can't connect to the Internet", Snackbar.LENGTH_INDEFINITE).show();
        }
    }
}