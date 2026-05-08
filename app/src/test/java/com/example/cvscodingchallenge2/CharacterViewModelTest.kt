package com.example.cvscodingchallenge2

import com.example.cvscodingchallenge2.data.repository.CharacterRepository
import com.example.cvscodingchallenge2.ui.search.CharacterViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock

@OptIn(ExperimentalCoroutinesApi::class)
class CharacterViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var repository: CharacterRepository

    private lateinit var viewModel: CharacterViewModel

    @Before
    fun setup() {

        repository = mock()

        viewModel =
            CharacterViewModel(repository)
    }

    @Test
    fun `query update changes filters state`() =
        runTest {

            viewModel.onQueryChange("rick")

            assertEquals(
                "rick",
                viewModel.filters.value.query
            )
        }

    @Test
    fun `clear filters preserves query`() =
        runTest {

            viewModel.onQueryChange("rick")
            viewModel.onStatusSelected("alive")

            viewModel.clearFilters()

            assertEquals(
                "rick",
                viewModel.filters.value.query
            )

            assertNull(
                viewModel.filters.value.status
            )
        }
}