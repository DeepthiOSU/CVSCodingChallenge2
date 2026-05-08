package com.example.cvscodingchallenge2.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.cvscodingchallenge2.data.remote.RickMortyApi
import com.example.cvscodingchallenge2.ui.search.model.SearchFilters
import com.example.cvscodingchallenge2.data.remote.dto.CharacterDto
import retrofit2.HttpException
import java.io.IOException

class CharacterPagingSource(
    private val api: RickMortyApi,
    private val filters: SearchFilters
) : PagingSource<Int, CharacterDto>() {

    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, CharacterDto> {

        return try {

            val page = params.key ?: 1

            val response = api.searchCharacters(
                name = filters.query,
                status = filters.status,
                species = filters.species,
                type = filters.type,
                page = page
            )

            LoadResult.Page(
                data = response.results,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.info.next == null) null else page + 1
            )

        } catch (e: HttpException) {

            if (e.code() == 404) {

                LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )

            } else {

                LoadResult.Error(e)
            }

        } catch (e: IOException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(
        state: PagingState<Int, CharacterDto>
    ): Int? {
        return state.anchorPosition?.let { anchorPosition ->

            val anchorPage =
                state.closestPageToPosition(anchorPosition)

            anchorPage?.prevKey?.plus(1)
                ?: anchorPage?.nextKey?.minus(1)
        }
    }
}