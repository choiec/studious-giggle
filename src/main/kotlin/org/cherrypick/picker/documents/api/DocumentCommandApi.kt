package org.cherrypick.picker.documents.api

import org.cherrypick.picker.documents.api.dto.DocumentView

interface DocumentCommandApi {
    fun register(title: String): DocumentView
}
