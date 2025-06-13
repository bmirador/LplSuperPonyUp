package com.redprisma.lplsuperponyup.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.redprisma.lplsuperponyup.data.local.db.models.CommentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CommentDao {

    @Query("SELECT * FROM comments")
    fun getAllComments(): Flow<List<CommentEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComments(comments: List<CommentEntity>)

    @Query("DELETE FROM comments")
    suspend fun clearComments()

    @Query("SELECT * FROM comments WHERE postId == :postId")
    fun getAllCommentsByPostId(postId: Int): Flow<List<CommentEntity>>
}
