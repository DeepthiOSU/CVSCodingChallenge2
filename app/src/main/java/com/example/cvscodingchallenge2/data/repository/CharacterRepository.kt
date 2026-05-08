package com.example.cvscodingchallenge2.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.cvscodingchallenge2.data.remote.RickMortyApi
import com.example.cvscodingchallenge2.data.remote.dto.CharacterDto
import com.example.cvscodingchallenge2.data.remote.dto.SearchFilters
import com.example.cvscodingchallenge2.ui.paging.CharacterPagingSource
import kotlinx.coroutines.flow.Flow

class CharacterRepository(
    private val api: RickMortyApi
) {
    fun search(
        filters: SearchFilters
    ): Flow<PagingData<CharacterDto>> {

        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),

            pagingSourceFactory = {
                CharacterPagingSource(
                    api = api,
                    filters = filters
                )
            }

        ).flow
    }
}