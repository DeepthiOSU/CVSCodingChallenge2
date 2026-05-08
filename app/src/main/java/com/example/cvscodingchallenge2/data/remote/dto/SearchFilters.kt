package com.example.cvscodingchallenge2.data.remote.dto

data class SearchFilters(
    val query: String = "",
    val status: String? = null,
    val species: String = "",
    val type: String = ""
)