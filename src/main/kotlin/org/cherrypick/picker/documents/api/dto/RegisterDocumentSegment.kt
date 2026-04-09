package org.cherrypick.picker.documents.api.dto

data class RegisterDocumentSegment(
    val segmentType: String,
    val ordinal: Int,
    val text: String,
    val tokenEstimate: Int,
    val locator: String,
)
