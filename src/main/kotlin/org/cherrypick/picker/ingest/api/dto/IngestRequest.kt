package org.cherrypick.picker.ingest.api.dto

data class IngestRequest(
    val sourceId: String,
    val format: String,
    val title: String,
    val payload: String,
)
