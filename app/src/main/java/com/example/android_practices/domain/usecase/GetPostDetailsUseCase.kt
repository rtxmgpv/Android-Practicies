package com.example.android_practices.domain.usecase

import com.example.android_practices.domain.model.Comment
import com.example.android_practices.domain.model.Post
import com.example.android_practices.domain.repository.PostRepository

class GetPostDetailsUseCase(private val repository: PostRepository) {
    suspend operator fun invoke(postId: Int): Pair<Post, List<Comment>> {
        val post = repository.getPostById(postId)
        val comments = repository.getCommentsForPost(postId)
        return Pair(post, comments)
    }
}