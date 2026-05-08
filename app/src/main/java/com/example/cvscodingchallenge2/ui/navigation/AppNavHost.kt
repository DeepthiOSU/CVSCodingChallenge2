package com.example.cvscodingchallenge2.ui.navigation

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cvscodingchallenge2.ui.search.CharacterListScreen
import com.example.cvscodingchallenge2.ui.search.CharacterViewModel
import com.example.cvscodingchallenge2.ui.search.UiState
import com.example.cvscodingchallenge2.ui.search.detail.CharacterDetailScreen
import com.example.cvscodingchallenge2.util.DateUtil

@Composable
fun AppNavHost(viewModel: CharacterViewModel) {

    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = "list"
    ) {

        composable("list") {
            CharacterListScreen(
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
                CharacterDetailScreen(it, { navController.popBackStack() }) {

                    val text = buildString {

                        appendLine("Check out this Rick & Morty character!")
                        appendLine()

                        appendLine("Name: ${character.name}")
                        appendLine("Species: ${character.species}")
                        appendLine("Status: ${character.status}")
                        appendLine("Origin: ${character.origin.name}")

                        character.type?.let { it1 ->
                            if (it1.isNotBlank()) {
                                appendLine("Type: ${character.type}")
                            }
                        }

                        appendLine("Created: ${DateUtil().formatDate(character.created)}")
                        appendLine()

                        appendLine("Image:")
                        appendLine(character.image)
                    }

                    val intent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_TEXT, text)
                    }

                    context.startActivity(
                        Intent.createChooser(intent, "Share Character")
                    )
                }
            }
        }
    }
}