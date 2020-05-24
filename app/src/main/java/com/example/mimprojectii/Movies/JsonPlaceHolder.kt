package com.example.mimprojectii.Movies

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface JsonPlaceHolder {
    @GET(".")
    fun getPosts(
        @Query("t") t: String,
        @Query("apikey") apikey: String
    ): Call<Post>
}
