package com.flatcode.simplemultiapps.Wordpress.Activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.flatcode.simplemultiapps.R;
import com.flatcode.simplemultiapps.Unit.THEME;
import com.flatcode.simplemultiapps.Wordpress.Model.Media;
import com.flatcode.simplemultiapps.Wordpress.Sqlite.PostDB;
import com.flatcode.simplemultiapps.Wordpress.Util.InternetConnection;
import com.flatcode.simplemultiapps.Wordpress.Util.WPApiService;
import com.flatcode.simplemultiapps.Wordpress.Util.WordPressClient;
import com.flatcode.simplemultiapps.databinding.ActivityWordpressDetailsBinding;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WordpressDetailsActivity extends AppCompatActivity {

    private ActivityWordpressDetailsBinding binding;
    Context context = WordpressDetailsActivity.this;

    View parentView;
    boolean isItemSelected;

    public static Intent createIntent(Context context, int id, int featuredMedia, String title,
                                      String excerpt, String content) {
        Intent intent = new Intent(context, WordpressDetailsActivity.class);
        //Setzen des wertes aus dem Intent
        intent.putExtra("postId", id);
        intent.putExtra("featuredMedia", featuredMedia);
        intent.putExtra("postExcerpt", excerpt);
        intent.putExtra("postTitle", title);
        intent.putExtra("postContent", content);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //THEME.setThemeOfApp(context);
        super.onCreate(savedInstanceState);
        binding = ActivityWordpressDetailsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //Get Intent
        int id = (int) getIntent().getSerializableExtra("postId");
        int featuredMedia = (int) getIntent().getSerializableExtra("featuredMedia");
        String title = getIntent().getSerializableExtra("postTitle").toString();
        String content = getIntent().getSerializableExtra("postContent").toString().replaceAll("\\\\n", "<br>").replaceAll("\\\\r", "").replaceAll("\\\\", "");

        initToolbar(title, id);
        initWebView(content);

        //Call Media
        if (InternetConnection.checkInternetConnection(getApplicationContext())) {
            WPApiService api = WordPressClient.getApiService();
            Call<Media> call = api.getPostThumbnail(featuredMedia);
            call.enqueue(new Callback<>() {
                @Override
                public void onResponse(Call<Media> call, Response<Media> response) {
                    if (response.code() != 404) {
                        Media media = response.body();
                        String mediaUrl = media.getGuid().get("rendered").toString().replaceAll("\"", "");

                        Glide.with(getApplicationContext()).load(mediaUrl)
                                .thumbnail(0.5f)
                                .centerCrop()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(binding.postBackdrop);
                    } else {
                    }
                }

                @Override
                public void onFailure(Call<Media> call, Throwable t) {
                }
            });

        } else {
            Snackbar.make(parentView, "Can't connect to the Internet", Snackbar.LENGTH_INDEFINITE).show();
        }
        binding.content.postTitle.setText(title);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //Get Intent
        int id = (int) getIntent().getSerializableExtra("postId");
        String title = getIntent().getSerializableExtra("postTitle").toString();
        String excerpt = getIntent().getSerializableExtra("postExcerpt").toString();
        String content = getIntent().getSerializableExtra("postContent").toString().replaceAll("\\\\n", "").replaceAll("\\\\r", "").replaceAll("\\\\", "");

        //Toggle Navigation icon
        if (!isItemSelected) {
            item.setIcon(getResources().getDrawable(R.drawable.ic_heart_selected, getTheme()));
            isItemSelected = true;
            PostDB.getInstance(getApplicationContext()).insert(id, title, excerpt, isItemSelected);
        } else {
            item.setIcon(getResources().getDrawable(R.drawable.ic_heart_unselected, getTheme()));
            isItemSelected = false;
            PostDB.getInstance(getApplicationContext()).delete(id);
        }

        return super.onOptionsItemSelected(item);
    }


    private class MyWebView extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(request.getUrl().toString());
            return true;
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }
    }


    private void initToolbar(String title, int id) {
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        setSupportActionBar(binding.postToolbar);
        initCollapsingToolbar(title);
        isItemSelected = PostDB.getInstance(getApplicationContext()).getDbPostIsFav(id);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.postToolbar.setNavigationOnClickListener(v -> finish());
    }

    //Init CollapsingToolbarLayout
    private void initCollapsingToolbar(final String title) {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.post_collapsing_toolbarLayout);
        collapsingToolbar.setTitle(title);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView(String content) {
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(WordpressDetailsActivity.this);
        progressDialog.setTitle(getString(R.string.progressdialog_title));
        progressDialog.setMessage(getString(R.string.progressdialog_message));

        //Set Html content
        content = "<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\" />" +
                "<script src=\"prism.js\"></script>" +
                "<div class=\"content\">" + content + "</div>";

        binding.content.webview.getSettings().setLoadsImagesAutomatically(true);
        binding.content.webview.getSettings().setJavaScriptEnabled(true);
        binding.content.webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressDialog.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressDialog.dismiss();
            }
        });

        binding.content.webview.loadDataWithBaseURL("file:///android_asset/*", content, "text/html; charset=utf-8", "UTF-8", null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_to_favorite_menu, menu);
        if (isItemSelected) {
            menu.findItem(R.id.add_as_favorite).setIcon(getResources().getDrawable(R.drawable.ic_heart_selected, getTheme()));
        } else {
            menu.findItem(R.id.add_as_favorite).setIcon(getResources().getDrawable(R.drawable.ic_heart_unselected, getTheme()));
        }
        return true;
    }
}