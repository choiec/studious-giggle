package org.cherrypick.picker.retrieval.application

import org.cherrypick.picker.documents.api.DocumentQueryApi
import org.cherrypick.picker.documents.api.dto.DocumentRetrievalView
import org.cherrypick.picker.documents.api.dto.DocumentSegmentView
import org.cherrypick.picker.retrieval.api.RetrievalApi
import org.cherrypick.picker.retrieval.api.dto.SearchHitView
import org.cherrypick.picker.retrieval.api.dto.SearchResultView
import org.cherrypick.picker.retrieval.domain.SearchQuery
import org.springframework.stereotype.Service

@Service
internal class RetrievalFacade(
    private val documentQueryApi: DocumentQueryApi,
) : RetrievalApi {
    override fun search(query: String): SearchResultView {
        val searchQuery = SearchQuery(query)
        if (searchQuery.isBlank()) {
            return SearchResultView(hits = emptyList())
        }

        val hits =
            documentQueryApi
                .listCurrentRetrievalDocuments()
                .flatMap { document ->
                    document.segments.mapNotNull { segment ->
                        toSearchHit(document, segment, searchQuery)
                    }
                }.sortedWith(
                    compareByDescending<ScoredHit> { it.score }
                        .thenBy { it.hit.documentId }
                        .thenBy { it.hit.segmentOrdinal },
                ).map { it.hit }

        return SearchResultView(hits = hits)
    }

    private fun toSearchHit(
        document: DocumentRetrievalView,
        segment: DocumentSegmentView,
        query: SearchQuery,
    ): ScoredHit? {
        val normalizedText = segment.text.lowercase()
        val phraseMatch = normalizedText.contains(query.normalized)
        val termMatches = query.terms.count { normalizedText.contains(it) }
        if (!phraseMatch && termMatches == 0) {
            return null
        }

        val score = termMatches + if (phraseMatch) query.terms.size + 2 else 0
        return ScoredHit(
            score = score,
            hit =
                SearchHitView(
                    documentId = document.documentId,
                    title = document.title,
                    revisionNumber = document.revisionNumber,
                    sourceId = document.sourceId,
                    segmentId = segment.segmentId,
                    segmentOrdinal = segment.ordinal,
                    locator = segment.locator,
                    snippet = buildSnippet(segment.text, query),
                ),
        )
    }

    private fun buildSnippet(
        text: String,
        query: SearchQuery,
    ): String {
        val normalizedText = text.lowercase()
        val matchIndex =
            query.terms
                .map { normalizedText.indexOf(it) }
                .filter { it >= 0 }
                .minOrNull() ?: 0
        val startIndex = maxOf(0, matchIndex - 24)
        val endIndex = minOf(text.length, startIndex + 120)
        val prefix = if (startIndex > 0) "..." else ""
        val suffix = if (endIndex < text.length) "..." else ""
        return prefix + text.substring(startIndex, endIndex).trim() + suffix
    }

    private data class ScoredHit(
        val score: Int,
        val hit: SearchHitView,
    )
}
