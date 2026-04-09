package org.cherrypick.picker.ingest.infrastructure.storage

import org.springframework.data.jpa.repository.JpaRepository

internal interface SpringDataStoredSourceJpaRepository : JpaRepository<JpaStoredSourceEntity, String>
