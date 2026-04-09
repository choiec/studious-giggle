package org.cherrypick.picker.knowledge.api.dto

data class FactView(
    val subject: String,
    val relation: String,
    val sourceTaskId: String,
)
