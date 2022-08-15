package com.flatcode.simplemultiapps.JokeApp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.flatcode.simplemultiapps.databinding.ItemJokeBinding;

import java.util.List;

public class JokeAdapter extends RecyclerView.Adapter<JokeAdapter.ViewHolder> {

    private ItemJokeBinding binding;
    private final Context context;
    List<Joke> jokes;

    public JokeAdapter(Context context,List<Joke> jokes) {
        this.context = context;
        this.jokes = jokes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemJokeBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (jokes.get(position).getType().equals("single")) {
            holder.firstLine.setText(jokes.get(position).getJoke());
            holder.secondLine.setVisibility(View.GONE);
        } else {
            holder.firstLine.setText(jokes.get(position).getSetup());
            holder.secondLine.setVisibility(View.VISIBLE);
            holder.secondLine.setText(jokes.get(position).getDelivery());
        }
    }

    @Override
    public int getItemCount() {
        return jokes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView firstLine, secondLine;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            firstLine = binding.firstLine;
            secondLine = binding.secondLine;
        }
    }
}
