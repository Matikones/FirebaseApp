package com.example.mimprojectii.Movies

import com.google.gson.annotations.SerializedName

class Post {
    private var Title = ""

    @SerializedName("body")

    fun getTitle(): String{
        return Title
    }
}