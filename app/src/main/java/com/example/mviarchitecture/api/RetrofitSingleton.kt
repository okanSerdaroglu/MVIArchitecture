package com.example.mviarchitecture.api

import com.example.mviarchitecture.util.LiveDataCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitSingleton {

    private const val BASE_URL = "https://open-api.xyz/"
    private val retrofitBuilder: Retrofit.Builder by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
    }

    val apiService: APIService by lazy {
        retrofitBuilder.build().create(APIService::class.java)
    }

}