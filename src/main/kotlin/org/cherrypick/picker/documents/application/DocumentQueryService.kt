package org.cherrypick.picker.documents.application

import org.cherrypick.picker.documents.api.DocumentQueryApi
import org.cherrypick.picker.documents.api.dto.DocumentView
import org.cherrypick.picker.documents.domain.Document
import org.cherrypick.picker.documents.domain.DocumentRepository
import org.cherrypick.picker.shared.errors.ErrorCode
import org.cherrypick.picker.shared.errors.NotFoundException
import org.cherrypick.picker.shared.errors.ValidationException
import org.cherrypick.picker.shared.ids.DocumentId
import org.springframework.stereotype.Service

@Service
internal class DocumentQueryService(
    private val documentRepository: DocumentRepository,
) : DocumentQueryApi {
    override fun getDocument(documentId: String): DocumentView {
        val normalizedDocumentId = documentId.trim()
        if (normalizedDocumentId.isBlank()) {
            throw ValidationException(
                ErrorCode.INVALID_DOCUMENT_ID,
                "Document id must not be blank",
            )
        }

        return documentRepository.findById(DocumentId(normalizedDocumentId))?.toView()
            ?: throw NotFoundException(
                ErrorCode.DOCUMENT_NOT_FOUND,
                "Document '$normalizedDocumentId' was not found",
            )
    }

    override fun findLatestDocument(): DocumentView? = documentRepository.findLatest()?.toView()

    override fun getCanonicalDocument(): DocumentView = findLatestDocument() ?: Document.bootstrap().toView()
}
