package org.cherrypick.picker.ingest.application

import org.cherrypick.picker.documents.api.DocumentCommandApi
import org.cherrypick.picker.ingest.api.IngestApi
import org.cherrypick.picker.ingest.api.dto.IngestResult
import org.springframework.stereotype.Service

@Service
internal class IngestFacade(
    private val documentCommandApi: DocumentCommandApi,
) : IngestApi {
    override fun importSample(): IngestResult {
        val document = documentCommandApi.register("Imported XML")
        return IngestResult(documentId = document.id, message = "Imported ${document.title}")
    }
}
