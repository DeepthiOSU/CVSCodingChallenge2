package com.example.cvscodingchallenge2.ui.search

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun FilterSection(
    selectedStatus: String?,
    selectedSpecies: String?,
    selectedType: String?,
    onStatusSelected: (String?) -> Unit,
    onSpeciesSelected: (String?) -> Unit,
    onTypeSelected: (String?) -> Unit,
    onClearFilters: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        FilterDropdown(
            label = "Status",
            options = listOf("alive", "dead", "unknown"),
            selectedOption = selectedStatus,
            onOptionSelected = onStatusSelected
        )

        FilterDropdown(
            label = "Species",
            options = listOf(
                "Human",
                "Alien",
                "Robot",
                "Animal"
            ),
            selectedOption = selectedSpecies,
            onOptionSelected = onSpeciesSelected
        )

        FilterDropdown(
            label = "Type",
            options = listOf(
                "Parasite",
                "Clone",
                "Mythological Creature"
            ),
            selectedOption = selectedType,
            onOptionSelected = onTypeSelected
        )

        TextButton(
            onClick = onClearFilters
        ) {
            Text("Clear Filters")
        }
    }
}

