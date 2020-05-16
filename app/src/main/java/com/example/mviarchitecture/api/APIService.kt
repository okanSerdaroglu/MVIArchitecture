package com.example.mviarchitecture.api

import com.example.mviarchitecture.models.BlogPost
import com.example.mviarchitecture.models.User
import retrofit2.http.GET
import retrofit2.http.Path

interface APIService {

    @GET("placeholder/blogs")
    fun getBlogPostList(): List<BlogPost>

    @GET("placeholder/user/{userId}")
    fun getUser(
        @Path("userId") userId: String
    ): User

}