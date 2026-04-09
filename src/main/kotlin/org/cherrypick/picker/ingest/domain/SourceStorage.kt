package org.cherrypick.picker.ingest.domain

interface SourceStorage {
    fun save(source: RawSource): StoredSource

    fun findBySourceId(sourceId: String): StoredSource?
}
