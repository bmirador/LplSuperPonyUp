package com.example.data

import com.example.data.util.DataResult
import com.example.domain.Comment
import kotlinx.coroutines.flow.Flow

interface CommentsRepository {
    fun fetchComments(): Flow<DataResult<List<Comment>>>
}