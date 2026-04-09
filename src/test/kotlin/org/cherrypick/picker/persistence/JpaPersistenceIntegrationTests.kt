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
                    content = "first persisted revision",
                ),
            )
        val secondDocument =
            documentCommandApi.register(
                RegisterDocumentCommand(
                    sourceId = "jpa-documents-revision",
                    title = "JPA Revision Two",
                    content = "second persisted revision",
                ),
            )

        val storedDocument = documentQueryApi.getDocument(secondDocument.id)
        val appliedMigrations = countRows("select count(*) from flyway_schema_history where success = true")

        assertThat(applicationContext.containsBean("dataSource")).isTrue()
        assertThat(secondDocument.id).isEqualTo(firstDocument.id)
        assertThat(storedDocument.revisionCount).isEqualTo(2)
        assertThat(storedDocument.currentRevision).isEqualTo(2)
        assertThat(appliedMigrations).isGreaterThan(0)
    }

    @Test
    fun jpaProfilePersistsIngestedSourcesAndDocuments() {
        val ingestResult =
            ingestApi.importDocument(
                IngestRequest(
                    sourceId = "jpa-ingest-source",
                    format = "xml",
                    title = "JPA Imported Document",
                    payload = "<document><p>Persisted through JPA</p></document>",
                ),
            )

        val storedDocument = documentQueryApi.getDocument(ingestResult.documentId)
        val storedSources = countRows("select count(*) from ingest_sources where source_id = ?", ingestResult.storedSourceId)

        assertThat(storedDocument.title).isEqualTo("JPA Imported Document")
        assertThat(storedDocument.latestSourceId).isEqualTo("jpa-ingest-source")
        assertThat(storedSources).isEqualTo(1)
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
