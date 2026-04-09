package org.cherrypick.picker.documents.api

import org.cherrypick.picker.documents.api.dto.DocumentView

interface DocumentQueryApi {
    fun getCanonicalDocument(): DocumentView
}
