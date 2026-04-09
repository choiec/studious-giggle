package org.cherrypick.picker.ingest.parser

import org.cherrypick.picker.ingest.domain.ParsedSegment
import org.cherrypick.picker.ingest.domain.ParsedSource
import org.cherrypick.picker.ingest.domain.RawSource
import org.cherrypick.picker.shared.errors.ErrorCode
import org.cherrypick.picker.shared.errors.ValidationException
import org.springframework.stereotype.Component
import org.w3c.dom.Element
import org.xml.sax.InputSource
import java.io.StringReader
import javax.xml.XMLConstants
import javax.xml.parsers.DocumentBuilderFactory

@Component
internal class XmlDocumentParser : SourceParser {
    override fun supports(format: String): Boolean = format.lowercase() == "xml"

    override fun parse(source: RawSource): ParsedSource {
        val segments = extractSegments(source.payload)
        val canonicalBody = segments.joinToString(separator = "\n\n") { it.text }

        if (canonicalBody.isBlank()) {
            throw ValidationException(
                ErrorCode.INVALID_INGEST_PAYLOAD,
                "XML payload must contain readable text content",
            )
        }

        return ParsedSource(
            sourceId = source.sourceId,
            format = source.normalizedFormat(),
            title = source.title,
            canonicalBody = canonicalBody,
            segments = segments,
        )
    }

    private fun extractSegments(payload: String): List<ParsedSegment> =
        try {
            val document = DOCUMENT_BUILDER_FACTORY.newDocumentBuilder().parse(InputSource(StringReader(payload)))
            val root = document.documentElement ?: return emptyList()
            val candidates = mutableListOf<SegmentCandidate>()
            collectSegments(root, "/${root.tagName.lowercase()}[1]", candidates)
            val normalizedCandidates = if (candidates.isNotEmpty()) candidates else fallbackSegment(root)
            normalizedCandidates.mapIndexed { index, candidate ->
                ParsedSegment(
                    segmentType = candidate.segmentType,
                    ordinal = index + 1,
                    text = candidate.text,
                    tokenEstimate = estimateTokens(candidate.text),
                    locator = candidate.locator,
                )
            }
        } catch (_: Exception) {
            throw ValidationException(
                ErrorCode.INVALID_INGEST_PAYLOAD,
                "XML payload must be a valid XML document",
            )
        }

    private fun collectSegments(
        element: Element,
        path: String,
        segments: MutableList<SegmentCandidate>,
    ) {
        val normalizedText = normalizeText(element.textContent)
        if (normalizedText.isBlank()) {
            return
        }

        if (isSegmentCandidate(element) && !hasSegmentCandidateDescendant(element)) {
            segments += SegmentCandidate(element.tagName.lowercase(), normalizedText, path)
            return
        }

        val siblingCounts = linkedMapOf<String, Int>()
        childElements(element).forEach { child ->
            val tagName = child.tagName.lowercase()
            val siblingIndex = (siblingCounts[tagName] ?: 0) + 1
            siblingCounts[tagName] = siblingIndex
            collectSegments(child, "$path/$tagName[$siblingIndex]", segments)
        }
    }

    private fun hasSegmentCandidateDescendant(element: Element): Boolean =
        childElements(element).any { child ->
            isSegmentCandidate(child) || hasSegmentCandidateDescendant(child)
        }

    private fun fallbackSegment(root: Element): List<SegmentCandidate> {
        val normalizedText = normalizeText(root.textContent)
        return if (normalizedText.isBlank()) {
            emptyList()
        } else {
            listOf(SegmentCandidate(root.tagName.lowercase(), normalizedText, "/${root.tagName.lowercase()}[1]"))
        }
    }

    private fun childElements(element: Element): List<Element> {
        val children = mutableListOf<Element>()
        val nodeList = element.childNodes
        for (index in 0 until nodeList.length) {
            val child = nodeList.item(index)
            if (child is Element) {
                children += child
            }
        }
        return children
    }

    private fun isSegmentCandidate(element: Element): Boolean = element.tagName.lowercase() in SEGMENT_TAGS

    private fun normalizeText(content: String?): String = content.orEmpty().replace(WHITESPACE_REGEX, " ").trim()

    private fun estimateTokens(text: String): Int = maxOf(1, (text.length + 3) / 4)

    private data class SegmentCandidate(
        val segmentType: String,
        val text: String,
        val locator: String,
    )

    companion object {
        private val WHITESPACE_REGEX = Regex("\\s+")
        private val SEGMENT_TAGS = setOf("p", "paragraph", "item", "li", "question", "answer", "passage", "body", "section", "title")
        private val DOCUMENT_BUILDER_FACTORY =
            DocumentBuilderFactory.newInstance().apply {
                setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true)
                setFeature("http://apache.org/xml/features/disallow-doctype-decl", true)
                setFeature("http://xml.org/sax/features/external-general-entities", false)
                setFeature("http://xml.org/sax/features/external-parameter-entities", false)
                isXIncludeAware = false
                isNamespaceAware = false
                setExpandEntityReferences(false)
            }
    }
}
