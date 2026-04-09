package org.cherrypick.picker.shared.util

inline fun <T, R> Result<T>.map(transform: (T) -> R): Result<R> =
    fold(
        onSuccess = { Result.success(transform(it)) },
        onFailure = { Result.failure(it) },
    )
