package com.example.android_practices.domain.usecase

import com.example.android_practices.domain.model.Post
import com.example.android_practices.domain.repository.PostRepository

class GetPostsUseCase(private val repository: PostRepository) {
    suspend operator fun invoke(limit: Int = 20): List<Post> {
        return repository.getPosts(limit)
    }
}