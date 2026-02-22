package com.example.moviesapimvvm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.moviesapimvvm.Data.MovieViewModel
import com.example.moviesapimvvm.navigation.navigation
import com.example.moviesapimvvm.ui.theme.MoviesApiMVVMTheme
import org.koin.androidx.compose.koinViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MoviesApiMVVMTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val movieViewModel: MovieViewModel = koinViewModel()


                    val state = movieViewModel.state
                    navigation()
                }
            }
        }
    }
}
