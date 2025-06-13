package com.redprisma.lplsuperponyup.data.remote

import com.redprisma.lplsuperponyup.data.remote.models.CommentDto
import retrofit2.http.GET

interface CommentsService {
    @GET("posts/1/comments")
    suspend fun getComments(): List<CommentDto?> = listOf()
}
