package org.cherrypick.picker.documents

import org.assertj.core.api.Assertions.assertThat
import org.cherrypick.picker.documents.api.DocumentQueryApi
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.modulith.test.ApplicationModuleTest

@ApplicationModuleTest(mode = ApplicationModuleTest.BootstrapMode.ALL_DEPENDENCIES)
class DocumentsModuleTests {
    @Autowired
    lateinit var documentQueryApi: DocumentQueryApi

    @Test
    fun moduleBoots() {
        assertThat(documentQueryApi.getCanonicalDocument().id).isNotBlank()
    }
}
