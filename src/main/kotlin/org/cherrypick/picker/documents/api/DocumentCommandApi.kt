package org.cherrypick.picker.documents.api

import org.cherrypick.picker.documents.api.dto.DocumentView
import org.cherrypick.picker.documents.api.dto.RegisterDocumentCommand

interface DocumentCommandApi {
    fun register(command: RegisterDocumentCommand): DocumentView
}
