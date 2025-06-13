package com.redprisma.lplsuperponyup.data.util

sealed class AppError {
    object Network : AppError()
    object Unauthorized : AppError()
    object NotFound : AppError()
    object Timeout : AppError()
    object Server : AppError()
    data class Unknown(val message: String? = null) :
        AppError()
}

fun Throwable.toAppError(): AppError {
    return when (this) {
        is java.net.UnknownHostException -> AppError.Network
        is java.net.SocketTimeoutException -> AppError.Timeout
        is retrofit2.HttpException -> when (code()) {
            401 -> AppError.Unauthorized
            404 -> AppError.NotFound
            in 500..599 -> AppError.Server
            else -> AppError.Unknown(message)
        }

        else -> AppError.Unknown(message)
    }
}
