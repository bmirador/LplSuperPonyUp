package com.redprisma.db

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.redprisma.db.local.db.AppDatabase
import com.redprisma.db.local.db.CommentDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.logging.Logger
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
