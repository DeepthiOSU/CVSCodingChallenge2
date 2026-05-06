package com.example.cvscodingchallenge2.data.repository

import com.example.cvscodingchallenge2.data.remote.RickMortyApi
import com.example.cvscodingchallenge2.data.remote.dto.CharacterDto

class CharacterRepository(
    private val api: RickMortyApi
) {
    suspend fun search(name: String): ArrayList<CharacterDto> {
        return api.searchCharacters(name).results
    }
}