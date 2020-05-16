package com.example.mviarchitecture.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.mviarchitecture.api.RetrofitSingleton
import com.example.mviarchitecture.ui.main.state.MainViewState
import com.example.mviarchitecture.util.ApiEmptyResponse
import com.example.mviarchitecture.util.ApiErrorResponse
import com.example.mviarchitecture.util.ApiSuccessResponse

object Repository {

    fun getBlogPosts(): LiveData<MainViewState> {
        return Transformations
            .switchMap(RetrofitSingleton.apiService.getBlogPostList()) { apiResponse ->
                object : LiveData<MainViewState>() {
                    override fun onActive() {
                        super.onActive()
                        when (apiResponse) {
                            is ApiSuccessResponse -> {
                                value = MainViewState(
                                    blogPost = apiResponse.body
                                )
                            }

                            is ApiErrorResponse -> {
                                value = MainViewState() // Handle error ?
                            }

                            is ApiEmptyResponse -> {
                                value = MainViewState() // handle error
                            }

                        }
                    }
                }
            }
    }

    fun getUser(userID: String): LiveData<MainViewState> {
        return Transformations
            .switchMap(RetrofitSingleton.apiService.getUser(userID)) { apiResponse ->
                object : LiveData<MainViewState>() {
                    override fun onActive() {
                        super.onActive()
                        when (apiResponse) {
                            is ApiSuccessResponse -> {
                                value = MainViewState(
                                    user = apiResponse.body
                                )

                            }

                            is ApiErrorResponse -> {
                                value = MainViewState() // Handle error ?
                            }

                            is ApiEmptyResponse -> {
                                value = MainViewState() // handle error
                            }

                        }
                    }
                }
            }
    }

}