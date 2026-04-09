package org.cherrypick.picker.documents.infrastructure.persistence

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.OrderBy
import jakarta.persistence.Table
import org.cherrypick.picker.documents.domain.Document
import org.cherrypick.picker.documents.domain.DocumentStatus
import org.cherrypick.picker.shared.ids.DocumentId
import java.time.Instant

@Entity
@Table(name = "documents")
internal class JpaDocumentEntity(
    @Id
    @Column(name = "document_id", nullable = false, length = 12)
    var documentId: String = "",
    @Column(name = "title", nullable = false)
    var title: String = "",
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 32)
    var status: DocumentStatus = DocumentStatus.IMPORTED,
    @Column(name = "updated_at", nullable = false)
    var updatedAt: Instant = Instant.EPOCH,
    @OneToMany(
        mappedBy = "document",
        cascade = [CascadeType.ALL],
        orphanRemoval = true,
        fetch = FetchType.EAGER,
    )
    @OrderBy("id.revisionNumber ASC")
    var revisions: MutableList<JpaDocumentRevisionEntity> = mutableListOf(),
) {
    fun toDomain(): Document =
        Document(
            id = DocumentId(documentId),
            title = title,
            status = status,
            revisions = revisions.sortedBy { it.id.revisionNumber }.map { it.toDomain() },
        )

    companion object {
        fun from(document: Document): JpaDocumentEntity {
            val entity =
                JpaDocumentEntity(
                    documentId = document.id.value,
                    title = document.title,
                    status = document.status,
                    updatedAt = Instant.now(),
                )

            entity.revisions =
                document.revisions
                    .map { JpaDocumentRevisionEntity.from(entity, it) }
                    .toMutableList()

            return entity
        }
    }
}
