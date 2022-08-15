package com.flatcode.simplemultiapps.NewsApp;

import com.flatcode.simplemultiapps.NewsApp.Model.NewsHeadlines;

import java.util.List;

public interface OnFetchDataListener<NewsApiResponse> {

    void onFetchData(List<NewsHeadlines> list, String message);

    void onError(String message);
}
