package org.cherrypick.picker

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.modulith.core.ApplicationModules

@SpringBootTest
class PickerApplicationTests(
    private val applicationContext: ApplicationContext,
) {
    @Test
    fun contextLoads() {
        assertThat(applicationContext.containsBean("dataSource")).isFalse()
    }

    @Test
    fun verifiesModularStructure() {
        ApplicationModules.of(PickerApplication::class.java).verify()
    }
}
