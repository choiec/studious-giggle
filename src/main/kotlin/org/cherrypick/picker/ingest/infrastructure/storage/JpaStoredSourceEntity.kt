package org.cherrypick.picker.ingest.infrastructure.storage

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.cherrypick.picker.ingest.domain.RawSource
import org.cherrypick.picker.ingest.domain.StoredSource
import java.time.Instant

@Entity
@Table(name = "ingest_sources")
internal class JpaStoredSourceEntity(
    @Id
    @Column(name = "source_id", nullable = false)
    var sourceId: String = "",
    @Column(name = "format", nullable = false, length = 32)
    var format: String = "",
    @Column(name = "title", nullable = false)
    var title: String = "",
    @Column(name = "payload", nullable = false, columnDefinition = "text")
    var payload: String = "",
    @Column(name = "stored_at", nullable = false)
    var storedAt: Instant = Instant.EPOCH,
) {
    fun toDomain(): StoredSource =
        StoredSource(
            sourceId = sourceId,
            format = format,
            title = title,
            payload = payload,
        )

    companion object {
        fun from(source: RawSource): JpaStoredSourceEntity =
            JpaStoredSourceEntity(
                sourceId = source.sourceId,
                format = source.normalizedFormat(),
                title = source.title,
                payload = source.payload,
                storedAt = Instant.now(),
            )
    }
}
