package org.cherrypick.picker.review.api.dto

data class ReviewTaskView(
    val taskId: String,
    val title: String,
    val searchHits: Int,
)
