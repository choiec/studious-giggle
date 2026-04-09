package org.cherrypick.picker.knowledge.api

import org.cherrypick.picker.knowledge.api.dto.FactView

interface KnowledgeApi {
    fun facts(): List<FactView>
}
