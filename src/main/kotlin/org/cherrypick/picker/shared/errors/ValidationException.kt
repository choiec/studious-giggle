package org.cherrypick.picker.shared.errors

class ValidationException(
    errorCode: ErrorCode,
    message: String,
) : DomainException(errorCode, message)
