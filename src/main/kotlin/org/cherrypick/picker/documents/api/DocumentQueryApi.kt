package org.cherrypick.picker.documents.api

import org.cherrypick.picker.documents.api.dto.DocumentRetrievalView
import org.cherrypick.picker.documents.api.dto.DocumentView

interface DocumentQueryApi {
    fun getDocument(documentId: String): DocumentView

    fun findLatestDocument(): DocumentView?

    fun getCanonicalDocument(): DocumentView

    fun listCurrentRetrievalDocuments(): List<DocumentRetrievalView>
}
