package com.redprisma.db.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.redprisma.db.local.db.models.CommentEntity

@Database(entities = [CommentEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun commentDao(): CommentDao
}
