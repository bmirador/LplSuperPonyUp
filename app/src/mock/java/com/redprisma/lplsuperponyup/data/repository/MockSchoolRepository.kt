package com.redprisma.lplsuperponyup.data.repository

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.redprisma.lplsuperponyup.data.local.db.models.toDomain
import com.redprisma.lplsuperponyup.data.models.Comment
import com.redprisma.lplsuperponyup.data.remote.models.CommentDto
import com.redprisma.lplsuperponyup.data.remote.models.toEntity
import com.redprisma.lplsuperponyup.data.util.DataResult
import com.redprisma.lplsuperponyup.data.util.toAppError
import com.redprisma.lplsuperponyup.di.AssetPathState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MockCommentsRepository @Inject constructor(
    private val context: Context,
    private val assetPathProvider: AssetPathState
) : CommentsRepository {

    private val gson by lazy { Gson() }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun fetchComments(): Flow<DataResult<List<Comment>>> =
        assetPathProvider.pathFlow
            .flatMapLatest { path ->
                flow {
                    try {
                        val json = context.assets.open(path)
                            .bufferedReader()
                            .use { it.readText() }

                        val listType = object : TypeToken<List<CommentDto>>() {}.type
                        val comments = gson.fromJson<List<CommentDto>>(json, listType)
                            .map { it.toEntity().toDomain() }

                        emit(DataResult.Success(comments, fromCache = false))
                    } catch (e: Exception) {
                        emit(DataResult.Error(e.toAppError()))
                    }
                }
            }
            .flowOn(Dispatchers.IO)
}
