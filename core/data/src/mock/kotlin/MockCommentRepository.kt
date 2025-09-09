package com.redprisma.lplsuperponyup.data.repository

import android.content.Context
import com.example.data.AssetPathState
import com.example.data.CommentsRepository
import com.example.data.util.DataResult
import com.example.data.util.toDomain
import com.example.data.util.toEntity
import com.example.domain.Comment
import com.example.domain.DomainError
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.redprisma.network.remote.models.CommentDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import kotlin.getValue

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

                        val listType = TypeToken.getParameterized(
                            List::class.java, CommentDto::class.java
                        ).type

                        val comments: List<Comment> =
                            gson.fromJson<List<CommentDto>>(json, listType)
                                .map { it.toEntity().toDomain() }

                        emit(DataResult.Success(comments, fromCache = false))
                    } catch (e: Exception) {
                        emit(DataResult.Error(DomainError.Unknown()))
                    }
                }
            }
            .flowOn(Dispatchers.IO)
}
