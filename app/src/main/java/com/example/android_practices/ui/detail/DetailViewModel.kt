package com.example.android_practices.ui.detail

import androidx.lifecycle.ViewModel
import com.example.android_practices.data.MockRepository
import com.example.android_practices.data.model.Comment
import com.example.android_practices.data.model.Post
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DetailViewModel : ViewModel() {

    private val _post = MutableStateFlow<Post?>(null)
    val post: StateFlow<Post?> = _post.asStateFlow()

    private val _comments = MutableStateFlow<List<Comment>>(emptyList())
    val comments: StateFlow<List<Comment>> = _comments.asStateFlow()

    fun load(postId: Int) {
        _post.value = MockRepository.getPost(postId)
        _comments.value = MockRepository.getComments(postId)
    }
}