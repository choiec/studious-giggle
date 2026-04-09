package org.cherrypick.picker

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.modulith.core.ApplicationModules

@SpringBootTest
class PickerApplicationTests {
    @Test
    fun contextLoads() {
    }

    @Test
    fun verifiesModularStructure() {
        ApplicationModules.of(PickerApplication::class.java).verify()
    }
}
