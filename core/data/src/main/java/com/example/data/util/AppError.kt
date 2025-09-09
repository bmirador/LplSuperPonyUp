package com.example.data.util

import com.example.domain.DomainError
import com.redprisma.db.DatabaseError
import com.redprisma.network.remote.NetworkError

fun NetworkError.toDomainError(): DomainError = when (this) {
    NetworkError.Connection -> DomainError.NetworkUnavailable
    NetworkError.Timeout -> DomainError.NetworkUnavailable
    NetworkError.Unauthorized -> DomainError.Unauthorized
    NetworkError.NotFound -> DomainError.NotFound
    NetworkError.Server -> DomainError.Server
    is NetworkError.Unknown -> DomainError.Unknown(message)
}

fun DatabaseError.toDomainError(): DomainError = when (this) {
    DatabaseError.Constraint -> DomainError.ValidationFailed
    DatabaseError.NotFound -> DomainError.NotFound
    is DatabaseError.Unknown -> DomainError.Unknown(message)
}
