package com.redprisma.network.remote

import com.redprisma.network.remote.models.CommentDto
import retrofit2.http.GET

interface CommentsService {
    @GET("posts/1/comments")
    suspend fun getComments(): List<CommentDto?> = listOf()
}
