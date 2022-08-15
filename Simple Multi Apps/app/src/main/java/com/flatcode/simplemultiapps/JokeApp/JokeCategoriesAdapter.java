package com.flatcode.simplemultiapps.JokeApp;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.flatcode.simplemultiapps.R;
import com.flatcode.simplemultiapps.Unit.DATA;
import com.flatcode.simplemultiapps.databinding.ItemJokeCategoryBinding;

import java.util.List;

public class JokeCategoriesAdapter extends RecyclerView.Adapter<JokeCategoriesAdapter.ViewHolder> {

    private ItemJokeCategoryBinding binding;
    private final Context context;
    List<String> categories;
    int selected_position = 0;

    public JokeCategoriesAdapter(Context context, List<String> cats) {
        this.context = context;
        this.categories = cats;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemJokeCategoryBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.catName.setText(categories.get(position));
        if (selected_position == position) {
            holder.card.setBackgroundResource(R.drawable.button_profile2);
            holder.catName.setTextColor(Color.WHITE);
        } else {
            holder.card.setBackgroundResource(R.drawable.button_profile);
            holder.catName.setTextColor(Color.BLACK);
        }
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView catName;
        CardView card;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            catName = binding.categoriesName;
            card = binding.card;
        }

        @Override
        public void onClick(View v) {
            // Below line is just like a safety check, because sometimes holder could be null,
            // in that case, getAdapterPosition() will return RecyclerView.NO_POSITION
            if (getAdapterPosition() == RecyclerView.NO_POSITION) return;

            // Updating old as well as new positions
            notifyItemChanged(selected_position);
            selected_position = getAdapterPosition();
            notifyItemChanged(selected_position);

            // Do your another stuff for your onClick
            if (categories.get(selected_position) == "Any") {
                loadFragment(new JokesFragment(DATA.JOKE_URL + "Any?amount=10"), v);
            }
            if (categories.get(selected_position) == "Programming") {
                loadFragment(new JokesFragment(DATA.JOKE_URL + "Programming?amount=10"), v);
            }
            if (categories.get(selected_position) == "Dark") {
                loadFragment(new JokesFragment(DATA.JOKE_URL + "Dark?amount=10"), v);
            }

            if (categories.get(selected_position) == "Spooky") {
                loadFragment(new JokesFragment(DATA.JOKE_URL + "Spooky?amount=10"), v);
            }

            if (categories.get(selected_position) == "Misc") {
                loadFragment(new JokesFragment(DATA.JOKE_URL + "Misc?amount=10"), v);
            }

            if (categories.get(selected_position) == "Pun") {
                loadFragment(new JokesFragment(DATA.JOKE_URL + "Programming?amount=10"), v);
            }

            if (categories.get(selected_position) == "Christmas") {
                loadFragment(new JokesFragment(DATA.JOKE_URL + "Christmas?amount=10"), v);
            }

        }
    }

    public void loadFragment(Fragment fragment, View v) {
        AppCompatActivity activity = (AppCompatActivity) v.getContext();
        FragmentManager manager = activity.getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction().replace(R.id.fragment, fragment);
        transaction.commit();
    }
}