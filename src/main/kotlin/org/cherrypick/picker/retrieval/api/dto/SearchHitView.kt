package org.cherrypick.picker.retrieval.api.dto

data class SearchHitView(
    val documentId: String,
    val title: String,
    val revisionNumber: Int,
    val sourceId: String,
    val segmentId: String,
    val segmentOrdinal: Int,
    val locator: String,
    val snippet: String,
)
