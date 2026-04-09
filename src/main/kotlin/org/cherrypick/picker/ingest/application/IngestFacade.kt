package org.cherrypick.picker.ingest.application

import org.cherrypick.picker.documents.api.DocumentCommandApi
import org.cherrypick.picker.documents.api.dto.RegisterDocumentCommand
import org.cherrypick.picker.ingest.api.IngestApi
import org.cherrypick.picker.ingest.api.dto.IngestRequest
import org.cherrypick.picker.ingest.api.dto.IngestResult
import org.cherrypick.picker.ingest.domain.RawSource
import org.cherrypick.picker.ingest.domain.SourceStorage
import org.cherrypick.picker.ingest.parser.SourceParser
import org.cherrypick.picker.shared.errors.ErrorCode
import org.cherrypick.picker.shared.errors.ValidationException
import org.springframework.beans.factory.ObjectProvider
import org.springframework.stereotype.Service
import org.springframework.transaction.support.TransactionTemplate

@Service
internal class IngestFacade(
    private val documentCommandApi: DocumentCommandApi,
    private val sourceStorage: SourceStorage,
    private val sourceParsers: List<SourceParser>,
    private val transactionTemplateProvider: ObjectProvider<TransactionTemplate>,
) : IngestApi {
    override fun importDocument(request: IngestRequest): IngestResult {
        val transactionTemplate = transactionTemplateProvider.ifAvailable ?: return doImportDocument(request)

        return requireNotNull(transactionTemplate.execute<IngestResult> { doImportDocument(request) }) {
            "Transaction execution returned null for source ${request.sourceId}"
        }
    }

    override fun importSample(): IngestResult =
        importDocument(
            IngestRequest(
                sourceId = "sample-xml",
                format = "xml",
                title = "Imported XML",
                payload = "<document><p>Imported XML</p></document>",
            ),
        )

    private fun doImportDocument(request: IngestRequest): IngestResult {
        val rawSource = RawSource.from(request)
        val parser =
            sourceParsers.firstOrNull { it.supports(rawSource.normalizedFormat()) }
                ?: throw ValidationException(
                    ErrorCode.UNSUPPORTED_SOURCE_FORMAT,
                    "Unsupported source format '${rawSource.format}'",
                )

        val parsedSource = parser.parse(rawSource)
        val storedSource = sourceStorage.save(rawSource)
        val document =
            documentCommandApi.register(
                RegisterDocumentCommand(
                    sourceId = parsedSource.sourceId,
                    title = parsedSource.title,
                    content = parsedSource.content,
                ),
            )

        return IngestResult(
            documentId = document.id,
            message = "Imported ${document.title} from ${storedSource.format}",
            revisionNumber = document.currentRevision,
            storedSourceId = storedSource.sourceId,
        )
    }
}
