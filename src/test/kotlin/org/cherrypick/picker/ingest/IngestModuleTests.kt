package org.cherrypick.picker.ingest

import org.assertj.core.api.Assertions.assertThat
import org.cherrypick.picker.documents.api.DocumentQueryApi
import org.cherrypick.picker.ingest.api.IngestApi
import org.cherrypick.picker.ingest.api.dto.IngestRequest
import org.cherrypick.picker.shared.errors.ErrorCode
import org.cherrypick.picker.shared.errors.ValidationException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.modulith.test.ApplicationModuleTest

@ApplicationModuleTest(mode = ApplicationModuleTest.BootstrapMode.ALL_DEPENDENCIES)
class IngestModuleTests {
    @Autowired
    lateinit var ingestApi: IngestApi

    @Autowired
    lateinit var documentQueryApi: DocumentQueryApi

    @Test
    fun importsDocumentAndMakesItQueryable() {
        val ingestResult =
            ingestApi.importDocument(
                IngestRequest(
                    sourceId = "ingest-success",
                    format = "xml",
                    title = "Imported Policy",
                    payload = "<document><p>Imported Policy Content</p></document>",
                ),
            )

        val storedDocument = documentQueryApi.getDocument(ingestResult.documentId)

        assertThat(ingestResult.revisionNumber).isEqualTo(1)
        assertThat(storedDocument.title).isEqualTo("Imported Policy")
        assertThat(storedDocument.latestSourceId).isEqualTo("ingest-success")
    }

    @Test
    fun rejectsUnsupportedFormat() {
        val exception =
            assertThrows<ValidationException> {
                ingestApi.importDocument(
                    IngestRequest(
                        sourceId = "ingest-unsupported",
                        format = "json",
                        title = "Imported Json",
                        payload = "{\"title\":\"Json\"}",
                    ),
                )
            }

        assertThat(exception.errorCode).isEqualTo(ErrorCode.UNSUPPORTED_SOURCE_FORMAT)
    }

    @Test
    fun reimportsSameSourceAsNewRevision() {
        val firstImport =
            ingestApi.importDocument(
                IngestRequest(
                    sourceId = "ingest-revision",
                    format = "xml",
                    title = "Revision One",
                    payload = "<document><p>first import</p></document>",
                ),
            )
        val secondImport =
            ingestApi.importDocument(
                IngestRequest(
                    sourceId = "ingest-revision",
                    format = "xml",
                    title = "Revision Two",
                    payload = "<document><p>second import</p></document>",
                ),
            )

        val storedDocument = documentQueryApi.getDocument(secondImport.documentId)

        assertThat(secondImport.documentId).isEqualTo(firstImport.documentId)
        assertThat(secondImport.revisionNumber).isEqualTo(2)
        assertThat(storedDocument.revisionCount).isEqualTo(2)
        assertThat(storedDocument.title).isEqualTo("Revision Two")
    }
}
