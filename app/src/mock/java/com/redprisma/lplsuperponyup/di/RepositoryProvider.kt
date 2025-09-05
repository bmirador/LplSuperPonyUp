package com.redprisma.lplsuperponyup.di

import android.content.Context
import com.redprisma.lplsuperponyup.data.repository.CommentsRepository
import com.redprisma.lplsuperponyup.data.repository.MockCommentsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun provideCommentsRepository(
        @ApplicationContext appContext: Context,
        assetPathProvider: AssetPathProviderImpl
    ): CommentsRepository {
        return MockCommentsRepository(appContext, assetPathProvider)
    }
}