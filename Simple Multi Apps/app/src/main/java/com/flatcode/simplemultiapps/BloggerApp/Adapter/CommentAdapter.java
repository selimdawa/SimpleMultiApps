package com.flatcode.simplemultiapps.BloggerApp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.flatcode.simplemultiapps.BloggerApp.Model.Comment;
import com.flatcode.simplemultiapps.R;
import com.flatcode.simplemultiapps.Unit.DATA;
import com.flatcode.simplemultiapps.Unit.VOID;
import com.flatcode.simplemultiapps.databinding.ItemBloggerCommentBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private ItemBloggerCommentBinding binding;
    private Context context;
    public ArrayList<Comment> comments;

    public CommentAdapter(Context context, ArrayList<Comment> comments) {
        this.context = context;
        this.comments = comments;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemBloggerCommentBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Comment list = comments.get(position);
        String id = list.getId();
        String name = list.getName();
        String published = list.getPublished();
        String comment = list.getComment();
        String image = list.getProfileImage();

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

        holder.name.setText(name);
        holder.date.setText(formattedDate);
        holder.comment.setText(comment);

        try {
            VOID.Glide(context, image, holder.image);
        } catch (Exception e) {
            holder.image.setImageResource(R.drawable.ic_person);
        }
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name, date, comment;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = binding.name;
            date = binding.date;
            comment = binding.comment;
            image = binding.image;
        }
    }
}