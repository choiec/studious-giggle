package org.cherrypick.picker.ops.application

import org.cherrypick.picker.documents.api.DocumentQueryApi
import org.cherrypick.picker.indexing.api.IndexingApi
import org.cherrypick.picker.ingest.api.IngestApi
import org.cherrypick.picker.knowledge.api.KnowledgeApi
import org.cherrypick.picker.ops.api.AdminApi
import org.cherrypick.picker.ops.api.dto.JobStatusView
import org.cherrypick.picker.retrieval.api.RetrievalApi
import org.cherrypick.picker.review.api.ReviewApi
import org.springframework.stereotype.Service

@Service
internal class OpsFacade(
    private val documentQueryApi: DocumentQueryApi,
    private val ingestApi: IngestApi,
    private val indexingApi: IndexingApi,
    private val retrievalApi: RetrievalApi,
    private val reviewApi: ReviewApi,
    private val knowledgeApi: KnowledgeApi,
) : AdminApi {
    override fun status(): JobStatusView {
        val canonical = documentQueryApi.getCanonicalDocument()
        val ingest = ingestApi.importSample()
        val indexing = indexingApi.status()
        val retrieval = retrievalApi.search("ops")
        val retrievalDocumentId = retrieval.hits.firstOrNull()?.documentId ?: canonical.id
        val review = reviewApi.openQueue()
        val facts = knowledgeApi.facts()
        val summary =
            listOf(
                canonical.id,
                ingest.documentId,
                indexing.strategy,
                retrievalDocumentId,
                review.taskId,
                facts.first().subject,
            ).joinToString(separator = " | ")
        return JobStatusView(summary = summary, healthy = true)
    }
}
