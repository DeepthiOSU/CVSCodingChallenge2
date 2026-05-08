package com.example.cvscodingchallenge2.data.repository

import com.example.cvscodingchallenge2.data.remote.RickMortyApi
import com.example.cvscodingchallenge2.data.remote.dto.CharacterDto
import com.example.cvscodingchallenge2.ui.search.model.SearchFilters

class CharacterRepository(
    private val api: RickMortyApi
) {
    suspend fun search(filters: SearchFilters): List<CharacterDto> {
        return api.searchCharacters(
            name = filters.query,
            status = filters.status,
            species = filters.species,
            type = filters.type,
            page = 1
        ).results
    }
}