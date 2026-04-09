package org.cherrypick.picker.knowledge

import org.assertj.core.api.Assertions.assertThat
import org.cherrypick.picker.knowledge.api.KnowledgeApi
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.modulith.test.ApplicationModuleTest

@ApplicationModuleTest(mode = ApplicationModuleTest.BootstrapMode.ALL_DEPENDENCIES)
class KnowledgeModuleTests {
    @Autowired
    lateinit var knowledgeApi: KnowledgeApi

    @Test
    fun moduleBoots() {
        assertThat(knowledgeApi.facts()).hasSize(1)
    }
}
