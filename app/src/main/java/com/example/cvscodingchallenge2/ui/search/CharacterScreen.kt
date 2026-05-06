package com.example.cvscodingchallenge2.ui.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cvscodingchallenge2.data.remote.dto.CharacterDto

@Composable
fun CharacterScreen(
    viewModel: CharacterViewModel,
    onItemClick: (CharacterDto) -> Unit
) {
    var query by remember { mutableStateOf("") }
    val state by viewModel.uiState.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {

        OutlinedTextField(
            value = query,
            onValueChange = {
                query = it
                viewModel.onQueryChange(it)
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Search characters...") },
            singleLine = true
        )

        Spacer(Modifier.height(8.dp))

        when (state) {
            is UiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is UiState.Success -> {
                val data = (state as UiState.Success).data

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2)
                ) {
                    items(data, key = { it.id }) { character ->
                        CharacterItem(character, onItemClick)
                    }
                }
            }

            is UiState.Error -> {
                Text("Something went wrong")
            }

            else -> Unit
        }
    }
}