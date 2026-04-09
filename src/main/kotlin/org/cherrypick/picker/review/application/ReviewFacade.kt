package org.cherrypick.picker.review.application

import org.cherrypick.picker.documents.api.DocumentQueryApi
import org.cherrypick.picker.retrieval.api.RetrievalApi
import org.cherrypick.picker.review.api.ReviewApi
import org.cherrypick.picker.review.api.dto.ReviewTaskView
import org.springframework.stereotype.Service

@Service
internal class ReviewFacade(
    private val documentQueryApi: DocumentQueryApi,
    private val retrievalApi: RetrievalApi,
) : ReviewApi {
    override fun openQueue(): ReviewTaskView {
        val canonical = documentQueryApi.getCanonicalDocument()
        val result = retrievalApi.search("review")
        return ReviewTaskView(
            taskId = "REVIEW-0001",
            title = canonical.title,
            searchHits = result.hits.size,
        )
    }
}
