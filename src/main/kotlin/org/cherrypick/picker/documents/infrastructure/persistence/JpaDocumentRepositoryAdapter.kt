package org.cherrypick.picker.documents.infrastructure.persistence

import org.cherrypick.picker.documents.domain.Document
import org.cherrypick.picker.documents.domain.DocumentRepository
import org.cherrypick.picker.shared.ids.DocumentId
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Repository

@Repository
@ConditionalOnProperty(
    prefix = "picker.persistence",
    name = ["mode"],
    havingValue = "jpa",
)
internal class JpaDocumentRepositoryAdapter(
    private val repository: SpringDataDocumentJpaRepository,
) : DocumentRepository {
    override fun save(document: Document): Document = repository.save(JpaDocumentEntity.from(document)).toDomain()

    override fun findById(documentId: DocumentId): Document? = repository.findById(documentId.value).orElse(null)?.toDomain()

    override fun findLatest(): Document? = repository.findTopByOrderByUpdatedAtDesc()?.toDomain()

    override fun findAll(): List<Document> = repository.findAllByOrderByUpdatedAtDesc().map { it.toDomain() }
}
