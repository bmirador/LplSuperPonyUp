package com.redprisma.lplsuperponyup.di

import com.redprisma.lplsuperponyup.data.datastore.AssetPathState
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AssetProviderModule {

    @Binds
    @Singleton
    abstract fun bindAssetPathState(impl: AssetPathProviderImpl): AssetPathState
}