package com.redprisma.lplsuperponyup.data.util

import com.example.domain.Comment
import com.redprisma.db.local.db.models.CommentEntity
import com.redprisma.network.remote.models.CommentDto

fun CommentDto.toEntity(): CommentEntity {
    return CommentEntity(
        id = this.id,
        name = this.name,
        body = this.body,
        postId = this.postId,
        email = this.email
    )
}

fun CommentEntity.toDomain(): Comment {
    return Comment(
        id = this.id,
        name = this.name,
        body = this.body,
        postId = this.postId,
        email = this.email
    )
}