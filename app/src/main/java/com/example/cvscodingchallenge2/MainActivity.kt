package com.example.cvscodingchallenge2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.cvscodingchallenge2.data.remote.RickMortyApi
import com.example.cvscodingchallenge2.data.repository.CharacterRepository
import com.example.cvscodingchallenge2.ui.search.CharacterViewModel
import com.example.cvscodingchallenge2.ui.search.HomeScreen
import com.example.cvscodingchallenge2.ui.theme.CVSCodingChallenge2Theme
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {

    private val viewModel: CharacterViewModel by viewModels {
        CharacterViewModelFactory(
            CharacterRepository(
                Retrofit.Builder()
                    .baseUrl("https://rickandmortyapi.com/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(RickMortyApi::class.java)
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CVSCodingChallenge2Theme {
                HomeScreen(viewModel)
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CVSCodingChallenge2Theme {
        Greeting("Android")
    }
}