package com.example.mviarchitecture.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.mviarchitecture.models.BlogPost
import com.example.mviarchitecture.models.User
import com.example.mviarchitecture.repository.Repository
import com.example.mviarchitecture.ui.main.state.MainStateEvent
import com.example.mviarchitecture.ui.main.state.MainViewState
import com.example.mviarchitecture.util.AbsentLiveData
import com.example.mviarchitecture.util.DataState

class MainViewModel : ViewModel() {

    private val _stateEvent: MutableLiveData<MainStateEvent> = MutableLiveData()
    private val _viewState: MutableLiveData<MainViewState> = MutableLiveData()

    val viewState: LiveData<MainViewState>
        get() = _viewState

    /**
     * it is the same with above method
     * fun observeViewState():LiveData<MainViewState>{
    return _viewState
    }*/


    val dataState: LiveData<DataState<MainViewState>> = Transformations
        .switchMap(_stateEvent) { stateEvent ->
            stateEvent?.let {
                handleStateEvent(it)
            }
        }

    private fun handleStateEvent(stateEvent: MainStateEvent): LiveData<DataState<MainViewState>> {
        return when (stateEvent) {
            is MainStateEvent.GetBlogPostsEvent -> {
                return Repository.getBlogPosts()
            }

            is MainStateEvent.GetUserEvent -> {
                return Repository.getUser(stateEvent.userID)
            }

            is MainStateEvent.None -> {
                AbsentLiveData.create()
            }
        }
    }

    fun setBlogListData(blogPostList: List<BlogPost>) {
        val update = getCurrentViewStateOrNew()
        update.blogPost = blogPostList
        _viewState.value = update
    }

    fun setUser(user: User) {
        val update = getCurrentViewStateOrNew()
        update.user = user
        _viewState.value = update
    }

    private fun getCurrentViewStateOrNew(): MainViewState {
        return viewState.value?.let {
            it
        } ?: MainViewState()
    }

    fun setStateEvent(event: MainStateEvent) {
        _stateEvent.value = event
    }


}
