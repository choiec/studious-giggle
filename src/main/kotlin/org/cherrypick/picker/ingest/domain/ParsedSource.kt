package org.cherrypick.picker.ingest.domain

data class ParsedSource(
    val sourceId: String,
    val format: String,
    val title: String,
    val canonicalBody: String,
    val segments: List<ParsedSegment>,
)
