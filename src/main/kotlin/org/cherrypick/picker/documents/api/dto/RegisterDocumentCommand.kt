package org.cherrypick.picker.documents.api.dto

data class RegisterDocumentCommand(
    val sourceId: String,
    val title: String,
    val content: String,
)
