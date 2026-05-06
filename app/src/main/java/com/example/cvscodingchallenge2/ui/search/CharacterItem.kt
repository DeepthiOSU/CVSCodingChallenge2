package com.example.cvscodingchallenge2.ui.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.cvscodingchallenge2.data.remote.dto.CharacterDto

@Composable
fun CharacterItem(
    character: CharacterDto,
    onClick: (CharacterDto) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .clickable { onClick(character) }
    ) {
        AsyncImage(
            model = character.image,
            contentDescription = character.name,
            modifier = Modifier.fillMaxWidth()
        )
        Text(text = character.name)
    }
}