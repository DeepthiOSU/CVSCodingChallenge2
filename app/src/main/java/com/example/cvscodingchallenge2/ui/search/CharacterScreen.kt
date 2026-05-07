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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.example.cvscodingchallenge2.R
import com.example.cvscodingchallenge2.data.remote.dto.CharacterDto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterScreen(
    viewModel: CharacterViewModel,
    onItemClick: (CharacterDto) -> Unit
) {
    val query by viewModel.filters.collectAsState()
    val state by viewModel.uiState.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Rick & Morty Search")
                }
            )
        }

    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(dimensionResource(R.dimen.standard_padding))
        ) {

            OutlinedTextField(
                value = query.query,
                onValueChange = {
                    viewModel.onQueryChange(it)
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Search characters...") },
                singleLine = true
            )

            Spacer(Modifier.height(dimensionResource(R.dimen.standard_padding)))

            // FilterSection
            FilterSection(
                selectedStatus = query.status,
                selectedSpecies = query.species,
                selectedType = query.type,
                onStatusSelected = viewModel::onStatusSelected,
                onSpeciesSelected = viewModel::onSpeciesSelected,
                onTypeSelected = viewModel::onTypeSelected,
                onClearFilters = viewModel::clearFilters
            )

            Spacer(Modifier.height(dimensionResource(R.dimen.standard_padding)))

            when (state) {

                UiState.Idle -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Start typing to search")
                    }
                }

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
                        columns = GridCells.Adaptive(dimensionResource(R.dimen.gridcell_padding)),
                        content = {
                            items(data, key = { it.id }) { character ->
                                CharacterItem(character, onItemClick)
                            }
                        }
                    )
                }

                UiState.Empty -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("There is nothing here")
                    }
                }

                is UiState.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Something went wrong")
                    }
                }
            }
        }
    }
}