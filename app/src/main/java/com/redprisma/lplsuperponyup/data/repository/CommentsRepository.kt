package com.redprisma.lplsuperponyup.data.repository

import com.redprisma.lplsuperponyup.data.local.models.Comment
import kotlinx.coroutines.flow.Flow

interface CommentsRepository {
    fun fetchComments(): Flow<Result<List<Comment?>>>
}