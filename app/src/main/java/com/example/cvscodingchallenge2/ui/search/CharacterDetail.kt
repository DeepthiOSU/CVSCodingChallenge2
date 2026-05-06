package com.example.cvscodingchallenge2.ui.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil3.compose.AsyncImage
import com.example.cvscodingchallenge2.data.remote.dto.CharacterDto
import com.example.cvscodingchallenge2.util.DateUtil

@Composable
fun CharacterDetail(character: CharacterDto) {
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {

        Text(text = character.name)

        AsyncImage(
            model = character.image,
            contentDescription = null,
            modifier = Modifier.fillMaxWidth()
        )

        Text("Species: ${character.species}")
        Text("Status: ${character.status}")
        Text("Origin: ${character.origin.name}")

        character.type?.takeIf { it.isNotBlank() }?.let {
            Text("Type: $it")
        }

        Text("Created: ${DateUtil().formatDate(character.created)}")
    }
}