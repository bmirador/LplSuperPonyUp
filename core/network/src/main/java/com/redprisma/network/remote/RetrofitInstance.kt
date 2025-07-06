package com.redprisma.network.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor


val logging = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}

val clientBuilder = OkHttpClient.Builder()
    .addInterceptor(logging)
