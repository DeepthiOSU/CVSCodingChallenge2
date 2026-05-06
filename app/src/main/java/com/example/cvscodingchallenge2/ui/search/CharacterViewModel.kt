package com.example.cvscodingchallenge2.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cvscodingchallenge2.data.remote.dto.CharacterDto
import com.example.cvscodingchallenge2.data.repository.CharacterRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

class CharacterViewModel(
    private val repository: CharacterRepository
) : ViewModel() {

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<UiState> =
        query
            .debounce(400)
            .filter { it.isNotBlank() }
            .distinctUntilChanged()
            .flatMapLatest { search ->
                flow {
                    emit(UiState.Loading)
                    try {
                        val result = repository.search(search)
                        emit(UiState.Success(result))
                    } catch (e: Exception) {
                        emit(UiState.Error)
                    }
                }
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                UiState.Idle
            )

    fun onQueryChange(newQuery: String) {
        _query.value = newQuery
    }
}

sealed class UiState {
    object Idle : UiState()
    object Loading : UiState()
    data class Success(val data: ArrayList<CharacterDto>) : UiState()
    object Error : UiState()
}