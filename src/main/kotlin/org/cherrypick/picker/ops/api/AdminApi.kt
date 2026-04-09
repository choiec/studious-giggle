package org.cherrypick.picker.ops.api

import org.cherrypick.picker.ops.api.dto.JobStatusView

interface AdminApi {
    fun status(): JobStatusView
}
