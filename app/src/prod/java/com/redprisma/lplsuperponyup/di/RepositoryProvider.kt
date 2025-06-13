package com.redprisma.lplsuperponyup.di

import com.redprisma.lplsuperponyup.data.local.db.CommentDao
import com.redprisma.lplsuperponyup.data.remote.CommentsService
import com.redprisma.lplsuperponyup.data.repository.CommentsRepositoryImpl
import com.redprisma.lplsuperponyup.data.repository.CommentsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryProvider {

    @Provides
    @Singleton
    fun provideCommentService(
        service: CommentsService,
        dao: CommentDao
    ): CommentsRepository {
       return CommentsRepositoryImpl(service, dao)
    }
}