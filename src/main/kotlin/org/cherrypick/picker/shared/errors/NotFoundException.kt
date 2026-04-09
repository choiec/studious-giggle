package org.cherrypick.picker.shared.errors

class NotFoundException(
    errorCode: ErrorCode,
    message: String,
) : DomainException(errorCode, message)
