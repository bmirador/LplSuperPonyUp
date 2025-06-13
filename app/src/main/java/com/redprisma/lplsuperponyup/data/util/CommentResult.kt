package com.redprisma.lplsuperponyup.data.util

sealed class DataResult<T> {
    data class Success<T>(val data: T, val error: AppError? = null, val fromCache: Boolean) :
        DataResult<T>()

    data class Error<T>(val appError: AppError) : DataResult<T>()
}
