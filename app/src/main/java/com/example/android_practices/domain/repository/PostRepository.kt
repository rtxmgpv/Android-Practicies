package com.example.android_practices.domain.repository

import com.example.android_practices.domain.model.Comment
import com.example.android_practices.domain.model.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    suspend fun getPosts(limit: Int, userId: Int?): List<Post>
    suspend fun getPostById(id: Int): Post
    suspend fun getCommentsForPost(postId: Int): List<Comment>

    fun getFilterUserId(): Flow<Int>
    fun getFilterLimit(): Flow<Int>
    suspend fun saveFilters(userId: Int, limit: Int)

    fun getFavorites(): Flow<List<Post>>
    suspend fun addFavorite(post: Post)
    suspend fun removeFavorite(postId: Int)
    fun isFavorite(postId: Int): Flow<Boolean>
}