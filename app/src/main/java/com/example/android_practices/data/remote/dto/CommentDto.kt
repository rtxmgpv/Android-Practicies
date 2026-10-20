package com.example.android_practices.data.remote.dto

import com.example.android_practices.domain.model.Comment
import com.google.gson.annotations.SerializedName

data class CommentDto(
    @SerializedName("id") val id: Int,
    @SerializedName("postId") val postId: Int,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("body") val body: String
)

fun CommentDto.toDomain(): Comment {
    return Comment(
        id = id,
        name = name,
        email = email,
        body = body
    )
}