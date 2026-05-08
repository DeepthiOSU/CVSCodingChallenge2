package com.example.cvscodingchallenge2.ui.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
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
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.cvscodingchallenge2.R
import com.example.cvscodingchallenge2.data.remote.dto.CharacterDto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterScreen(
    viewModel: CharacterViewModel,
    onItemClick: (CharacterDto) -> Unit
) {
    val query by viewModel.filters.collectAsState()
    val characters = viewModel.characters.collectAsLazyPagingItems()
    val isIdle = query.query.isBlank() &&
            query.status == null &&
            query.species.isBlank() &&
            query.type.isBlank()
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

            Text("Results: ${characters.itemCount}")

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

            when {
                isIdle -> {

                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        Text("Search for characters")
                    }
                }

                characters.loadState.refresh is LoadState.Loading -> {

                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        CircularProgressIndicator()
                    }
                }

                characters.loadState.refresh is LoadState.Error -> {

                    val error =
                        (characters.loadState.refresh as LoadState.Error)
                            .error.localizedMessage ?: "Unknown error"

                    Column(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(text = error)

                        Spacer(modifier = Modifier.height(8.dp))

                        Button(
                            onClick = { characters.retry() }
                        ) {
                            Text("Retry")
                        }
                    }
                }

                characters.loadState.refresh is LoadState.NotLoading -> {

                    if (characters.itemCount == 0) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.TopCenter
                        ) {
                            Text(
                                text = "No results found"
                            )
                        }

                    } else {
                        LazyVerticalGrid(
                            columns = GridCells.Adaptive(160.dp),
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {

                            items(
                                count = characters.itemCount
                            ) { index ->

                                val character = characters[index]

                                character?.let { it ->

                                    CharacterItem(
                                        character = it,
                                        onClick = {
                                            onItemClick(it)
                                        }
                                    )
                                }
                            }

                            if (characters.loadState.append is LoadState.Loading) {

                                item(span = { GridItemSpan(maxLineSpan) }) {

                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp),
                                        contentAlignment = Alignment.TopCenter
                                    ) {
                                        CircularProgressIndicator()
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}