package org.cherrypick.picker.indexing

import org.assertj.core.api.Assertions.assertThat
import org.cherrypick.picker.indexing.api.IndexingApi
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.modulith.test.ApplicationModuleTest

@ApplicationModuleTest(mode = ApplicationModuleTest.BootstrapMode.ALL_DEPENDENCIES)
class IndexingModuleTests {
    @Autowired
    lateinit var indexingApi: IndexingApi

    @Test
    fun moduleBoots() {
        assertThat(indexingApi.status().visibleDocuments).isPositive()
    }
}
