package com.redprisma.lplsuperponyup.di

import android.content.Context
import com.redprisma.lplsuperponyup.data.repository.CommentsRepository
import com.redprisma.lplsuperponyup.data.repository.MockCommentsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryProvider {

    @Provides
    @Singleton
    fun provideCommentsRepository(
        @ApplicationContext appContext: Context
    ): CommentsRepository {
        return MockCommentsRepository(appContext)
    }
}