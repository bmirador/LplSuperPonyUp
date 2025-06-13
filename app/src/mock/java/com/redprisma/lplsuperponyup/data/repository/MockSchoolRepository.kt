package com.redprisma.lplsuperponyup.data.repository

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.redprisma.lplsuperponyup.data.local.models.Comment
import com.redprisma.lplsuperponyup.data.remote.models.CommentDto
import com.redprisma.lplsuperponyup.data.remote.models.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MockCommentsRepository(private val context: Context) : CommentsRepository {
    override fun fetchComments(): Flow<Result<List<Comment?>>> = flow {

        val json = context.assets.open("mock/comment-json.json")
            .bufferedReader().use { it.readText() }
        val type = object : TypeToken<List<CommentDto>>() {}.type
        val hold: List<CommentDto> = Gson().fromJson(json, type)

        emit(Result.success(hold.map { it.toDomain() }))
    }
}