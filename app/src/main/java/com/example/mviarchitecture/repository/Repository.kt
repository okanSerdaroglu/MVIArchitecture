package com.example.mviarchitecture.repository

import androidx.lifecycle.LiveData
import com.example.mviarchitecture.api.RetrofitSingleton
import com.example.mviarchitecture.models.BlogPost
import com.example.mviarchitecture.models.User

import com.example.mviarchitecture.ui.main.state.MainViewState
import com.example.mviarchitecture.util.ApiSuccessResponse

import com.example.mviarchitecture.util.DataState
import com.example.mviarchitecture.util.GenericApiResponse

object Repository {

    fun getBlogPosts(): LiveData<DataState<MainViewState>> {
        return object : NetworkBoundResource<List<BlogPost>, MainViewState>() {
            override fun handleApiSuccessResponse(response: ApiSuccessResponse<List<BlogPost>>) {
                result.value = DataState.data(
                    null
                    , MainViewState(blogPost = response.body)
                )
            }

            override fun createCall(): LiveData<GenericApiResponse<List<BlogPost>>> {
                return RetrofitSingleton.apiService.getBlogPostList()
            }

        }.asLiveData()

    }

    fun getUser(userID: String): LiveData<DataState<MainViewState>> {
        return object : NetworkBoundResource<User, MainViewState>() {
            override fun handleApiSuccessResponse(response: ApiSuccessResponse<User>) {
                result.value = DataState.data(data = MainViewState(user = response.body))
            }

            override fun createCall(): LiveData<GenericApiResponse<User>> {
                return RetrofitSingleton.apiService.getUser(userID)
            }

        }.asLiveData()
    }

}