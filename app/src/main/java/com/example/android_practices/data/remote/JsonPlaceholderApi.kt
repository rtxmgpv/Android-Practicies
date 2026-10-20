package com.example.android_practices.data.remote

import com.example.android_practices.data.remote.dto.CommentDto
import com.example.android_practices.data.remote.dto.PostDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface JsonPlaceholderApi {
    @GET("posts")
    suspend fun getPosts(
        @Query("_limit") limit: Int,
        @Query("userId") userId: Int? = null
    ): List<PostDto>

    @GET("posts/{id}")
    suspend fun getPostById(@Path("id") postId: Int): PostDto

    @GET("comments")
    suspend fun getComments(@Query("postId") postId: Int): List<CommentDto>
}