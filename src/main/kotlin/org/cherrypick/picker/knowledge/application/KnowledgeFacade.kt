package org.cherrypick.picker.knowledge.application

import org.cherrypick.picker.documents.api.DocumentQueryApi
import org.cherrypick.picker.knowledge.api.KnowledgeApi
import org.cherrypick.picker.knowledge.api.dto.FactView
import org.cherrypick.picker.review.api.ReviewApi
import org.springframework.stereotype.Service

@Service
internal class KnowledgeFacade(
    private val documentQueryApi: DocumentQueryApi,
    private val reviewApi: ReviewApi,
) : KnowledgeApi {
    override fun facts(): List<FactView> {
        val canonical = documentQueryApi.getCanonicalDocument()
        val reviewTask = reviewApi.openQueue()
        return listOf(
            FactView(
                subject = canonical.id,
                relation = "reviewed-by",
                sourceTaskId = reviewTask.taskId,
            ),
        )
    }
}
