package org.cherrypick.picker.review.api

import org.cherrypick.picker.review.api.dto.ReviewTaskView

interface ReviewApi {
    fun openQueue(): ReviewTaskView
}
