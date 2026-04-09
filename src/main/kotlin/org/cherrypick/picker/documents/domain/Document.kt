package org.cherrypick.picker.documents.domain

import org.cherrypick.picker.documents.api.dto.DocumentView
import org.cherrypick.picker.shared.hashing.ContentHash
import org.cherrypick.picker.shared.ids.DocumentId

data class Document(
    val id: DocumentId,
    val title: String,
    val status: String,
    val contentHash: ContentHash,
) {
    fun toView(): DocumentView = DocumentView(id = id.value, title = title, status = status)

    companion object {
        fun canonical(): Document =
            Document(
                id = DocumentId("DOC-0001"),
                title = "Canonical Document",
                status = "ACTIVE",
                contentHash = ContentHash.of("canonical-document"),
            )

        fun create(title: String): Document =
            Document(
                id = DocumentId("DOC-${ContentHash.of(title).value.take(8)}"),
                title = title,
                status = "IMPORTED",
                contentHash = ContentHash.of(title),
            )
    }
}
