package com.flatcode.simplemultiapps.JokeApp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.flatcode.simplemultiapps.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JokesFragment extends Fragment {

    String jokesUrl;
    RecyclerView jokesList;
    JokeAdapter adapter;
    List<Joke> jokes;

    public JokesFragment(String Url) {
        this.jokesUrl = Url;
        jokes = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_jokes, container, false);
        jokesList = view.findViewById(R.id.jokesList);
        adapter = new JokeAdapter(getContext(), jokes);
        jokesList.setLayoutManager(new LinearLayoutManager(getContext()));
        jokesList.setAdapter(adapter);
        getJokes(jokesUrl);
        return view;
    }

    public void getJokes(String url) {
        // fetch jokes and populate the data
        RequestQueue queue = Volley.newRequestQueue(requireContext());
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                JSONArray jokesArray = response.getJSONArray("jokes");
                for (int i = 0; i < Integer.parseInt(response.getString("amount")); i++) {
                    JSONObject jokeData = jokesArray.getJSONObject(i);
                    Joke j = new Joke();
                    j.setType(jokeData.getString("type"));
                    if (jokeData.getString("type").equals("single")) {
                        j.setJoke(jokeData.getString("joke"));
                    } else {
                        j.setSetup(jokeData.getString("setup"));
                        j.setDelivery(jokeData.getString("delivery"));
                    }
                    jokes.add(j);
                    adapter.notifyDataSetChanged();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
        });
        queue.add(objectRequest);
    }
}