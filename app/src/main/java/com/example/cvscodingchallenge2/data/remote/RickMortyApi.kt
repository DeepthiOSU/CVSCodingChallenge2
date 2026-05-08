package com.example.cvscodingchallenge2.data.remote

import com.example.cvscodingchallenge2.data.remote.dto.CharacterResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface RickMortyApi {
    @GET("character")
    suspend fun searchCharacters(
        @Query("name") name: String,
        @Query("status") status: String?,
        @Query("species") species: String?,
        @Query("type") type: String?,
        @Query("page") page: Int
    ): CharacterResponseDto
}