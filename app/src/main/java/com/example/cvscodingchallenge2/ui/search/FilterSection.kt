package com.example.cvscodingchallenge2.ui.search

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.dimensionResource
import com.example.cvscodingchallenge2.R


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
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.small_padding)),
        verticalAlignment = Alignment.CenterVertically
    ) {

        FilterDropdown(
            label = "Status",
            options = listOf("alive", "dead", "unknown"),
            selectedOption = selectedStatus,
            onOptionSelected = onStatusSelected
        )

        OutlinedTextField(
            value = selectedSpecies ?: "",
            onValueChange = {
                onSpeciesSelected(it)
            },
            label = {
                Text("Species")
            }
        )

        OutlinedTextField(
            value = selectedType ?: "",
            onValueChange = {
                onTypeSelected(it)
            },
            label = {
                Text("Type")
            }
        )

        TextButton(
            onClick = onClearFilters
        )
        {
            Text("Clear Filters")
        }
    }
}

