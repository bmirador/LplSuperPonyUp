package com.redprisma.lplsuperponyup.di

import com.redprisma.common.Logger
import com.redprisma.db.local.db.CommentDao
import com.redprisma.lplsuperponyup.data.repository.CommentsRepository
import com.redprisma.lplsuperponyup.data.repository.CommentsRepositoryImpl
import com.redprisma.network.remote.CommentsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryProvider {

    @Provides
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
    @Provides
    @Singleton
    fun provideCommentService(
        service: CommentsService,
        dao: CommentDao,
        logger: Logger
    ): CommentsRepository {
        return CommentsRepositoryImpl(service, dao, logger)
    }
}