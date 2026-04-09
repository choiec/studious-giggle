package org.cherrypick.picker.documents.infrastructure.persistence

import jakarta.persistence.Column
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.MapsId
import jakarta.persistence.Table
import org.cherrypick.picker.documents.domain.DocumentRevision
import org.cherrypick.picker.shared.hashing.ContentHash

@Entity
@Table(name = "document_revisions")
internal class JpaDocumentRevisionEntity(
    @EmbeddedId
    var id: JpaDocumentRevisionId = JpaDocumentRevisionId(),
    @MapsId("documentId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id", nullable = false)
    var document: JpaDocumentEntity? = null,
    @Column(name = "source_id", nullable = false)
    var sourceId: String = "",
    @Column(name = "content_hash", nullable = false, length = 64)
    var contentHash: String = "",
    @Column(name = "content", nullable = false, columnDefinition = "text")
    var content: String = "",
) {
    fun toDomain(): DocumentRevision =
        DocumentRevision(
            number = id.revisionNumber,
            sourceId = sourceId,
            contentHash = ContentHash(contentHash),
            content = content,
        )

    companion object {
        fun from(
            document: JpaDocumentEntity,
            revision: DocumentRevision,
        ): JpaDocumentRevisionEntity =
            JpaDocumentRevisionEntity(
                id = JpaDocumentRevisionId(document.documentId, revision.number),
                document = document,
                sourceId = revision.sourceId,
                contentHash = revision.contentHash.value,
                content = revision.content,
            )
    }
}
