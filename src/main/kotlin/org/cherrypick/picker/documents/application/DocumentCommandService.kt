package org.cherrypick.picker.documents.application

import org.cherrypick.picker.documents.api.DocumentCommandApi
import org.cherrypick.picker.documents.api.dto.DocumentView
import org.cherrypick.picker.documents.domain.Document
import org.springframework.stereotype.Service

@Service
internal class DocumentCommandService : DocumentCommandApi {
    override fun register(title: String): DocumentView = Document.create(title).toView()
}
