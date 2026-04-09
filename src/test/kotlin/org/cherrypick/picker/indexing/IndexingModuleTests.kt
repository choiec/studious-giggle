package org.cherrypick.picker.indexing

import org.assertj.core.api.Assertions.assertThat
import org.cherrypick.picker.documents.api.DocumentCommandApi
import org.cherrypick.picker.documents.api.dto.RegisterDocumentCommand
import org.cherrypick.picker.indexing.api.IndexingApi
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.modulith.test.ApplicationModuleTest

@ApplicationModuleTest(mode = ApplicationModuleTest.BootstrapMode.ALL_DEPENDENCIES)
class IndexingModuleTests {
    @Autowired
    lateinit var indexingApi: IndexingApi

    @Autowired
    lateinit var documentCommandApi: DocumentCommandApi

    @Test
    fun reportsVisibleDocumentsFromStoredCorpus() {
        assertThat(indexingApi.status().visibleDocuments).isZero()

        documentCommandApi.register(
            RegisterDocumentCommand(
                sourceId = "indexing-visible-document",
                title = "Indexable Guide",
                canonicalBody = "Indexable study guide",
            ),
        )

        assertThat(indexingApi.status().visibleDocuments).isEqualTo(1)
        assertThat(indexingApi.status().strategy).isEqualTo("keyword")
    }
}
