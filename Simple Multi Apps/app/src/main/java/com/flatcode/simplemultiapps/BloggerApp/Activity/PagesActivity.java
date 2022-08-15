package com.flatcode.simplemultiapps.BloggerApp.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.flatcode.simplemultiapps.BloggerApp.Adapter.PagesAdapter;
import com.flatcode.simplemultiapps.BloggerApp.Model.Page;
import com.flatcode.simplemultiapps.R;
import com.flatcode.simplemultiapps.Unit.DATA;
import com.flatcode.simplemultiapps.Unit.THEME;
import com.flatcode.simplemultiapps.databinding.ActivityBloggerPagesBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class PagesActivity extends AppCompatActivity {

    private ActivityBloggerPagesBinding binding;

    private ArrayList<Page> pages;
    private PagesAdapter adapter;

    Context context = PagesActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        THEME.setThemeOfApp(context);
        super.onCreate(savedInstanceState);
        binding = ActivityBloggerPagesBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.toolbar.nameSpace.setText(R.string.blogger_pages);
        binding.toolbar.back.setVisibility(View.VISIBLE);
        binding.toolbar.back.setOnClickListener(v -> onBackPressed());

        loadPages();
    }

    private void loadPages() {
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setTitle("Please wait...");
        dialog.setMessage("Loading Pages");
        dialog.show();

        String url = "https://www.googleapis.com/blogger/v3/blogs/" + DATA.BLOG_ID + "/pages?key=" + DATA.BLOGGER_API;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
            dialog.dismiss();

            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("items");

                pages = new ArrayList<>();
                pages.clear();

                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String id = jsonObject1.getString("id");
                        String title = jsonObject1.getString("title");
                        String content = jsonObject1.getString("content");
                        String published = jsonObject1.getString("published");
                        String updated = jsonObject1.getString("updated");
                        String url_ = jsonObject1.getString("url");
                        String selfLink = jsonObject1.getString("selfLink");
                        String displayName = jsonObject1.getJSONObject("author").getString("displayName");
                        String image = jsonObject1.getJSONObject("author").getJSONObject("image").getString("url");

                        Page page = new Page(DATA.EMPTY + displayName, DATA.EMPTY + content,
                                DATA.EMPTY + id, DATA.EMPTY + published, DATA.EMPTY
                                + selfLink, DATA.EMPTY + title,
                                DATA.EMPTY + updated, DATA.EMPTY + url_);

                        pages.add(page);

                    } catch (Exception e) {
                        Toast.makeText(context, DATA.EMPTY + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                adapter = new PagesAdapter(context, pages);
                binding.recyclerView.setAdapter(adapter);
            } catch (Exception e) {
            }
        }, error -> {
            dialog.dismiss();
            Toast.makeText(context, DATA.EMPTY + error.getMessage(), Toast.LENGTH_SHORT).show();
        });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
}