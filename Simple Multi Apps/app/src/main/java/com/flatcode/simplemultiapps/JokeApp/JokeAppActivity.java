package com.flatcode.simplemultiapps.JokeApp;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.flatcode.simplemultiapps.R;
import com.flatcode.simplemultiapps.Unit.THEME;
import com.flatcode.simplemultiapps.databinding.ActivityJokeAppBinding;

import java.util.ArrayList;

public class JokeAppActivity extends AppCompatActivity {

    private ActivityJokeAppBinding binding;

    RecyclerView jokeList;
    JokeCategoriesAdapter catAdapter;

    Context context = JokeAppActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        THEME.setThemeOfApp(context);
        super.onCreate(savedInstanceState);
        binding = ActivityJokeAppBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.toolbar.nameSpace.setText(R.string.joke);

        ArrayList<String> cats = new ArrayList<>();
        cats.add("Any");
        cats.add("Programming");
        cats.add("Dark");
        cats.add("Spooky");
        cats.add("Misc");
        cats.add("Pun");
        cats.add("Christmas");

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        catAdapter = new JokeCategoriesAdapter(context, cats);
        binding.recyclerView.setAdapter(catAdapter);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction().replace(R.id.fragment, new JokesFragment("https://v2.jokeapi.dev/joke/Any?amount=10"));
        transaction.commit();
    }
}