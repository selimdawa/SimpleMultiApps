package com.flatcode.simplemultiapps.LiveTV.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.flatcode.simplemultiapps.LiveTV.Adapter.ChannelAdapter;
import com.flatcode.simplemultiapps.LiveTV.Model.Channel;
import com.flatcode.simplemultiapps.LiveTV.Service.ChannelDataService;
import com.flatcode.simplemultiapps.R;
import com.flatcode.simplemultiapps.Unit.CLASS;
import com.flatcode.simplemultiapps.Unit.DATA;
import com.flatcode.simplemultiapps.Unit.THEME;
import com.flatcode.simplemultiapps.Unit.VOID;
import com.flatcode.simplemultiapps.databinding.ActivityLiveTvBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LiveTVActivity extends AppCompatActivity {

    private ActivityLiveTvBinding binding;

    ChannelAdapter bigSliderAdapter, newsChannelAdapter, sportsChannelAdapter, enterChannelAdapter;
    List<Channel> channelList, newsChannels, sportsChannel, enterChannel;
    ChannelDataService service;

    Context context = LiveTVActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        THEME.setThemeOfApp(context);
        super.onCreate(savedInstanceState);
        binding = ActivityLiveTvBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.toolbar.nameSpace.setText(R.string.live_tv);

        channelList = new ArrayList<>();
        service = new ChannelDataService(this);

        binding.bigSliderList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        bigSliderAdapter = new ChannelAdapter(channelList, "slider");
        binding.bigSliderList.setAdapter(bigSliderAdapter);

        getSliderData("http://" + DATA.IP_LIVE_TV + "/mytv/api.php?key=1A4mgi2rBHCJdqggsYVx&id=1&channels=all");
        getNewsChannels("http://" + DATA.IP_LIVE_TV + "/mytv/api.php?key=1A4mgi2rBHCJdqggsYVx&id=1&cat=News");
        getSportsChannel("http://" + DATA.IP_LIVE_TV + "/mytv/api.php?key=1A4mgi2rBHCJdqggsYVx&id=1&cat=Sports");
        getEnterChannel("http://" + DATA.IP_LIVE_TV + "/mytv/api.php?key=1A4mgi2rBHCJdqggsYVx&id=1&cat=Entertainment");

        binding.toolbar.categories.setOnClickListener(v -> VOID.Intent(context, CLASS.LIVE_TV_CATEGORIES));

        binding.more.setOnClickListener(v -> {
            Intent i = new Intent(v.getContext(), CategoryDetailsActivity.class);
            i.putExtra("categoryName", "News");
            v.getContext().startActivity(i);
        });

        binding.more2.setOnClickListener(v -> {
            Intent i = new Intent(v.getContext(), CategoryDetailsActivity.class);
            i.putExtra("categoryName", "Sports");
            v.getContext().startActivity(i);
        });

        binding.more3.setOnClickListener(v -> {
            Intent i = new Intent(v.getContext(), CategoryDetailsActivity.class);
            i.putExtra("categoryName", "Entertainment");
            v.getContext().startActivity(i);
        });
    }

    public void getSliderData(String url) {
        service.getChannelData(url, new ChannelDataService.OnDataResponse() {
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

                        channelList.add(c);
                        bigSliderAdapter.notifyDataSetChanged();

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

    public void getNewsChannels(String url) {
        newsChannels = new ArrayList<>();
        binding.newsChannelList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        newsChannelAdapter = new ChannelAdapter(newsChannels, "category");
        binding.newsChannelList.setAdapter(newsChannelAdapter);

        service.getChannelData(url, new ChannelDataService.OnDataResponse() {
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

                        newsChannels.add(c);
                        newsChannelAdapter.notifyDataSetChanged();

                        //Log.d(TAG, "onResponse: " + c.toString());
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

    public void getSportsChannel(String url) {
        sportsChannel = new ArrayList<>();
        binding.sportsChannelList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        sportsChannelAdapter = new ChannelAdapter(sportsChannel, "category");
        binding.sportsChannelList.setAdapter(sportsChannelAdapter);

        service.getChannelData(url, new ChannelDataService.OnDataResponse() {
            @Override
            public void onResponse(JSONObject response) {
                //Log.d(TAG, "onResponse: sports" + response.toString());
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
                        sportsChannel.add(c);
                        sportsChannelAdapter.notifyDataSetChanged();

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

    public void getEnterChannel(String url) {
        enterChannel = new ArrayList<>();
        binding.enterChannelList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        enterChannelAdapter = new ChannelAdapter(enterChannel, "category");
        binding.enterChannelList.setAdapter(enterChannelAdapter);

        service.getChannelData(url, new ChannelDataService.OnDataResponse() {
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

                        enterChannel.add(c);
                        enterChannelAdapter.notifyDataSetChanged();

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