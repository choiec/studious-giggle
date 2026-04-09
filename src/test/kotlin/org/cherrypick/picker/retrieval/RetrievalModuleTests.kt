package org.cherrypick.picker.retrieval

import org.assertj.core.api.Assertions.assertThat
import org.cherrypick.picker.retrieval.api.RetrievalApi
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.modulith.test.ApplicationModuleTest

@ApplicationModuleTest(mode = ApplicationModuleTest.BootstrapMode.ALL_DEPENDENCIES)
class RetrievalModuleTests {
    @Autowired
    lateinit var retrievalApi: RetrievalApi

    @Test
    fun moduleBoots() {
        assertThat(retrievalApi.search("test").hits).hasSize(1)
    }
}
