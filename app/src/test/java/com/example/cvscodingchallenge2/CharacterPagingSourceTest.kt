package com.example.cvscodingchallenge2

import androidx.paging.PagingSource
import com.example.cvscodingchallenge2.data.remote.RickMortyApi
import com.example.cvscodingchallenge2.data.remote.dto.CharacterDto
import com.example.cvscodingchallenge2.data.remote.dto.CharacterResponseDto
import com.example.cvscodingchallenge2.data.remote.dto.InfoDto
import com.example.cvscodingchallenge2.data.remote.dto.LocationDto
import com.example.cvscodingchallenge2.data.remote.dto.OriginDto
import com.example.cvscodingchallenge2.data.remote.SearchFilters
import com.example.cvscodingchallenge2.ui.paging.CharacterPagingSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class CharacterPagingSourceTest {

    private lateinit var api: RickMortyApi

    @Before
    fun setup() {
        api = mock()
    }

    @Test
    fun `load returns page when api call succeeds`() =
        runTest {

            val filters = SearchFilters(
                query = "rick"
            )

            val pagingSource =
                CharacterPagingSource(
                    api = api,
                    filters = filters
                )

            val characters = listOf(
                CharacterDto(
                    id = 1,
                    name = "Rick Sanchez",
                    status = "Alive",
                    species = "Human",
                    type = "",
                    gender = "Male",
                    origin = OriginDto("Earth", ""),
                    location = LocationDto(
                        "Citadel of Ricks",
                        "https://rickandmortyapi.com/api/location/3"
                    ),
                    image = "https://rickandmortyapi.com/api/character/avatar/810.jpeg",
                    listOf("https://rickandmortyapi.com/api/episode/51"),
                    "https://rickandmortyapi.com/api/character/810",
                    "2021-11-02T13:55:06.815Z",
                )
            )

            whenever(
                api.searchCharacters(
                    name = "rick",
                    status = null,
                    species = "",
                    type = "",
                    page = 1
                )
            ).thenReturn(
                CharacterResponseDto(
                    info = InfoDto(
                        count = 10,
                        pages = 1,
                        next = null,
                        prev = null
                    ),
                    results = characters
                )
            )

            val result = pagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = null,
                    loadSize = 20,
                    placeholdersEnabled = false
                )
            )

            assertEquals(
                PagingSource.LoadResult.Page(
                    data = characters,
                    prevKey = null,
                    nextKey = null
                ),
                result
            )
        }
}