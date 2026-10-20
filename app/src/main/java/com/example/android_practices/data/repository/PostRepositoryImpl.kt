package com.example.android_practices.data.repository

import com.example.android_practices.data.local.FavoriteDao
import com.example.android_practices.data.local.FavoriteEntity
import com.example.android_practices.data.local.FilterPreferences
import com.example.android_practices.data.remote.JsonPlaceholderApi
import com.example.android_practices.data.remote.dto.toDomain
import com.example.android_practices.di.FilterCache
import com.example.android_practices.domain.model.Comment
import com.example.android_practices.domain.model.Post
import com.example.android_practices.domain.repository.PostRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class PostRepositoryImpl(
    private val api: JsonPlaceholderApi,
    private val favoriteDao: FavoriteDao,
    private val filterPreferences: FilterPreferences,
    private val filterCache: FilterCache
) : PostRepository {

    override suspend fun getPosts(limit: Int, userId: Int?): List<Post> =
        withContext(Dispatchers.IO) {
            api.getPosts(limit = limit, userId = userId).map { it.toDomain() }
        }

    override suspend fun getPostById(id: Int): Post = withContext(Dispatchers.IO) {
        api.getPostById(id).toDomain()
    }

    override suspend fun getCommentsForPost(postId: Int): List<Comment> =
        withContext(Dispatchers.IO) {
            api.getComments(postId).map { it.toDomain() }
        }

    override fun getFilterUserId(): Flow<Int> = filterPreferences.userIdFlow
    override fun getFilterLimit(): Flow<Int> = filterPreferences.limitFlow

    override suspend fun saveFilters(userId: Int, limit: Int) {
        filterPreferences.saveFilters(userId, limit)
        val isDefault = userId == FilterPreferences.DEFAULT_USER_ID && limit == FilterPreferences.DEFAULT_LIMIT
        filterCache.hasActiveFilters.value = !isDefault
    }

    override fun getFavorites(): Flow<List<Post>> {
        return favoriteDao.getFavorites().map { entities ->
            entities.map { Post(it.id, it.title, it.body) }
        }
    }

    override suspend fun addFavorite(post: Post) = withContext(Dispatchers.IO) {
        favoriteDao.addFavorite(FavoriteEntity(post.id, post.title, post.body))
    }

    override suspend fun removeFavorite(postId: Int) = withContext(Dispatchers.IO) {
        favoriteDao.removeFavorite(postId)
    }

    override fun isFavorite(postId: Int): Flow<Boolean> = favoriteDao.isFavorite(postId)
}