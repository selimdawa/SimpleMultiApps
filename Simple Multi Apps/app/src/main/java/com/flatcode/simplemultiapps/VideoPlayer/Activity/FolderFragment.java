package com.flatcode.simplemultiapps.VideoPlayer.Activity;

import static com.flatcode.simplemultiapps.VideoPlayer.Activity.VideoPlayerActivity.folderList;
import static com.flatcode.simplemultiapps.VideoPlayer.Activity.VideoPlayerActivity.videoFiles;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.flatcode.simplemultiapps.R;
import com.flatcode.simplemultiapps.VideoPlayer.Adapter.FolderAdapter;

public class FolderFragment extends Fragment {

    FolderAdapter adapter;
    RecyclerView recyclerView;

    public FolderFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_folder, container, false);
             recyclerView = view.findViewById(R.id.recyclerView);
             if (folderList != null && folderList.size() > 0 && videoFiles != null) {
                 adapter = new FolderAdapter(getContext(), videoFiles, folderList);
                 recyclerView.setAdapter(adapter);
                 recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
             }
        return view;
    }
}