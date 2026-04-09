package org.cherrypick.picker.documents.domain

import org.cherrypick.picker.documents.api.dto.DocumentSegmentView
import org.cherrypick.picker.documents.api.dto.RegisterDocumentSegment
import org.cherrypick.picker.shared.hashing.ContentHash
import org.cherrypick.picker.shared.ids.DocumentId
import org.cherrypick.picker.shared.ids.SegmentId

data class DocumentSegment(
    val id: SegmentId,
    val segmentType: String,
    val ordinal: Int,
    val text: String,
    val tokenEstimate: Int,
    val locator: String,
) {
    fun toView(): DocumentSegmentView =
        DocumentSegmentView(
            segmentId = id.value,
            segmentType = segmentType,
            ordinal = ordinal,
            text = text,
            tokenEstimate = tokenEstimate,
            locator = locator,
        )

    companion object {
        fun from(
            documentId: DocumentId,
            revisionNumber: Int,
            segment: RegisterDocumentSegment,
        ): DocumentSegment {
            val normalizedText = segment.text.trim()
            return DocumentSegment(
                id = segmentId(documentId, revisionNumber, segment.ordinal),
                segmentType = segment.segmentType.trim().ifBlank { "body" },
                ordinal = segment.ordinal,
                text = normalizedText,
                tokenEstimate = if (segment.tokenEstimate > 0) segment.tokenEstimate else estimateTokens(normalizedText),
                locator = segment.locator.trim().ifBlank { "/document/body[${segment.ordinal}]" },
            )
        }

        fun fallbackFor(
            documentId: DocumentId,
            revisionNumber: Int,
            canonicalBody: String,
        ): DocumentSegment =
            from(
                documentId = documentId,
                revisionNumber = revisionNumber,
                segment =
                    RegisterDocumentSegment(
                        segmentType = "body",
                        ordinal = 1,
                        text = canonicalBody,
                        tokenEstimate = estimateTokens(canonicalBody),
                        locator = "/document/body[1]",
                    ),
            )

        private fun estimateTokens(text: String): Int = maxOf(1, (text.length + 3) / 4)

        private fun segmentId(
            documentId: DocumentId,
            revisionNumber: Int,
            ordinal: Int,
        ): SegmentId = SegmentId("SEG-${ContentHash.of("${documentId.value}:$revisionNumber:$ordinal").value.take(12)}")
    }
}
