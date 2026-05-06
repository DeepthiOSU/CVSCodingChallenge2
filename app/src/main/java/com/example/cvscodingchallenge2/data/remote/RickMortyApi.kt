package com.example.cvscodingchallenge2.data.remote

import com.example.cvscodingchallenge2.data.remote.dto.CharacterResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RickMortyApi {
    @GET("character")
    suspend fun searchCharacters(
        @Query("name") name: String
    ): CharacterResponse
}