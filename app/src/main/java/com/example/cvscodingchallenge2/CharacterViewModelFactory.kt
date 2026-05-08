package com.example.cvscodingchallenge2

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cvscodingchallenge2.data.repository.CharacterRepository
import com.example.cvscodingchallenge2.ui.search.CharacterViewModel

class CharacterViewModelFactory(
    private val repository: CharacterRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(CharacterViewModel::class.java)) {
            return CharacterViewModel(repository) as T
        }

        throw IllegalArgumentException(
            "Unknown ViewModel class: $modelClass"
        )
    }
}