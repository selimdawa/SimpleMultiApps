package com.flatcode.simplemultiapps.LiveTV.Activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.flatcode.simplemultiapps.LiveTV.Adapter.ChannelAdapter;
import com.flatcode.simplemultiapps.LiveTV.Model.Category;
import com.flatcode.simplemultiapps.LiveTV.Model.Channel;
import com.flatcode.simplemultiapps.LiveTV.Service.ChannelDataService;
import com.flatcode.simplemultiapps.Unit.DATA;
import com.flatcode.simplemultiapps.Unit.THEME;
import com.flatcode.simplemultiapps.databinding.ActivityLiveTvCategoryDetailsBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CategoryDetailsActivity extends AppCompatActivity {

    private ActivityLiveTvCategoryDetailsBinding binding;

    ChannelAdapter adapter;
    List<Channel> channels;
    ChannelDataService dataService;

    Context context = CategoryDetailsActivity.this;

    String categoryName = null;
    Category category = null;
    String url = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        THEME.setThemeOfApp(context);
        super.onCreate(savedInstanceState);
        binding = ActivityLiveTvCategoryDetailsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        channels = new ArrayList<>();
        dataService = new ChannelDataService(this);

        categoryName = getIntent().getStringExtra("categoryName");

        if (categoryName == null || categoryName.equals("")) {
            category = (Category) getIntent().getSerializableExtra("category");
            binding.toolbar.nameSpace.setText(category.getName());
            url = "http://" + DATA.IP_LIVE_TV + "/mytv/api.php?key=1A4mgi2rBHCJdqggsYVx&id=1&cat=" + category.getName();
        } else {
            binding.toolbar.nameSpace.setText(categoryName);
            url = "http://" + DATA.IP_LIVE_TV + "/mytv/api.php?key=1A4mgi2rBHCJdqggsYVx&id=1&cat=" + categoryName;
        }

        adapter = new ChannelAdapter(channels, "details");
        binding.recyclerView.setAdapter(adapter);

        binding.toolbar.back.setOnClickListener(v -> onBackPressed());

        dataService.getChannelData(url, new ChannelDataService.OnDataResponse() {
            @Override
            public void onResponse(JSONObject response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject channelData = response.getJSONObject(String.valueOf(i));
                        Channel c = new Channel();
                        c.setId(channelData.getInt("id"));
                        c.setName(channelData.getString("name"));
                        c.setDescription(channelData.getString("description"));
                        c.setThumbnail(channelData.getString("thumbnail"));
                        c.setLive_url(channelData.getString("live_url"));
                        c.setFacebook(channelData.getString("facebook"));
                        c.setTwitter(channelData.getString("twitter"));
                        c.setYoutube(channelData.getString("youtube"));
                        c.setWebsite(channelData.getString("website"));
                        c.setCategory(channelData.getString("category"));

                        channels.add(c);
                        adapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onError(String error) {
            }
        });
    }
}