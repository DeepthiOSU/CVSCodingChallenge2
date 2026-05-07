package com.example.cvscodingchallenge2.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cvscodingchallenge2.data.remote.dto.CharacterDto
import com.example.cvscodingchallenge2.data.remote.dto.SearchFilters
import com.example.cvscodingchallenge2.data.repository.CharacterRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import retrofit2.HttpException

class CharacterViewModel(
    private val repository: CharacterRepository
) : ViewModel() {

    private val _filters = MutableStateFlow(SearchFilters())
    val filters: StateFlow<SearchFilters> = _filters

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<UiState> =
        filters
            .debounce(400)
            .distinctUntilChanged()
            .flatMapLatest { search ->
                if (search.query.isBlank()) {
                    flowOf<UiState>(UiState.Idle)
                } else {
                    flow {
                        emit(UiState.Loading)
                        try {
                            val result = repository.search(search)
                            if (result.isEmpty()) {
                                emit(UiState.Empty)
                            } else {
                                emit(UiState.Success(result))
                            }
                        } catch (e: HttpException) {
                            if (e.code() == 404) {
                                emit(UiState.Empty)
                            } else {
                                emit(UiState.Error)
                            }
                        } catch (e: Exception) {
                            emit(UiState.Error)
                        }
                    }
                }
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                UiState.Idle
            )

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

    fun onSpeciesSelected(species: String?) {
        _filters.value = filters.value.copy(species = species)
    }

    fun onTypeSelected(type: String?) {
        _filters.value = filters.value.copy(type = type)
    }
}

sealed class UiState {
    object Idle : UiState()
    object Empty : UiState()
    object Loading : UiState()
    data class Success(val data: List<CharacterDto>) : UiState()
    object Error : UiState()
}