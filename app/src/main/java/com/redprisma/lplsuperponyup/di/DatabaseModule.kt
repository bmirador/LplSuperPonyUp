package com.redprisma.lplsuperponyup.di

import android.content.Context
import androidx.room.Room
import com.redprisma.lplsuperponyup.data.local.db.AppDatabase
import com.redprisma.lplsuperponyup.data.local.db.CommentDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    fun provideCommentDao(db: AppDatabase): CommentDao = db.commentDao()
}
