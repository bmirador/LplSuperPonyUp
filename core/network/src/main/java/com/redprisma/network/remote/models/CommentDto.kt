package com.redprisma.network.remote.models


import com.google.gson.annotations.SerializedName

data class CommentDto(
    @SerializedName("postId") val postId: Int,
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("body") val body: String
)

//fun CommentDto.toEntity(): CommentEntity {
//    return CommentEntity(
//        id = this.id,
//        name = this.name,
//        body = this.body,
//        postId = this.postId,
//        email = this.email
//    )
//}