package com.flatcode.simplemultiapps.Wordpress.Util;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WordPressClient {

    private static final String BASE_URL = "https://www.wpexplorer.com/wp-json/wp/v2/";

    public static Retrofit getRetroInstance() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    public static WPApiService getApiService() {
        return getRetroInstance().create(WPApiService.class);
    }
}
