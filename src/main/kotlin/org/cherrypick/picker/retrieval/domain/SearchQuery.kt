package org.cherrypick.picker.retrieval.domain

data class SearchQuery(
    val value: String,
) {
    val normalized: String = value.trim().lowercase()
    val terms: List<String> = normalized.split(Regex("\\s+")).filter(String::isNotBlank)

    fun isBlank(): Boolean = terms.isEmpty()
}
