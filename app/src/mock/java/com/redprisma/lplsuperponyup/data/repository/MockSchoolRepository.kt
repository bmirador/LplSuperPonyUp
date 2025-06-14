package com.redprisma.lplsuperponyup.data.repository

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.redprisma.lplsuperponyup.data.local.db.models.toDomain
import com.redprisma.lplsuperponyup.data.models.Comment
import com.redprisma.lplsuperponyup.data.remote.models.CommentDto
import com.redprisma.lplsuperponyup.data.remote.models.toEntity
import com.redprisma.lplsuperponyup.data.util.DataResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MockCommentsRepository(private val context: Context) : CommentsRepository {
    override fun fetchComments(): Flow<DataResult<List<Comment>>> = flow {

        val json = context.assets.open("mock/comment-json.json")
            .bufferedReader().use { it.readText() }
        val type = object : TypeToken<List<CommentDto>>() {}.type
        val hold: List<CommentDto> = Gson().fromJson(json, type)

        emit(DataResult.Success(hold.map { it.toEntity().toDomain() }, fromCache = false))
    }
}