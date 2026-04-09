package org.cherrypick.picker.indexing.api

import org.cherrypick.picker.indexing.api.dto.IndexStatusView

interface IndexingApi {
    fun status(): IndexStatusView
}
