package org.cherrypick.picker.documents.api.dto

data class DocumentSegmentView(
    val segmentId: String,
    val segmentType: String,
    val ordinal: Int,
    val text: String,
    val tokenEstimate: Int,
    val locator: String,
)
