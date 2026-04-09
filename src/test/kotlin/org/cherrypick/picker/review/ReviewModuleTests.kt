package org.cherrypick.picker.review

import org.assertj.core.api.Assertions.assertThat
import org.cherrypick.picker.review.api.ReviewApi
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.modulith.test.ApplicationModuleTest

@ApplicationModuleTest(mode = ApplicationModuleTest.BootstrapMode.ALL_DEPENDENCIES)
class ReviewModuleTests {
    @Autowired
    lateinit var reviewApi: ReviewApi

    @Test
    fun moduleBoots() {
        assertThat(reviewApi.openQueue().taskId).isEqualTo("REVIEW-0001")
    }
}
