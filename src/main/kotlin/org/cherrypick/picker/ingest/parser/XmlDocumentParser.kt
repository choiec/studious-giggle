package org.cherrypick.picker.ingest.parser

import org.cherrypick.picker.ingest.domain.ParsedSource
import org.cherrypick.picker.ingest.domain.RawSource
import org.cherrypick.picker.shared.errors.ErrorCode
import org.cherrypick.picker.shared.errors.ValidationException
import org.springframework.stereotype.Component

@Component
internal class XmlDocumentParser : SourceParser {
    override fun supports(format: String): Boolean = format.lowercase() == "xml"

    override fun parse(source: RawSource): ParsedSource {
        val content =
            source.payload
                .replace(Regex("<[^>]+>"), " ")
                .replace(Regex("\\s+"), " ")
                .trim()

        if (content.isBlank()) {
            throw ValidationException(
                ErrorCode.INVALID_INGEST_PAYLOAD,
                "XML payload must contain readable text content",
            )
        }

        return ParsedSource(
            sourceId = source.sourceId,
            format = source.normalizedFormat(),
            title = source.title,
            content = content,
        )
    }
}
