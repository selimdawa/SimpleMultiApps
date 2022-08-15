package com.flatcode.simplemultiapps.NewsApp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.flatcode.simplemultiapps.NewsApp.Model.NewsHeadlines;
import com.flatcode.simplemultiapps.NewsApp.SelectListener;
import com.flatcode.simplemultiapps.Unit.VOID;
import com.flatcode.simplemultiapps.databinding.ItemNewsBinding;

import java.util.List;

public class NewsAppAdapter extends RecyclerView.Adapter<NewsAppViewHolder> {

    private ItemNewsBinding binding;
    private final Context context;
    public List<NewsHeadlines> headlines;
    private SelectListener listener;

    public NewsAppAdapter(Context context, List<NewsHeadlines> headlines, SelectListener listener) {
        this.context = context;
        this.headlines = headlines;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NewsAppViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemNewsBinding.inflate(LayoutInflater.from(context), parent, false);
        return new NewsAppViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAppViewHolder holder, int position) {
        NewsHeadlines list = headlines.get(position);
        String content = list.getTitle();
        String source = list.getSource().getName();
        String urlToImage = list.getUrlToImage();

        if (content != null)
        holder.content.setText(content);
        if (source != null)
            holder.source.setText(source);
        if (urlToImage != null)
            VOID.Glide(context, urlToImage, holder.image);

        holder.card.setOnClickListener(v -> listener.onNewsClicked(list));
    }

    @Override
    public int getItemCount() {
        return headlines.size();
    }
}