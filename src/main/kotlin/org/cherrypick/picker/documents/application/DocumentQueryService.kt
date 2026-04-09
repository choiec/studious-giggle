package org.cherrypick.picker.documents.application

import org.cherrypick.picker.documents.api.DocumentQueryApi
import org.cherrypick.picker.documents.api.dto.DocumentView
import org.cherrypick.picker.documents.domain.Document
import org.springframework.stereotype.Service

@Service
internal class DocumentQueryService : DocumentQueryApi {
    override fun getCanonicalDocument(): DocumentView = Document.canonical().toView()
}
