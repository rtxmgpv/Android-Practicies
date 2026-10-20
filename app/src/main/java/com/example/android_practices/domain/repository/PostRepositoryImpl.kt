package com.example.android_practices.data.repository

import com.example.android_practices.data.remote.JsonPlaceholderApi
import com.example.android_practices.data.remote.dto.toDomain
import com.example.android_practices.domain.model.Comment
import com.example.android_practices.domain.model.Post
import com.example.android_practices.domain.repository.PostRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PostRepositoryImpl(
    private val api: JsonPlaceholderApi
) : PostRepository {

    override suspend fun getPosts(limit: Int): List<Post> = withContext(Dispatchers.IO) {
        api.getPosts(limit).map { it.toDomain() }
    }

    override suspend fun getPostById(id: Int): Post = withContext(Dispatchers.IO) {
        api.getPostById(id).toDomain()
    }

    override suspend fun getCommentsForPost(postId: Int): List<Comment> = withContext(Dispatchers.IO) {
        api.getComments(postId).map { it.toDomain() }
    }
}