package org.cherrypick.picker.documents.infrastructure.persistence

import org.cherrypick.picker.documents.domain.Document
import org.cherrypick.picker.documents.domain.DocumentRepository
import org.cherrypick.picker.shared.ids.DocumentId
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Repository
import java.util.LinkedHashMap

@Repository
@ConditionalOnProperty(
    prefix = "picker.persistence",
    name = ["mode"],
    havingValue = "in-memory",
    matchIfMissing = true,
)
internal class InMemoryDocumentRepository : DocumentRepository {
    private val documents = LinkedHashMap<String, Document>()

    override fun save(document: Document): Document =
        synchronized(this) {
            documents.remove(document.id.value)
            documents[document.id.value] = document
            document
        }

    override fun findById(documentId: DocumentId): Document? =
        synchronized(this) {
            documents[documentId.value]
        }

    override fun findLatest(): Document? =
        synchronized(this) {
            documents.values.lastOrNull()
        }
}
