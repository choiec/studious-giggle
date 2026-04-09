package org.cherrypick.picker.ingest.domain

data class ParsedSegment(
    val segmentType: String,
    val ordinal: Int,
    val text: String,
    val tokenEstimate: Int,
    val locator: String,
)
