package com.example.android_practices.ui.list

import androidx.lifecycle.ViewModel
import com.example.android_practices.data.MockRepository
import com.example.android_practices.data.model.Post
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ListViewModel : ViewModel() {

    private val _posts = MutableStateFlow(MockRepository.getPosts())
    val posts: StateFlow<List<Post>> = _posts.asStateFlow()
}