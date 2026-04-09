package org.cherrypick.picker.documents.infrastructure.persistence

import org.springframework.data.jpa.repository.JpaRepository

internal interface SpringDataDocumentJpaRepository : JpaRepository<JpaDocumentEntity, String> {
    fun findTopByOrderByUpdatedAtDesc(): JpaDocumentEntity?
}
