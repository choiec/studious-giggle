package org.cherrypick.picker.retrieval.api.dto

data class SearchHitView(
    val documentId: String,
    val title: String,
    val snippet: String,
)
