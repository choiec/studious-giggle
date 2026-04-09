package org.cherrypick.picker.retrieval

import org.assertj.core.api.Assertions.assertThat
import org.cherrypick.picker.documents.api.DocumentCommandApi
import org.cherrypick.picker.documents.api.dto.RegisterDocumentCommand
import org.cherrypick.picker.documents.api.dto.RegisterDocumentSegment
import org.cherrypick.picker.retrieval.api.RetrievalApi
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.modulith.test.ApplicationModuleTest

@ApplicationModuleTest(mode = ApplicationModuleTest.BootstrapMode.ALL_DEPENDENCIES)
class RetrievalModuleTests {
    @Autowired
    lateinit var retrievalApi: RetrievalApi

    @Autowired
    lateinit var documentCommandApi: DocumentCommandApi

    @Test
    fun returnsSegmentAwareHits() {
        documentCommandApi.register(
            RegisterDocumentCommand(
                sourceId = "retrieval-review-handbook",
                title = "Review Handbook",
                canonicalBody =
                    "Review checklist for policy updates.\n\n" +
                        "Fallback review workflow for operations.",
                segments =
                    listOf(
                        RegisterDocumentSegment(
                            segmentType = "paragraph",
                            ordinal = 1,
                            text = "Review checklist for policy updates.",
                            tokenEstimate = 9,
                            locator = "/document[1]/section[1]/p[1]",
                        ),
                        RegisterDocumentSegment(
                            segmentType = "paragraph",
                            ordinal = 2,
                            text = "Fallback review workflow for operations.",
                            tokenEstimate = 10,
                            locator = "/document[1]/section[1]/p[2]",
                        ),
                    ),
            ),
        )

        val result = retrievalApi.search("review")
        val firstHit = result.hits.first()

        assertThat(result.hits).hasSize(2)
        assertThat(firstHit.title).isEqualTo("Review Handbook")
        assertThat(firstHit.revisionNumber).isEqualTo(1)
        assertThat(firstHit.segmentId).startsWith("SEG-")
        assertThat(firstHit.locator).contains("/document[1]/section[1]/p[")
        assertThat(firstHit.snippet.lowercase()).contains("review")
    }

    @Test
    fun blankQueryReturnsNoHits() {
        assertThat(retrievalApi.search(" ").hits).isEmpty()
    }
}
