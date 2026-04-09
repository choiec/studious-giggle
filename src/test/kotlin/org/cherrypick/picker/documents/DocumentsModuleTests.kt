package org.cherrypick.picker.documents

import org.assertj.core.api.Assertions.assertThat
import org.cherrypick.picker.documents.api.DocumentCommandApi
import org.cherrypick.picker.documents.api.DocumentQueryApi
import org.cherrypick.picker.documents.api.dto.RegisterDocumentCommand
import org.cherrypick.picker.shared.errors.ErrorCode
import org.cherrypick.picker.shared.errors.ValidationException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.modulith.test.ApplicationModuleTest

@ApplicationModuleTest(mode = ApplicationModuleTest.BootstrapMode.ALL_DEPENDENCIES)
class DocumentsModuleTests {
    @Autowired
    lateinit var documentCommandApi: DocumentCommandApi

    @Autowired
    lateinit var documentQueryApi: DocumentQueryApi

    @Test
    fun registersAndRetrievesDocument() {
        val createdDocument =
            documentCommandApi.register(
                RegisterDocumentCommand(
                    sourceId = "documents-success",
                    title = "Procurement Guide",
                    content = "Normalized procurement content",
                ),
            )

        val storedDocument = documentQueryApi.getDocument(createdDocument.id)

        assertThat(storedDocument.id).isEqualTo(createdDocument.id)
        assertThat(storedDocument.title).isEqualTo("Procurement Guide")
        assertThat(storedDocument.currentRevision).isEqualTo(1)
        assertThat(storedDocument.revisionCount).isEqualTo(1)
        assertThat(storedDocument.latestSourceId).isEqualTo("documents-success")
    }

    @Test
    fun rejectsBlankContent() {
        val exception =
            assertThrows<ValidationException> {
                documentCommandApi.register(
                    RegisterDocumentCommand(
                        sourceId = "documents-invalid",
                        title = "Invalid Document",
                        content = " ",
                    ),
                )
            }

        assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_DOCUMENT_CONTENT)
    }

    @Test
    fun createsNewRevisionForRepeatedSourceId() {
        val firstDocument =
            documentCommandApi.register(
                RegisterDocumentCommand(
                    sourceId = "documents-revision",
                    title = "Revision One",
                    content = "first revision",
                ),
            )
        val secondDocument =
            documentCommandApi.register(
                RegisterDocumentCommand(
                    sourceId = "documents-revision",
                    title = "Revision Two",
                    content = "second revision",
                ),
            )

        val storedDocument = documentQueryApi.getDocument(secondDocument.id)

        assertThat(secondDocument.id).isEqualTo(firstDocument.id)
        assertThat(secondDocument.currentRevision).isEqualTo(2)
        assertThat(storedDocument.revisionCount).isEqualTo(2)
        assertThat(storedDocument.title).isEqualTo("Revision Two")
    }
}
