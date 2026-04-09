package org.cherrypick.picker.ingest.api

import org.cherrypick.picker.ingest.api.dto.IngestRequest
import org.cherrypick.picker.ingest.api.dto.IngestResult

interface IngestApi {
    fun importDocument(request: IngestRequest): IngestResult

    fun importSample(): IngestResult
}
