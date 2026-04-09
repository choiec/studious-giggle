package org.cherrypick.picker.ops

import org.assertj.core.api.Assertions.assertThat
import org.cherrypick.picker.ops.api.AdminApi
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.modulith.test.ApplicationModuleTest

@ApplicationModuleTest(mode = ApplicationModuleTest.BootstrapMode.ALL_DEPENDENCIES)
class OpsModuleTests {
    @Autowired
    lateinit var adminApi: AdminApi

    @Test
    fun moduleBoots() {
        assertThat(adminApi.status().healthy).isTrue()
    }
}
