package org.cherrypick.picker.retrieval.api

import org.cherrypick.picker.retrieval.api.dto.SearchResultView

interface RetrievalApi {
    fun search(query: String): SearchResultView
}
