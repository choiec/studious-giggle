package org.cherrypick.picker.documents.domain

import org.cherrypick.picker.documents.api.dto.DocumentView
import org.cherrypick.picker.documents.api.dto.RegisterDocumentCommand
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
        val nextRevision =
            DocumentRevision(
                number = currentRevision.number + 1,
                sourceId = command.sourceId.trim(),
                contentHash = ContentHash.of(command.content.trim()),
                content = command.content.trim(),
            )

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

    companion object {
        fun bootstrap(): Document =
            create(
                RegisterDocumentCommand(
                    sourceId = "bootstrap",
                    title = "Canonical Document",
                    content = "Canonical bootstrap document",
                ),
            ).copy(status = DocumentStatus.ACTIVE)

        fun create(command: RegisterDocumentCommand): Document {
            validate(command)

            return Document(
                id = idFromSource(command.sourceId),
                title = command.title.trim(),
                status = DocumentStatus.IMPORTED,
                revisions =
                    listOf(
                        DocumentRevision(
                            number = 1,
                            sourceId = command.sourceId.trim(),
                            contentHash = ContentHash.of(command.content.trim()),
                            content = command.content.trim(),
                        ),
                    ),
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

            if (command.content.isBlank()) {
                throw ValidationException(
                    ErrorCode.INVALID_DOCUMENT_CONTENT,
                    "Document content must not be blank",
                )
            }
        }
    }
}
