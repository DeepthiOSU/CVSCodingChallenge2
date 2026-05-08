package com.example.cvscodingchallenge2.data.remote.dto

data class CharacterResponseDto(
    val info: InfoDto,
    val results: List<CharacterDto>
)