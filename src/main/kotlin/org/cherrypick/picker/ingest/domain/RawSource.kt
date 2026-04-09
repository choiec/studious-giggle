package org.cherrypick.picker.ingest.domain

import org.cherrypick.picker.ingest.api.dto.IngestRequest
import org.cherrypick.picker.shared.errors.ErrorCode
import org.cherrypick.picker.shared.errors.ValidationException

data class RawSource(
    val sourceId: String,
    val format: String,
    val title: String,
    val payload: String,
) {
    fun normalizedFormat(): String = format.trim().lowercase()

    companion object {
        fun from(request: IngestRequest): RawSource {
            if (request.sourceId.isBlank()) {
                throw ValidationException(
                    ErrorCode.INVALID_SOURCE_ID,
                    "Ingest sourceId must not be blank",
                )
            }

            if (request.title.isBlank()) {
                throw ValidationException(
                    ErrorCode.INVALID_DOCUMENT_TITLE,
                    "Ingest title must not be blank",
                )
            }

            if (request.payload.isBlank()) {
                throw ValidationException(
                    ErrorCode.INVALID_INGEST_PAYLOAD,
                    "Ingest payload must not be blank",
                )
            }

            return RawSource(
                sourceId = request.sourceId.trim(),
                format = request.format.trim(),
                title = request.title.trim(),
                payload = request.payload.trim(),
            )
        }
    }
}
