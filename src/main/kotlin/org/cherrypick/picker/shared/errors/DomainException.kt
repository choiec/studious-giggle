package org.cherrypick.picker.shared.errors

open class DomainException(
    val errorCode: ErrorCode,
    message: String,
) : RuntimeException(message)
