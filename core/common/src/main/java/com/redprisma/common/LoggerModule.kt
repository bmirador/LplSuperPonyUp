package com.redprisma.common

import android.util.Log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LoggerModule {

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
}
