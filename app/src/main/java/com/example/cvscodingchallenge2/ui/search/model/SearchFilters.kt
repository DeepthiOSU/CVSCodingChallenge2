package com.example.cvscodingchallenge2.ui.search.model

data class SearchFilters(
    val query: String = "",
    val status: String? = null,
    val species: String = "",
    val type: String = ""
)