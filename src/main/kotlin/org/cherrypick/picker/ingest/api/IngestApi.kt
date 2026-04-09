package org.cherrypick.picker.ingest.api

import org.cherrypick.picker.ingest.api.dto.IngestResult

interface IngestApi {
    fun importSample(): IngestResult
}
