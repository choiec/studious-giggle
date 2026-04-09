package org.cherrypick.picker.ingest

import org.assertj.core.api.Assertions.assertThat
import org.cherrypick.picker.ingest.api.IngestApi
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.modulith.test.ApplicationModuleTest

@ApplicationModuleTest(mode = ApplicationModuleTest.BootstrapMode.ALL_DEPENDENCIES)
class IngestModuleTests {
    @Autowired
    lateinit var ingestApi: IngestApi

    @Test
    fun moduleBoots() {
        assertThat(ingestApi.importSample().documentId).startsWith("DOC-")
    }
}
