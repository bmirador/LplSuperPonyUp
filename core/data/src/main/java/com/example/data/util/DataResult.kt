package com.example.data.util

import com.example.domain.DomainError

sealed class DataResult<T> {
    data class Success<T>(val data: T, val error: DomainError? = null, val fromCache: Boolean) :
        DataResult<T>()

    data class Error<T>(val appError: DomainError?) : DataResult<T>()
}