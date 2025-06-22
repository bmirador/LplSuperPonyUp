package com.redprisma.lplsuperponyup.data.repository

import com.redprisma.lplsuperponyup.data.local.db.CommentDao
import com.redprisma.lplsuperponyup.data.local.db.models.toDomain
import com.redprisma.lplsuperponyup.data.models.Comment
import com.redprisma.lplsuperponyup.data.remote.CommentsService
import com.redprisma.lplsuperponyup.data.remote.models.toEntity
import com.redprisma.lplsuperponyup.data.util.AppError
import com.redprisma.lplsuperponyup.data.util.DataResult
import com.redprisma.lplsuperponyup.data.util.ERROR_TAG
import com.redprisma.lplsuperponyup.data.util.toAppError
import com.redprisma.lplsuperponyup.logger.Logger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CommentsRepositoryImpl @Inject constructor(
    private val api: CommentsService,
    private val commentDao: CommentDao,
    private val logger: Logger
) : CommentsRepository {

    override fun fetchComments(): Flow<DataResult<List<Comment>>> = flow {
        var loadedFromCache = false
        var networkException: Exception? = null

        try {
            val remoteData = api.getComments().mapNotNull { it?.toEntity() }
            commentDao.insertComments(remoteData)
        } catch (e: Exception) {
            loadedFromCache = true
            networkException = e
            logger.e(ERROR_TAG, e.message.toString())
        }

        val data = commentDao.getAllCommentsByPostId(1)
            .map { entities -> entities.map { it.toDomain() } }

        emitAll(
            data.map { comments ->
                if (comments.isEmpty() && loadedFromCache) {
                    DataResult.Error(
                        networkException?.toAppError() ?: AppError.Unknown(null)
                    )
                } else {
                    DataResult.Success(
                        data = comments,
                        error = networkException?.toAppError(),
                        fromCache = loadedFromCache
                    )
                }
            }
        )
    }.catch { e ->
        emit(DataResult.Error(e.toAppError()))
    }
}
