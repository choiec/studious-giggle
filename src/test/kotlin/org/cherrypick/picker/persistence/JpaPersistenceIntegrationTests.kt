package org.cherrypick.picker.persistence

import org.assertj.core.api.Assertions.assertThat
import org.cherrypick.picker.documents.api.DocumentCommandApi
import org.cherrypick.picker.documents.api.DocumentQueryApi
import org.cherrypick.picker.documents.api.dto.RegisterDocumentCommand
import org.cherrypick.picker.ingest.api.IngestApi
import org.cherrypick.picker.ingest.api.dto.IngestRequest
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestConstructor
import javax.sql.DataSource

@SpringBootTest
@ActiveProfiles("jpa-test")
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class JpaPersistenceIntegrationTests(
    private val applicationContext: ApplicationContext,
    private val dataSource: DataSource,
    private val documentCommandApi: DocumentCommandApi,
    private val documentQueryApi: DocumentQueryApi,
    private val ingestApi: IngestApi,
) {
    @Test
    fun jpaProfilePersistsDocumentRevisions() {
        val firstDocument =
            documentCommandApi.register(
                RegisterDocumentCommand(
                    sourceId = "jpa-documents-revision",
                    title = "JPA Revision One",
                    canonicalBody = "first persisted revision",
                ),
            )
        val secondDocument =
            documentCommandApi.register(
                RegisterDocumentCommand(
                    sourceId = "jpa-documents-revision",
                    title = "JPA Revision Two",
                    canonicalBody = "second persisted revision",
                ),
            )

        val storedDocument = documentQueryApi.getDocument(secondDocument.id)
        val retrievalDocument = documentQueryApi.listCurrentRetrievalDocuments().single { it.documentId == secondDocument.id }
        val appliedMigrations = countRows("select count(*) from flyway_schema_history where success = true")

        assertThat(applicationContext.containsBean("dataSource")).isTrue()
        assertThat(secondDocument.id).isEqualTo(firstDocument.id)
        assertThat(storedDocument.revisionCount).isEqualTo(2)
        assertThat(storedDocument.currentRevision).isEqualTo(2)
        assertThat(retrievalDocument.revisionNumber).isEqualTo(2)
        assertThat(retrievalDocument.segments).hasSize(1)
        assertThat(retrievalDocument.segments.single().text).isEqualTo("second persisted revision")
        assertThat(appliedMigrations).isGreaterThan(0)
    }

    @Test
    fun jpaProfilePersistsIngestedSourcesAndSegments() {
        val ingestResult =
            ingestApi.importDocument(
                IngestRequest(
                    sourceId = "jpa-ingest-source",
                    format = "xml",
                    title = "JPA Imported Document",
                    payload = "<document><section><p>Persisted through JPA</p><p>With segment metadata</p></section></document>",
                ),
            )

        val storedDocument = documentQueryApi.getDocument(ingestResult.documentId)
        val retrievalDocument = documentQueryApi.listCurrentRetrievalDocuments().single { it.documentId == ingestResult.documentId }
        val storedSources = countRows("select count(*) from ingest_sources where source_id = ?", ingestResult.storedSourceId)
        val storedSegments = countRows("select count(*) from document_revision_segments where document_id = ?", ingestResult.documentId)

        assertThat(storedDocument.title).isEqualTo("JPA Imported Document")
        assertThat(storedDocument.latestSourceId).isEqualTo("jpa-ingest-source")
        assertThat(storedSources).isEqualTo(1)
        assertThat(storedSegments).isEqualTo(2)
        assertThat(retrievalDocument.segments).hasSize(2)
        assertThat(retrievalDocument.segments.first().locator).isEqualTo("/document[1]/section[1]/p[1]")
    }

    private fun countRows(
        sql: String,
        vararg params: Any,
    ): Int =
        dataSource.connection.use { connection ->
            connection.prepareStatement(sql).use { statement ->
                params.forEachIndexed { index, value -> statement.setObject(index + 1, value) }

                statement.executeQuery().use { resultSet ->
                    check(resultSet.next()) { "Expected a row count result" }
                    resultSet.getInt(1)
                }
            }
        }
}
