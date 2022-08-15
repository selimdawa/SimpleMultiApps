package com.flatcode.simplemultiapps.Wordpress.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.flatcode.simplemultiapps.R;
import com.flatcode.simplemultiapps.Unit.THEME;
import com.flatcode.simplemultiapps.Wordpress.Adapter.WordpressAdapter;
import com.flatcode.simplemultiapps.Wordpress.Model.Post;
import com.flatcode.simplemultiapps.Wordpress.Sqlite.PostDB;
import com.flatcode.simplemultiapps.Wordpress.Util.InternetConnection;
import com.flatcode.simplemultiapps.Wordpress.Util.WPApiService;
import com.flatcode.simplemultiapps.Wordpress.Util.WordPressClient;
import com.flatcode.simplemultiapps.databinding.ActivityWordpressFavoritesBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WordpressFavoritesActivity extends AppCompatActivity {

    private ActivityWordpressFavoritesBinding binding;
    Context context = WordpressFavoritesActivity.this;

    private View favPost;
    private List<Post> sqLitePostList;
    private List<Post> postList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        THEME.setThemeOfApp(context);
        super.onCreate(savedInstanceState);
        binding = ActivityWordpressFavoritesBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        favPost = binding.item;

        binding.toolbar.back.setVisibility(View.VISIBLE);
        binding.toolbar.back.setOnClickListener(v -> onBackPressed());
        binding.toolbar.nameSpace.setText(R.string.favorites);

        sqLitePostList = PostDB.getInstance(getApplicationContext()).getAllDbPosts();
        setFavListContent(true, sqLitePostList);
    }

    public void setFavListContent(final boolean withProgress, final List<Post> favPostList) {
        if (InternetConnection.checkInternetConnection(getApplicationContext())) {
            WPApiService api = WordPressClient.getApiService();
            Call<List<Post>> call = api.getPosts();

            final ProgressDialog progressDialog;
            progressDialog = new ProgressDialog(context);
            progressDialog.setTitle(getString(R.string.progressdialog_title));
            progressDialog.setMessage(getString(R.string.progressdialog_message));

            if (withProgress) {
                progressDialog.show();
            }

            call.enqueue(new Callback<>() {
                @Override
                public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                    ArrayList<Post> myList = new ArrayList<Post>();
                    postList = response.body();
                    for (Post post : postList) {
                        for (Post dbPost : favPostList) {
                            if (post.getId() == dbPost.getWpPostId()) {
                                myList.add(post);
                            }
                        }
                    }
                    //binding.recyclerView.setHasFixedSize(true);
                    binding.recyclerView.setAdapter(new WordpressAdapter(getApplicationContext(), myList));

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
            Snackbar.make(favPost, "Can't connect to the Internet", Snackbar.LENGTH_INDEFINITE).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        sqLitePostList = PostDB.getInstance(getApplicationContext()).getAllDbPosts();
        setFavListContent(true, sqLitePostList);
    }
}