package org.cherrypick.picker.documents.domain

import org.cherrypick.picker.documents.api.dto.DocumentRetrievalView
import org.cherrypick.picker.documents.api.dto.DocumentView
import org.cherrypick.picker.documents.api.dto.RegisterDocumentCommand
import org.cherrypick.picker.documents.api.dto.RegisterDocumentSegment
import org.cherrypick.picker.shared.errors.ErrorCode
import org.cherrypick.picker.shared.errors.ValidationException
import org.cherrypick.picker.shared.hashing.ContentHash
import org.cherrypick.picker.shared.ids.DocumentId

data class Document(
    val id: DocumentId,
    val title: String,
    val status: DocumentStatus,
    val revisions: List<DocumentRevision>,
) {
    val currentRevision: DocumentRevision
        get() = revisions.last()

    fun register(command: RegisterDocumentCommand): Document {
        validate(command)
        val nextRevision = createRevision(id, currentRevision.number + 1, command)

        return copy(
            title = command.title.trim(),
            status = DocumentStatus.IMPORTED,
            revisions = revisions + nextRevision,
        )
    }

    fun toView(): DocumentView =
        DocumentView(
            id = id.value,
            title = title,
            status = status.name,
            currentRevision = currentRevision.number,
            revisionCount = revisions.size,
            latestSourceId = currentRevision.sourceId,
        )

    fun toRetrievalView(): DocumentRetrievalView =
        DocumentRetrievalView(
            documentId = id.value,
            title = title,
            revisionNumber = currentRevision.number,
            sourceId = currentRevision.sourceId,
            canonicalBody = currentRevision.canonicalBody,
            segments = currentRevision.segments.map { it.toView() },
        )

    companion object {
        fun bootstrap(): Document =
            create(
                RegisterDocumentCommand(
                    sourceId = "bootstrap",
                    title = "Canonical Document",
                    canonicalBody = "Canonical bootstrap document",
                ),
            ).copy(status = DocumentStatus.ACTIVE)

        fun create(command: RegisterDocumentCommand): Document {
            validate(command)
            val documentId = idFromSource(command.sourceId)

            return Document(
                id = documentId,
                title = command.title.trim(),
                status = DocumentStatus.IMPORTED,
                revisions = listOf(createRevision(documentId, 1, command)),
            )
        }

        fun idFromSource(sourceId: String): DocumentId {
            val normalizedSourceId = sourceId.trim()
            if (normalizedSourceId.isBlank()) {
                throw ValidationException(
                    ErrorCode.INVALID_SOURCE_ID,
                    "Document sourceId must not be blank",
                )
            }

            return DocumentId("DOC-${ContentHash.of(normalizedSourceId).value.take(8)}")
        }

        private fun createRevision(
            documentId: DocumentId,
            revisionNumber: Int,
            command: RegisterDocumentCommand,
        ): DocumentRevision {
            val normalizedCanonicalBody = command.canonicalBody.trim()
            return DocumentRevision(
                number = revisionNumber,
                sourceId = command.sourceId.trim(),
                contentHash = ContentHash.of(normalizedCanonicalBody),
                canonicalBody = normalizedCanonicalBody,
                segments = normalizeSegments(documentId, revisionNumber, normalizedCanonicalBody, command.segments),
            )
        }

        private fun normalizeSegments(
            documentId: DocumentId,
            revisionNumber: Int,
            canonicalBody: String,
            segments: List<RegisterDocumentSegment>,
        ): List<DocumentSegment> {
            if (segments.isEmpty()) {
                return listOf(DocumentSegment.fallbackFor(documentId, revisionNumber, canonicalBody))
            }

            return segments.sortedBy(RegisterDocumentSegment::ordinal).map {
                DocumentSegment.from(documentId, revisionNumber, it)
            }
        }

        private fun validate(command: RegisterDocumentCommand) {
            if (command.sourceId.isBlank()) {
                throw ValidationException(
                    ErrorCode.INVALID_SOURCE_ID,
                    "Document sourceId must not be blank",
                )
            }

            if (command.title.isBlank()) {
                throw ValidationException(
                    ErrorCode.INVALID_DOCUMENT_TITLE,
                    "Document title must not be blank",
                )
            }

            if (command.canonicalBody.isBlank()) {
                throw ValidationException(
                    ErrorCode.INVALID_DOCUMENT_CONTENT,
                    "Document content must not be blank",
                )
            }

            if (command.segments.any { it.text.isBlank() || it.ordinal < 1 }) {
                throw ValidationException(
                    ErrorCode.INVALID_DOCUMENT_CONTENT,
                    "Document segments must contain text and positive ordinals",
                )
            }

            if (command.segments
                    .map(RegisterDocumentSegment::ordinal)
                    .distinct()
                    .size != command.segments.size
            ) {
                throw ValidationException(
                    ErrorCode.INVALID_DOCUMENT_CONTENT,
                    "Document segments must use unique ordinals",
                )
            }
        }
    }
}
