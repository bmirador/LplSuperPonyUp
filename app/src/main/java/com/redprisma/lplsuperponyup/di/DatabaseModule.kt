package com.redprisma.lplsuperponyup.di

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.redprisma.lplsuperponyup.data.local.db.AppDatabase
import com.redprisma.lplsuperponyup.data.local.db.CommentDao
import com.redprisma.lplsuperponyup.logger.Logger
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
    fun provideLogger(): Logger {
        return object : Logger {
            override fun e(tag: String, message: String) {
                Log.e(tag, message)
            }
            override fun d(tag: String, message: String) {
                Log.d(tag, message)
            }
            override fun i(tag: String, message: String) {
                Log.i(tag, message)
            }
        }
    }

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
