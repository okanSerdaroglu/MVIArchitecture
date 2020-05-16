package com.example.mviarchitecture.api

import androidx.lifecycle.LiveData
import com.example.mviarchitecture.models.BlogPost
import com.example.mviarchitecture.models.User
import com.example.mviarchitecture.util.GenericApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface APIService {

    @GET("placeholder/blogs")
    fun getBlogPostList(): LiveData<GenericApiResponse<List<BlogPost>>>

    @GET("placeholder/user/{userId}")
    fun getUser(
        @Path("userId") userId: String
    ): LiveData<GenericApiResponse<User>>

}