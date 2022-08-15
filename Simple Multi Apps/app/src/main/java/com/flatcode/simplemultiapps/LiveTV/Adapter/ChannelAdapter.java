package com.flatcode.simplemultiapps.LiveTV.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.flatcode.simplemultiapps.LiveTV.Model.Channel;
import com.flatcode.simplemultiapps.R;
import com.flatcode.simplemultiapps.Unit.CLASS;
import com.flatcode.simplemultiapps.Unit.VOID;

import java.util.List;

public class ChannelAdapter extends RecyclerView.Adapter<ChannelAdapter.ViewHolder> {

    List<Channel> channels;
    String type;

    public ChannelAdapter(List<Channel> channels, String type) {
        this.channels = channels;
        this.type = type;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        if (type.equals("slider")) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_live_tv_slider, parent, false);
        } else if (type.equals("details")) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_live_tv, parent, false);
        } else {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_live_tv_home, parent, false);
        }
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(channels.get(position).getName());
        VOID.Glide(null, channels.get(position).getThumbnail(), holder.image);
        holder.itemView.setOnClickListener(v -> {
            Intent i = new Intent(v.getContext(), CLASS.LIVE_TV_DETAILS);
            i.putExtra("channel", channels.get(position));
            v.getContext().startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return channels.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
        }
    }
}