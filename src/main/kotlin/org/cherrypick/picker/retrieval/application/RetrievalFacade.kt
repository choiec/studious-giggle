package org.cherrypick.picker.retrieval.application

import org.cherrypick.picker.documents.api.DocumentQueryApi
import org.cherrypick.picker.indexing.api.IndexingApi
import org.cherrypick.picker.retrieval.api.RetrievalApi
import org.cherrypick.picker.retrieval.api.dto.SearchHitView
import org.cherrypick.picker.retrieval.api.dto.SearchResultView
import org.springframework.stereotype.Service

@Service
internal class RetrievalFacade(
    private val documentQueryApi: DocumentQueryApi,
    private val indexingApi: IndexingApi,
) : RetrievalApi {
    override fun search(query: String): SearchResultView {
        val canonical = documentQueryApi.getCanonicalDocument()
        val status = indexingApi.status()
        val hit =
            SearchHitView(
                documentId = canonical.id,
                title = canonical.title,
                snippet = "Matched '$query' with ${status.strategy}",
            )
        return SearchResultView(hits = listOf(hit))
    }
}
