package org.cherrypick.picker.documents.api.dto

data class DocumentView(
    val id: String,
    val title: String,
    val status: String,
    val currentRevision: Int,
    val revisionCount: Int,
    val latestSourceId: String,
)
