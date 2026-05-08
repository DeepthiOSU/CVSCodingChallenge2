package com.example.cvscodingchallenge2.ui.search.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import com.example.cvscodingchallenge2.R
import com.example.cvscodingchallenge2.data.remote.dto.CharacterDto
import com.example.cvscodingchallenge2.data.remote.dto.LocationDto
import com.example.cvscodingchallenge2.data.remote.dto.OriginDto
import com.example.cvscodingchallenge2.ui.theme.CVSCodingChallenge2Theme
import com.example.cvscodingchallenge2.util.DateUtil

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetail(
    character: CharacterDto,
    onBackClick: () -> Unit,
    onShareClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("")
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {

            Text(text = character.name)
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.standard_padding)))
            AsyncImage(
                model = character.image,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.standard_padding)))
            Text("Species: ${character.species}")
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.standard_padding)))
            Text("Status: ${character.status}")
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.standard_padding)))
            Text("Origin: ${character.origin.name}")
            character.type?.takeIf { it.isNotBlank() }?.let {
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.standard_padding)))
                Text("Type: $it")
            }
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.standard_padding)))
            Text("Created: ${DateUtil().formatDate(character.created)}")
            TextButton(onClick = { onShareClick() }) {
                Text("Share")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CharacterDetailPreview() {
    CVSCodingChallenge2Theme {
        CharacterDetail(
            CharacterDto(
                810,
                "Stan Lee Rick",
                "unknown",
                "Human",
                "",
                "Male",
                OriginDto("unknown", ""),
                LocationDto("Citadel of Ricks", "https://rickandmortyapi.com/api/location/3"),
                "https://rickandmortyapi.com/api/character/avatar/810.jpeg",
                listOf("https://rickandmortyapi.com/api/episode/51"),
                "https://rickandmortyapi.com/api/character/810",
                "2021-11-02T13:55:06.815Z",
            ),
            onBackClick = { },
        ) { }
    }
}