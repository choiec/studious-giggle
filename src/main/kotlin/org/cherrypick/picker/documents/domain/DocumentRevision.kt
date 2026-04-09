package org.cherrypick.picker.documents.domain

import org.cherrypick.picker.shared.hashing.ContentHash

data class DocumentRevision(
    val number: Int,
    val sourceId: String,
    val contentHash: ContentHash,
    val canonicalBody: String,
    val segments: List<DocumentSegment>,
)
