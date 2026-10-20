package com.example.android_practices.domain.repository

import com.example.android_practices.domain.model.Comment
import com.example.android_practices.domain.model.Post

interface PostRepository {
    suspend fun getPosts(limit: Int): List<Post>
    suspend fun getPostById(id: Int): Post
    suspend fun getCommentsForPost(postId: Int): List<Comment>
}