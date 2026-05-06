package com.example.cvscodingchallenge2

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cvscodingchallenge2.ui.search.CharacterDetail
import com.example.cvscodingchallenge2.ui.search.CharacterScreen
import com.example.cvscodingchallenge2.ui.search.CharacterViewModel
import com.example.cvscodingchallenge2.ui.search.UiState

@Composable
fun App(viewModel: CharacterViewModel) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "list"
    ) {

        composable("list") {
            CharacterScreen(
                viewModel = viewModel,
                onItemClick = { character ->
                    navController.navigate("detail/${character.id}")
                }
            )
        }

        composable(
            route = "detail/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->

            val id = backStackEntry.arguments?.getInt("id")

            val state by viewModel.uiState.collectAsState()

            val character = (state as? UiState.Success)
                ?.data
                ?.find { it.id == id }

            character?.let {
                CharacterDetail(it)
            }
        }
    }
}