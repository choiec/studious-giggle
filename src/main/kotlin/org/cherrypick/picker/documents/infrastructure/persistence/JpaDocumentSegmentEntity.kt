package org.cherrypick.picker.documents.infrastructure.persistence

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinColumns
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.cherrypick.picker.documents.domain.DocumentSegment
import org.cherrypick.picker.shared.ids.SegmentId

@Entity
@Table(name = "document_revision_segments")
internal class JpaDocumentSegmentEntity(
    @Id
    @Column(name = "segment_id", nullable = false, length = 16)
    var segmentId: String = "",
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns(
        value = [
            JoinColumn(name = "document_id", referencedColumnName = "document_id", nullable = false),
            JoinColumn(name = "revision_number", referencedColumnName = "revision_number", nullable = false),
        ],
    )
    var revision: JpaDocumentRevisionEntity? = null,
    @Column(name = "segment_type", nullable = false, length = 32)
    var segmentType: String = "",
    @Column(name = "segment_ordinal", nullable = false)
    var ordinal: Int = 0,
    @Column(name = "token_estimate", nullable = false)
    var tokenEstimate: Int = 0,
    @Column(name = "locator", nullable = false, length = 255)
    var locator: String = "",
    @Column(name = "segment_text", nullable = false, columnDefinition = "text")
    var text: String = "",
) {
    fun toDomain(): DocumentSegment =
        DocumentSegment(
            id = SegmentId(segmentId),
            segmentType = segmentType,
            ordinal = ordinal,
            text = text,
            tokenEstimate = tokenEstimate,
            locator = locator,
        )

    companion object {
        fun from(
            revision: JpaDocumentRevisionEntity,
            segment: DocumentSegment,
        ): JpaDocumentSegmentEntity =
            JpaDocumentSegmentEntity(
                segmentId = segment.id.value,
                revision = revision,
                segmentType = segment.segmentType,
                ordinal = segment.ordinal,
                tokenEstimate = segment.tokenEstimate,
                locator = segment.locator,
                text = segment.text,
            )
    }
}
