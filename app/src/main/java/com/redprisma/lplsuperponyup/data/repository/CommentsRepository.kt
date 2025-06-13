package com.redprisma.lplsuperponyup.data.repository

import com.redprisma.lplsuperponyup.data.models.Comment
import com.redprisma.lplsuperponyup.data.util.DataResult
import kotlinx.coroutines.flow.Flow

interface CommentsRepository {
    fun fetchComments(): Flow<DataResult<List<Comment>>>
}