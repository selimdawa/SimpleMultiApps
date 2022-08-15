package com.flatcode.simplemultiapps.MultipleDelete;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.flatcode.simplemultiapps.Unit.THEME;
import com.flatcode.simplemultiapps.databinding.ActivityMultiDeleteBinding;

import java.util.ArrayList;
import java.util.Arrays;

public class MultiDeleteActivity extends AppCompatActivity {

    private ActivityMultiDeleteBinding binding;

    ArrayList<String> arrayList = new ArrayList<>();
    MultiDeleteAdapter adapter;

    Activity activity;
    Context context = activity = MultiDeleteActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        THEME.setThemeOfApp(context);
        super.onCreate(savedInstanceState);
        binding = ActivityMultiDeleteBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //Add values in array list
        arrayList.addAll(Arrays.asList("one", "two", "three", "four", "five", "sex", "seven"
                , "eight", "nine", "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen"));

        //Set layout manager
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        //Initialize adapter
        adapter = new MultiDeleteAdapter(context, activity, arrayList, binding.tvEmpty);
        //Set adapter
        binding.recyclerView.setAdapter(adapter);
    }
}