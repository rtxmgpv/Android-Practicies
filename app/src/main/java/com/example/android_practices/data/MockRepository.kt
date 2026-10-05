package com.example.android_practices.data

import com.example.android_practices.data.model.Comment
import com.example.android_practices.data.model.Post

object MockRepository {

    private val posts: List<Post> = List(20) { i ->
        Post(
            id = i + 1,
            userId = (i % 5) + 1,
            title = "Пост #${i + 1}: ${listOf("Kotlin", "Compose", "Android", "API", "MVVM").random()}",
            body = "Это текст поста номер ${i + 1}. Здесь может быть длинное описание, " +
                    "которое мы покажем на экране деталей."
        )
    }

    private val comments: List<Comment> = List(50) { i ->
        Comment(
            id = i + 1,
            postId = (i % 20) + 1,
            name = "Пользователь ${i + 1}",
            email = "user${i + 1}@example.com",
            body = "Комментарий №${i + 1} к посту. Короткий текст для демонстрации."
        )
    }

    fun getPosts(): List<Post> = posts

    fun getPost(id: Int): Post? = posts.find { it.id == id }

    fun getComments(postId: Int): List<Comment> = comments.filter { it.postId == postId }
}