package org.cherrypick.picker.documents.api.dto

data class DocumentRetrievalView(
    val documentId: String,
    val title: String,
    val revisionNumber: Int,
    val sourceId: String,
    val canonicalBody: String,
    val segments: List<DocumentSegmentView>,
)
