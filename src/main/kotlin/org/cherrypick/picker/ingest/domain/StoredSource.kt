package org.cherrypick.picker.ingest.domain

data class StoredSource(
    val sourceId: String,
    val format: String,
    val title: String,
    val payload: String,
)
