package org.cherrypick.picker.ingest.infrastructure.storage

import org.cherrypick.picker.ingest.domain.RawSource
import org.cherrypick.picker.ingest.domain.SourceStorage
import org.cherrypick.picker.ingest.domain.StoredSource
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Repository
import java.util.LinkedHashMap

@Repository
@ConditionalOnProperty(
    prefix = "picker.persistence",
    name = ["mode"],
    havingValue = "in-memory",
    matchIfMissing = true,
)
internal class InMemorySourceStorage : SourceStorage {
    private val sources = LinkedHashMap<String, StoredSource>()

    override fun save(source: RawSource): StoredSource =
        synchronized(this) {
            val storedSource =
                StoredSource(
                    sourceId = source.sourceId,
                    format = source.normalizedFormat(),
                    title = source.title,
                    payload = source.payload,
                )
            sources[storedSource.sourceId] = storedSource
            storedSource
        }

    override fun findBySourceId(sourceId: String): StoredSource? =
        synchronized(this) {
            sources[sourceId]
        }
}
