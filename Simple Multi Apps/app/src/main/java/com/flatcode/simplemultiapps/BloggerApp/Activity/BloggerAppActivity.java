package com.flatcode.simplemultiapps.BloggerApp.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.flatcode.simplemultiapps.BloggerApp.Adapter.PostAdapter;
import com.flatcode.simplemultiapps.BloggerApp.Model.Post;
import com.flatcode.simplemultiapps.R;
import com.flatcode.simplemultiapps.Unit.CLASS;
import com.flatcode.simplemultiapps.Unit.DATA;
import com.flatcode.simplemultiapps.Unit.THEME;
import com.flatcode.simplemultiapps.Unit.VOID;
import com.flatcode.simplemultiapps.databinding.ActivityBloggerAppBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class BloggerAppActivity extends AppCompatActivity {

    private ActivityBloggerAppBinding binding;

    private String url = DATA.EMPTY;
    private String nextToken = DATA.EMPTY;
    private boolean isSearch = false;

    private ArrayList<Post> posts;
    private PostAdapter adapter;

    private ProgressDialog dialog;

    private Context context = BloggerAppActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        THEME.setThemeOfApp(context);
        super.onCreate(savedInstanceState);
        binding = ActivityBloggerAppBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.toolbar.nameSpace.setText(getString(R.string.blogger_name));

        binding.toolbar.pages.setOnClickListener(v -> VOID.Intent(context, CLASS.BLOGGER_PAGES));
        binding.toolbar.search.setOnClickListener(v -> {
            binding.toolbar.toolbar.setVisibility(View.GONE);
            binding.toolbar.toolbarSearch.setVisibility(View.VISIBLE);
            DATA.searchStatus = true;
        });
        binding.toolbar.close.setOnClickListener(v -> onBackPressed());

        dialog = new ProgressDialog(context);
        dialog.setTitle("Please wait...");

        posts = new ArrayList<>();
        posts.clear();

        loadPosts();

        binding.loadMore.setOnClickListener(v -> {
            String query = binding.toolbar.textSearch.getText().toString().trim();
            if (TextUtils.isEmpty(query)) {
                loadPosts();
            } else {
                searchPosts(query);
            }
        });

        binding.toolbar.postSearch.setOnClickListener(v -> {
            nextToken = DATA.EMPTY;
            url = DATA.EMPTY;

            posts = new ArrayList<>();
            posts.clear();

            String query = binding.toolbar.textSearch.getText().toString().trim();
            if (TextUtils.isEmpty(query)) {
                loadPosts();
            } else {
                searchPosts(query);
            }
        });
    }

    private void searchPosts(String query) {
        isSearch = true;
        dialog.show();

        if (nextToken.equals(DATA.EMPTY)) {
            url = "https://www.googleapis.com/blogger/v3/blogs/" + DATA.BLOG_ID + "/posts/search?q="
                    + query + "&key=" + DATA.BLOGGER_API;
        } else if (nextToken.equals("end")) {
            Toast.makeText(context, "No more posts...", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
            return;
        } else {
            url = "https://www.googleapis.com/blogger/v3/blogs/" + DATA.BLOG_ID + "/posts/search?q="
                    + query + "&pageToken=" + nextToken + "&key=" + DATA.BLOGGER_API;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
            dialog.dismiss();

            try {
                JSONObject jsonObject = new JSONObject(response);
                try {
                    nextToken = jsonObject.getString("nextPageToken");
                } catch (Exception e) {
                    Toast.makeText(context, "Reached end of page...", Toast.LENGTH_SHORT).show();
                    nextToken = "end";
                }

                JSONArray jsonArray = jsonObject.getJSONArray("items");

                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String id = jsonObject1.getString("id");
                        String title = jsonObject1.getString("title");
                        String content = jsonObject1.getString("content");
                        String published = jsonObject1.getString("published");
                        String updated = jsonObject1.getString("updated");
                        String url = jsonObject1.getString("url");
                        String selfLink = jsonObject1.getString("selfLink");
                        String authorName = jsonObject1.getJSONObject("author").getString("displayName");
                        //String image = jsonObject1.getJSONObject("author").getString("image");

                        Post post = new Post(DATA.EMPTY + authorName, DATA.EMPTY + content,
                                DATA.EMPTY + id, DATA.EMPTY + published,
                                DATA.EMPTY + selfLink, DATA.EMPTY + title,
                                DATA.EMPTY + updated, DATA.EMPTY + url);

                        posts.add(post);

                    } catch (Exception e) {
                        Toast.makeText(context, DATA.EMPTY + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                adapter = new PostAdapter(context, posts);
                binding.recyclerView.setAdapter(adapter);
                dialog.dismiss();
            } catch (Exception e) {
                Toast.makeText(context, DATA.EMPTY + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }, error -> {
            Toast.makeText(context, DATA.EMPTY + error.getMessage(), Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    private void loadPosts() {
        isSearch = false;
        dialog.show();

        if (nextToken.equals(DATA.EMPTY)) {
            url = "https://www.googleapis.com/blogger/v3/blogs/" + DATA.BLOG_ID + "/posts?maxResults="
                    + DATA.MAX_POST_RESULTS + "&key=" + DATA.BLOGGER_API;
        } else if (nextToken.equals("end")) {
            Toast.makeText(context, "No more posts...", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
            return;
        } else {
            url = "https://www.googleapis.com/blogger/v3/blogs/" + DATA.BLOG_ID + "/posts?maxResults="
                    + DATA.MAX_POST_RESULTS + "&pageToken=" + nextToken + "&key=" + DATA.BLOGGER_API;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
            dialog.dismiss();

            try {
                JSONObject jsonObject = new JSONObject(response);

                try {
                    nextToken = jsonObject.getString("nextPageToken");
                } catch (Exception e) {
                    Toast.makeText(context, "Reached end of page...", Toast.LENGTH_SHORT).show();
                    nextToken = "end";
                }

                JSONArray jsonArray = jsonObject.getJSONArray("items");

                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String id = jsonObject1.getString("id");
                        String title = jsonObject1.getString("title");
                        String content = jsonObject1.getString("content");
                        String published = jsonObject1.getString("published");
                        String updated = jsonObject1.getString("updated");
                        String url = jsonObject1.getString("url");
                        String selfLink = jsonObject1.getString("selfLink");
                        String authorName = jsonObject1.getJSONObject("author").getString("displayName");
                        String image = jsonObject1.getJSONObject("author").getString("image");

                        Post post = new Post(DATA.EMPTY + authorName, DATA.EMPTY + content,
                                DATA.EMPTY + id, DATA.EMPTY + published, DATA.EMPTY
                                + selfLink, DATA.EMPTY + title, DATA.EMPTY + updated,
                                DATA.EMPTY + url);

                        posts.add(post);

                    } catch (Exception e) {
                        Toast.makeText(context, DATA.EMPTY + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                adapter = new PostAdapter(context, posts);
                binding.recyclerView.setAdapter(adapter);
                dialog.dismiss();
            } catch (Exception e) {
                Toast.makeText(context, DATA.EMPTY + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }, error -> {
            Toast.makeText(context, DATA.EMPTY + error.getMessage(), Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        if (DATA.searchStatus) {
            binding.toolbar.toolbar.setVisibility(View.VISIBLE);
            binding.toolbar.toolbarSearch.setVisibility(View.GONE);
            DATA.searchStatus = false;
            binding.toolbar.textSearch.setText(DATA.EMPTY);
        } else
            super.onBackPressed();
    }
}