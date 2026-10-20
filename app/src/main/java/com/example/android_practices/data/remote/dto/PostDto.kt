package com.example.android_practices.data.remote.dto

import com.example.android_practices.domain.model.Post
import com.google.gson.annotations.SerializedName

data class PostDto(
    @SerializedName("id") val id: Int,
    @SerializedName("userId") val userId: Int,
    @SerializedName("title") val title: String,
    @SerializedName("body") val body: String
)

fun PostDto.toDomain(): Post {
    return Post(
        id = id,
        title = title,
        body = body
    )
}