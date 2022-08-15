package com.flatcode.simplemultiapps.VideoPlayer.Activity;

import static com.flatcode.simplemultiapps.VideoPlayer.Activity.VideoPlayerActivity.videoFiles;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.flatcode.simplemultiapps.R;
import com.flatcode.simplemultiapps.VideoPlayer.Adapter.VideoAdapter;

public class FilesFragment extends Fragment {

    RecyclerView recyclerView;
    View view;
    VideoAdapter adapter;

    public FilesFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_files, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        if (videoFiles != null && videoFiles.size() > 0) {
            adapter = new VideoAdapter(getContext(), videoFiles);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        }
        return view;
    }
}