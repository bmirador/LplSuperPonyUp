package com.redprisma.lplsuperponyup.data.remote.models


import com.google.gson.annotations.SerializedName
import com.redprisma.lplsuperponyup.data.local.db.models.CommentEntity

data class CommentDto(
    @SerializedName("postId") val postId: Int,
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("body") val body: String
)

fun CommentDto.toEntity(): CommentEntity {
    return CommentEntity(
        id = this.id,
        name = this.name,
        body = this.body,
        postId = this.postId,
        email = this.email
    )
}