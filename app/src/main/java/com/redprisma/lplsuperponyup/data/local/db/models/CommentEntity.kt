package com.redprisma.lplsuperponyup.data.local.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.redprisma.lplsuperponyup.data.models.Comment

@Entity(tableName = "comments")
data class CommentEntity(
    val postId: Int,
    @PrimaryKey val id: Int,
    val name: String,
    val email: String,
    val body: String
)

fun CommentEntity.toDomain(): Comment {
    return Comment(
        postId = postId,
        id = id,
        name = name,
        email = email,
        body = body
    )
}

