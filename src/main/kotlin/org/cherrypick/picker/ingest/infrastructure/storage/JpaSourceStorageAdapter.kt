package org.cherrypick.picker.ingest.infrastructure.storage

import org.cherrypick.picker.ingest.domain.RawSource
import org.cherrypick.picker.ingest.domain.SourceStorage
import org.cherrypick.picker.ingest.domain.StoredSource
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Repository

@Repository
@ConditionalOnProperty(
    prefix = "picker.persistence",
    name = ["mode"],
    havingValue = "jpa",
)
internal class JpaSourceStorageAdapter(
    private val repository: SpringDataStoredSourceJpaRepository,
) : SourceStorage {
    override fun save(source: RawSource): StoredSource = repository.save(JpaStoredSourceEntity.from(source)).toDomain()

    override fun findBySourceId(sourceId: String): StoredSource? = repository.findById(sourceId).orElse(null)?.toDomain()
}
