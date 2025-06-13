package com.redprisma.lplsuperponyup.data.repository

import com.redprisma.lplsuperponyup.data.local.models.Comment
import com.redprisma.lplsuperponyup.data.remote.CommentsService
import com.redprisma.lplsuperponyup.data.remote.models.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

// Implementation of the CommentsRepository interface
class CommentsRepositoryImpl @Inject constructor(private val api: CommentsService) :
    CommentsRepository {

    // Fetches comments from the remote API and emits them as a Flow<Result>
    override fun fetchComments(): Flow<Result<List<Comment?>>> =
        flow {
            // Fetch and convert remote data to domain model
            val data = api.getComments()?.map { it?.toDomain() }

            // Emit failure if data is empty or null
            if (data.isNullOrEmpty()) {
                emit(Result.failure(Exception("The list is empty!")))
            } else {
                // Emit success with data
                emit(Result.success(data))
            }
        }.catch { e ->
            // Catch any exceptions and emit as failure
            emit(Result.failure(e))
        }
}
