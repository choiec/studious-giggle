package org.cherrypick.picker.documents.application

import org.cherrypick.picker.documents.api.DocumentCommandApi
import org.cherrypick.picker.documents.api.dto.DocumentView
import org.cherrypick.picker.documents.api.dto.RegisterDocumentCommand
import org.cherrypick.picker.documents.domain.Document
import org.cherrypick.picker.documents.domain.DocumentRepository
import org.springframework.stereotype.Service

@Service
internal class DocumentCommandService(
    private val documentRepository: DocumentRepository,
) : DocumentCommandApi {
    override fun register(command: RegisterDocumentCommand): DocumentView {
        val documentId = Document.idFromSource(command.sourceId)
        val updatedDocument =
            documentRepository.findById(documentId)?.register(command) ?: Document.create(command)

        return documentRepository.save(updatedDocument).toView()
    }
}
