package com.flatcode.simplemultiapps.BloggerApp.Activity;

import static javax.xml.transform.OutputKeys.ENCODING;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.flatcode.simplemultiapps.BloggerApp.Adapter.CommentAdapter;
import com.flatcode.simplemultiapps.BloggerApp.Adapter.LabelAdapter;
import com.flatcode.simplemultiapps.BloggerApp.Model.Comment;
import com.flatcode.simplemultiapps.BloggerApp.Model.Label;
import com.flatcode.simplemultiapps.R;
import com.flatcode.simplemultiapps.Unit.DATA;
import com.flatcode.simplemultiapps.Unit.THEME;
import com.flatcode.simplemultiapps.databinding.ActivityPostDetailsBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PostDetailsActivity extends AppCompatActivity {

    private ActivityPostDetailsBinding binding;

    private String postId;
    private ArrayList<Label> list;
    private LabelAdapter adapter;

    private ArrayList<Comment> comments;
    private CommentAdapter commentAdapter;

    private Context context = PostDetailsActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        THEME.setThemeOfApp(context);
        super.onCreate(savedInstanceState);
        binding = ActivityPostDetailsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.toolbar.nameSpace.setText(R.string.post_details);
        binding.toolbar.back.setVisibility(View.VISIBLE);
        binding.toolbar.back.setOnClickListener(v -> onBackPressed());

        postId = getIntent().getStringExtra("postId");

        loadPostDetails();
    }

    private void loadPostDetails() {
        String url = "https://www.googleapis.com/blogger/v3/blogs/" + DATA.BLOG_ID + "/posts/"
                + postId + "?key=" + DATA.BLOGGER_API;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {

            try {
                JSONObject jsonObject = new JSONObject(response);

                String id = jsonObject.getString("id");
                String title = jsonObject.getString("title");
                String content = jsonObject.getString("content");
                String published = jsonObject.getString("published");
                String url_ = jsonObject.getString("url");
                String displayName = jsonObject.getJSONObject("author").getString("displayName");

                String gmtDate = published;
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy K:mm a");
                String formattedDate = DATA.EMPTY;
                try {
                    Date date = dateFormat.parse(gmtDate);
                    formattedDate = dateFormat2.format(date);
                } catch (Exception e) {
                    formattedDate = published;
                    e.printStackTrace();
                }

                binding.title.setText(title);
                binding.publishInfo.setText(MessageFormat.format("By {0}{1}{2}", displayName, DATA.SPACE, formattedDate));
                binding.webView.loadDataWithBaseURL(null, content, "text/html", ENCODING, null);

                try {
                    list = new ArrayList<>();
                    list.clear();

                    JSONArray jsonArray = jsonObject.getJSONArray("labels");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        String label = jsonArray.getString(i);
                        Label label1 = new Label(label);
                        list.add(label1);
                    }
                    adapter = new LabelAdapter(context, list);
                    binding.recyclerLabels.setAdapter(adapter);

                } catch (Exception e) {
                }

                loadComments();

            } catch (Exception e) {
                Toast.makeText(context, DATA.EMPTY + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }, error -> Toast.makeText(context, DATA.EMPTY + error.getMessage(), Toast.LENGTH_SHORT).show());

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    private void loadComments() {
        String url = "https://www.googleapis.com/blogger/v3/blogs/" + DATA.BLOG_ID + "/posts/"
                + postId + "/comments?key=" + DATA.BLOGGER_API;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, this::onResponse, error -> {
        });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    private void onResponse(String response) {
        comments = new ArrayList<>();
        comments.clear();

        try {
            JSONObject jsonObject = new JSONObject(response);

            JSONArray jsonArray = jsonObject.getJSONArray("items");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                String id = jsonObject1.getString("id");
                String published = jsonObject1.getString("published");
                String content = jsonObject1.getString("content");
                String displayName = jsonObject1.getJSONObject("author").getString("displayName");
                String profileImage = "http:" + jsonObject1.getJSONObject("author").getJSONObject("image").getString("url");

                Comment comment = new Comment(DATA.EMPTY + id, DATA.EMPTY + displayName,
                        DATA.EMPTY + profileImage, DATA.EMPTY + published,
                        DATA.EMPTY + content);

                comments.add(comment);
            }

            commentAdapter = new CommentAdapter(context, comments);
            binding.recyclerComments.setAdapter(commentAdapter);

        } catch (Exception e) {
        }
    }
}