package com.example.cvscodingchallenge2

class CharacterRepository(
    private val api: RickMortyApi
) {
    suspend fun search(name: String): ArrayList<CharacterDto> {
        return api.searchCharacters(name).results
    }
}