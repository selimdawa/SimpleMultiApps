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
import com.flatcode.simplemultiapps.R;
import com.flatcode.simplemultiapps.Unit.DATA;
import com.flatcode.simplemultiapps.Unit.THEME;
import com.flatcode.simplemultiapps.databinding.ActivityPageDetailsBinding;

import org.json.JSONObject;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PageDetailsActivity extends AppCompatActivity {

    private ActivityPageDetailsBinding binding;

    String pageId;

    Context context = PageDetailsActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        THEME.setThemeOfApp(context);
        super.onCreate(savedInstanceState);
        binding = ActivityPageDetailsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.toolbar.nameSpace.setText(getString(R.string.page_details));
        binding.toolbar.back.setVisibility(View.VISIBLE);
        binding.toolbar.back.setOnClickListener(v -> onBackPressed());

        pageId = getIntent().getStringExtra("pageId");

        loadPageDetails();
    }

    private void loadPageDetails() {
        String url = "https://www.googleapis.com/blogger/v3/blogs/" + DATA.BLOG_ID + "/pages/"
                + pageId + "?key=" + DATA.BLOGGER_API;

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
            } catch (Exception e) {
                Toast.makeText(context, DATA.EMPTY + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }, error -> Toast.makeText(context, DATA.EMPTY + error.getMessage(), Toast.LENGTH_SHORT).show());

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
}