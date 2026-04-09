package org.cherrypick.picker.indexing.application

import org.cherrypick.picker.documents.api.DocumentQueryApi
import org.cherrypick.picker.indexing.api.IndexingApi
import org.cherrypick.picker.indexing.api.dto.IndexStatusView
import org.springframework.stereotype.Service

@Service
internal class IndexingFacade(
    private val documentQueryApi: DocumentQueryApi,
) : IndexingApi {
    override fun status(): IndexStatusView {
        val canonical = documentQueryApi.getCanonicalDocument()
        val visibleDocuments = if (canonical.id.isBlank()) 0 else 1
        return IndexStatusView(visibleDocuments = visibleDocuments, strategy = "keyword+vector")
    }
}
