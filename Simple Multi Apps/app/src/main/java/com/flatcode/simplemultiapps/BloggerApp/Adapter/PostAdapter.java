package com.flatcode.simplemultiapps.BloggerApp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.flatcode.simplemultiapps.BloggerApp.Model.Post;
import com.flatcode.simplemultiapps.R;
import com.flatcode.simplemultiapps.Unit.CLASS;
import com.flatcode.simplemultiapps.Unit.DATA;
import com.flatcode.simplemultiapps.Unit.VOID;
import com.flatcode.simplemultiapps.databinding.ItemBloggerBinding;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private ItemBloggerBinding binding;
    private final Context context;
    public ArrayList<Post> posts;

    public PostAdapter(Context context, ArrayList<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemBloggerBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post list = posts.get(position);
        String authorName = list.getAuthorName();
        String content = list.getContent();
        String id = list.getId();
        String published = list.getPublished();
        String selfLink = list.getSelfLink();
        String title = list.getTitle();
        String updated = list.getUpdated();
        String url = list.getUrl();

        Document document = Jsoup.parse(content);
        try {
            Elements elements = document.select("img");
            String image = elements.get(0).attr("src");
            VOID.Glide(context, image, holder.image);
        } catch (Exception e) {
            holder.image.setImageResource(R.color.image_profile);
        }

        String gmtDate = published;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy K:mm a");
        String formattedDate = DATA.EMPTY;
        try {
            Date date = dateFormat.parse(gmtDate);
            formattedDate = dateFormat2.format(date);
        } catch (Exception e) {
            formattedDate = published;
            e.printStackTrace();
        }

        holder.title.setText(title);
        holder.description.setText(document.text());
        holder.publishInfo.setText(MessageFormat.format("By {0}{1}{2}", authorName, DATA.SPACE, formattedDate));

        holder.itemView.setOnClickListener(v -> VOID.IntentExtra(context, CLASS.BLOGGER_POST_DETAILS, "postId", id));
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title, publishInfo, description;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = binding.title;
            publishInfo = binding.publishInfo;
            description = binding.description;
            image = binding.image;
        }
    }
}