package com.redprisma.network.remote

import java.net.SocketTimeoutException
import java.net.UnknownHostException

sealed class    NetworkError {
    object Connection : NetworkError()
    object Timeout : NetworkError()
    object Unauthorized : NetworkError()
    object NotFound : NetworkError()
    object Server : NetworkError()
    data class Unknown(val message: String? = null) : NetworkError()
}

fun Throwable.toNetworkError(): NetworkError = when (this) {
    is UnknownHostException -> NetworkError.Connection
    is SocketTimeoutException -> NetworkError.Timeout
    is retrofit2.HttpException -> when (code()) {
        401 -> NetworkError.Unauthorized
        404 -> NetworkError.NotFound
        in 500..599 -> NetworkError.Server
        else -> NetworkError.Unknown(message)
    }
    else -> NetworkError.Unknown(message)
}