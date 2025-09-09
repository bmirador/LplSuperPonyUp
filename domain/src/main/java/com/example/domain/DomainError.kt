package com.example.domain

sealed class DomainError {
    object Unauthorized : DomainError()
    object NotFound : DomainError()
    object Server : DomainError()
    object ValidationFailed : DomainError()
    object NetworkUnavailable : DomainError()
    data class Unknown(val message: String? = null) : DomainError()
}
