package com.flatcode.simplemultiapps.RandomImgGenerating;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.flatcode.simplemultiapps.R;
import com.flatcode.simplemultiapps.Unit.CLASS;
import com.flatcode.simplemultiapps.Unit.DATA;
import com.flatcode.simplemultiapps.Unit.THEME;
import com.flatcode.simplemultiapps.Unit.VOID;
import com.flatcode.simplemultiapps.databinding.ActivityRandomImgGeneratingBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class RandomImgGeneratingActivity extends AppCompatActivity {

    private ActivityRandomImgGeneratingBinding binding;
    private Context context = RandomImgGeneratingActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        THEME.setThemeOfApp(context);
        super.onCreate(savedInstanceState);
        binding = ActivityRandomImgGeneratingBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.toolbar.nameSpace.setText(getString(R.string.random_img_generating));

        getImage(DATA.API_RANDOM_IMAGE);

        binding.refreshBtn.setOnClickListener(v -> getImage(DATA.API_RANDOM_IMAGE));
    }

    public void getImage(String url) {
        // extract the json data
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            try {
                JSONObject kittyData = response.getJSONObject(0);
                String catUrl = kittyData.getString("url");
                VOID.Glide(context, catUrl, binding.kittyImage);

                binding.downloadBtn.setOnClickListener(v -> {
                    Uri catUri = Uri.parse(catUrl);
                    Intent browser = new Intent(Intent.ACTION_VIEW, catUri);
                    startActivity(browser);
                });

                binding.infoBtn.setOnClickListener(v -> {
                    try {
                        JSONArray breedsInfo = kittyData.getJSONArray("breeds");
                        if (breedsInfo.isNull(0)) {
                            Toast.makeText(context, "Data Not Found.", Toast.LENGTH_SHORT).show();
                        } else {
                            JSONObject breedsData = breedsInfo.getJSONObject(0);
                            Intent i = new Intent(getApplicationContext(), CLASS.IMAGE_INFO);
                            i.putExtra("name", breedsData.getString("name"));
                            i.putExtra("origin", breedsData.getString("origin"));
                            i.putExtra("desc", breedsData.getString("description"));
                            i.putExtra("temp", breedsData.getString("temperament"));
                            i.putExtra("wikiUrl", breedsData.getString("wikipedia_url"));
                            i.putExtra("moreLink", breedsData.getString("vcahospitals_url"));
                            i.putExtra("imageUrl", catUrl);
                            startActivity(i);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show());
        queue.add(arrayRequest);
    }
}