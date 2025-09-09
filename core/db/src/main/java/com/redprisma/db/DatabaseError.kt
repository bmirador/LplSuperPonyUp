package com.redprisma.db

sealed class DatabaseError {
    object Constraint : DatabaseError()
    object NotFound : DatabaseError()
    data class Unknown(val message: String? = null) : DatabaseError()
}

fun Throwable.toDatabaseError(): DatabaseError = when (this) {
    is android.database.sqlite.SQLiteConstraintException -> DatabaseError.Constraint
    is android.database.sqlite.SQLiteException -> DatabaseError.Unknown(message)
    else -> DatabaseError.Unknown(message)
}
