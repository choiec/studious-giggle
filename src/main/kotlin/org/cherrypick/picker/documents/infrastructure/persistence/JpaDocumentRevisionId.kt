package org.cherrypick.picker.documents.infrastructure.persistence

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.io.Serializable

@Embeddable
internal data class JpaDocumentRevisionId(
    @Column(name = "document_id", nullable = false, length = 12)
    var documentId: String = "",
    @Column(name = "revision_number", nullable = false)
    var revisionNumber: Int = 0,
) : Serializable
