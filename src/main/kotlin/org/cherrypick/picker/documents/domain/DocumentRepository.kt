package org.cherrypick.picker.documents.domain

import org.cherrypick.picker.shared.ids.DocumentId

interface DocumentRepository {
    fun save(document: Document): Document

    fun findById(documentId: DocumentId): Document?

    fun findLatest(): Document?

    fun findAll(): List<Document>
}
