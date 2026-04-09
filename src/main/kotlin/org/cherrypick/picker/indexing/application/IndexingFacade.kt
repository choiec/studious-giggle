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
        val visibleDocuments = documentQueryApi.listCurrentRetrievalDocuments().size
        return IndexStatusView(visibleDocuments = visibleDocuments, strategy = "keyword")
    }
}
