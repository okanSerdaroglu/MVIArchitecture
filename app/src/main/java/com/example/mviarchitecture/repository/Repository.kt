package com.example.mviarchitecture.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.mviarchitecture.api.RetrofitSingleton
import com.example.mviarchitecture.ui.main.state.MainViewState
import com.example.mviarchitecture.util.ApiEmptyResponse
import com.example.mviarchitecture.util.ApiErrorResponse
import com.example.mviarchitecture.util.ApiSuccessResponse
import com.example.mviarchitecture.util.DataState

object Repository {

    fun getBlogPosts(): LiveData<DataState<MainViewState>> {
        return Transformations
            .switchMap(RetrofitSingleton.apiService.getBlogPostList()) { apiResponse ->
                object : LiveData<DataState<MainViewState>>() {
                    override fun onActive() {
                        super.onActive()
                        when (apiResponse) {
                            is ApiSuccessResponse -> {
                                value = DataState.data(
                                    data = MainViewState(blogPost = apiResponse.body)
                                )
                            }

                            is ApiErrorResponse -> {
                                value = DataState.error(message = apiResponse.errorMessage)
                            }

                            is ApiEmptyResponse -> {
                                value = DataState.error(message = "HTTP 204. Returned NOTHING!")
                            }

                        }
                    }
                }
            }
    }

    fun getUser(userID: String): LiveData<DataState<MainViewState>> {
        return Transformations
            .switchMap(RetrofitSingleton.apiService.getUser(userID)) { apiResponse ->
                object : LiveData<DataState<MainViewState>>() {
                    override fun onActive() {
                        super.onActive()
                        value = when (apiResponse) {
                            is ApiSuccessResponse -> {
                                DataState.data(data = MainViewState(user = apiResponse.body))
                            }

                            is ApiErrorResponse -> {
                                DataState.error(apiResponse.errorMessage) // Handle error ?
                            }

                            is ApiEmptyResponse -> {
                                DataState.error(message = "HTTP 204. Returned NOTHING!")
                            }

                        }
                    }
                }
            }
    }

}