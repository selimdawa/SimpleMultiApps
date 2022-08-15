package com.flatcode.simplemultiapps.LiveTV.Activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.flatcode.simplemultiapps.LiveTV.Adapter.CategoryAdapter;
import com.flatcode.simplemultiapps.LiveTV.Model.Category;
import com.flatcode.simplemultiapps.LiveTV.Service.ChannelDataService;
import com.flatcode.simplemultiapps.R;
import com.flatcode.simplemultiapps.Unit.DATA;
import com.flatcode.simplemultiapps.Unit.THEME;
import com.flatcode.simplemultiapps.databinding.ActivityLiveTvCategoriesBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CategoriesActivity extends AppCompatActivity {

    private ActivityLiveTvCategoriesBinding binding;

    CategoryAdapter categoryAdapter;
    List<Category> categoryList;
    ChannelDataService dataService;

    Context context = CategoriesActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        THEME.setThemeOfApp(context);
        super.onCreate(savedInstanceState);
        binding = ActivityLiveTvCategoriesBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.toolbar.nameSpace.setText(R.string.categories);

        dataService = new ChannelDataService(this);
        categoryList = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(context, categoryList);
        binding.recyclerView.setAdapter(categoryAdapter);

        binding.toolbar.back.setOnClickListener(v -> onBackPressed());

        dataService.getChannelData("http://" + DATA.IP_LIVE_TV + "/mytv/api.php?key=1A4mgi2rBHCJdqggsYVx&id=1&categories=all", new ChannelDataService.OnDataResponse() {
            @Override
            public void onResponse(JSONObject response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject categoryData = response.getJSONObject(String.valueOf(i));

                        Category category = new Category(categoryData.getInt("id"), categoryData.getString("name"), categoryData.getString("image_url"));
                        categoryList.add(category);
                        categoryAdapter.notifyDataSetChanged();
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