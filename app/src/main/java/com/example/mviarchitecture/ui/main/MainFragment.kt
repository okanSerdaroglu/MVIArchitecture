package com.example.mviarchitecture.ui.main

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mviarchitecture.R
import com.example.mviarchitecture.ui.DataStateListener
import com.example.mviarchitecture.ui.main.state.MainStateEvent.*
import com.example.mviarchitecture.ui.main.viewmodel.MainViewModel
import java.lang.ClassCastException

class MainFragment : Fragment() {

    lateinit var dataStateHandler: DataStateListener

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        /** initialize viewModel the best way*/
        viewModel = activity?.run {
            ViewModelProvider(this).get(MainViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        subscribeObservers()
    }

    private fun subscribeObservers() {

        /** get data from repository layer and update your data */
        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->
            println("DEBUG: DataState: $dataState")
            // handle loading and message
            dataStateHandler.onDataStateChanged(dataState)

            // Handle Data<T>
            dataState.data?.let { mainViewState ->
                mainViewState.getContentIfNotHandled()?.let {
                    it.blogPost?.let { blogPosts ->
                        // set blogPost data
                        viewModel.setBlogListData(blogPosts)
                    }

                    it.user?.let { user ->
                        // set user data
                        viewModel.setUser(user)
                    }
                }
            }
        })

        /** update UI views */
        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->
            viewState.blogPost?.let {
                print("DEBUG: Setting blog posts to RecyclerView: $viewState")
            }
        })

        /** update UI views */
        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->
            viewState.user?.let {
                print("DEBUG: Setting user data: $viewState")
            }
        })

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_get_user -> triggerGetUserEvent()
            R.id.action_get_blogs -> triggerGetBlogsEvent()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun triggerGetBlogsEvent() {
        viewModel.setStateEvent(GetBlogPostsEvent())
    }

    private fun triggerGetUserEvent() {
        viewModel.setStateEvent(GetUserEvent("1"))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            dataStateHandler = context as DataStateListener
        } catch (e: ClassCastException) {
            println("DEBUG: $context must implement DataStateListener")
        }
    }

}