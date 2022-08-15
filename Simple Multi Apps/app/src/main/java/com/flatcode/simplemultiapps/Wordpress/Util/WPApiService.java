package com.flatcode.simplemultiapps.Wordpress.Util;

import com.flatcode.simplemultiapps.Wordpress.Model.Media;
import com.flatcode.simplemultiapps.Wordpress.Model.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface WPApiService {

    @GET("posts")
    Call<List<Post>> getPosts();

    @GET("posts/{id}")
    Call<Post> getPostById(@Path("id") int postId);

    @GET("media/{featured_media}")
    Call<Media> getPostThumbnail(@Path("featured_media") int media);


}
