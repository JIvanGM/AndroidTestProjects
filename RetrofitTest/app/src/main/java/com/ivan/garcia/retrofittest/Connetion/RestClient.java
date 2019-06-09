package com.ivan.garcia.retrofittest.Connetion;

import com.ivan.garcia.retrofittest.Objects.Post;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RestClient {

    @GET("posts")
    Call<List<Post>> getPosts();

    @GET("posts/{id}")
    Call<Post> getPostById(@Path("id") int id);

    @POST("posts")
    Call<Post> createPost(@Body Post post);

    @PUT("posts/{id}")
    Call<Post> updatePost(@Path("id") int id, @Body Post post);

    @DELETE("posts/{id}")
    Call<Post> deletePost(@Path("id") int id);

}

