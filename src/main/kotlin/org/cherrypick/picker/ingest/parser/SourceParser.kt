package org.cherrypick.picker.ingest.parser

import org.cherrypick.picker.ingest.domain.ParsedSource
import org.cherrypick.picker.ingest.domain.RawSource

interface SourceParser {
    fun supports(format: String): Boolean

    fun parse(source: RawSource): ParsedSource
}
