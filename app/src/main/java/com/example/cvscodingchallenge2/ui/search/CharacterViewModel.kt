package com.example.cvscodingchallenge2.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.cvscodingchallenge2.data.remote.SearchFilters
import com.example.cvscodingchallenge2.data.repository.CharacterRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf

class CharacterViewModel(
    private val repository: CharacterRepository
) : ViewModel() {

    private val _filters = MutableStateFlow(SearchFilters())
    val filters: StateFlow<SearchFilters> = _filters

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val characters =
        _filters
            .debounce(400)
            .distinctUntilChanged()
            .flatMapLatest { filterState ->

                // Avoid API call if everything empty
                if (
                    filterState.query.isBlank() &&
                    filterState.status == null &&
                    filterState.species.isBlank() &&
                    filterState.type.isBlank()
                ) {

                    flowOf(PagingData.empty())

                } else {
                    repository.searchCharacters(filterState)
                }
            }
            .cachedIn(viewModelScope)

    fun onQueryChange(newQuery: String) {
        _filters.value = _filters.value.copy(
            query = newQuery
        )
    }

    fun clearFilters() {
        _filters.value = SearchFilters(
            query = filters.value.query
        )
    }

    fun onStatusSelected(status: String?) {
        _filters.value = filters.value.copy(status = status)
    }

    fun onSpeciesSelected(species: String) {
        _filters.value = filters.value.copy(species = species)
    }

    fun onTypeSelected(type: String) {
        _filters.value = filters.value.copy(type = type)
    }
}