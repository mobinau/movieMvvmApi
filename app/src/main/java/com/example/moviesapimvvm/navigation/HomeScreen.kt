package com.example.moviesapimvvm.navigation

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.moviesapimvvm.Data.MovieViewModel
import com.example.moviesapimvvm.Dataclass.Data
import org.koin.androidx.compose.koinViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage

@Composable
fun HomeScreen(navController: NavHostController) {

    val movieViewModel: MovieViewModel = koinViewModel()

    val movies = movieViewModel.response.collectAsLazyPagingItems()

    Scaffold(
        modifier = Modifier.background(Color.Transparent),
        topBar = { TopBar() },
        containerColor = Color.Transparent
    ) { paddingValues ->

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(Color.Transparent)
        ) {

            items(movies.itemCount) { index ->
                val movie = movies[index]
                if (movie != null) {
                    ItemUi(
                        movie = movie as Data,
                        navController = navController
                    )
                    println("MOVIE ID CLICKED = ${movie.id}")
                }
            }

            // لود اولیه
            item {
                if (movies.loadState.refresh is androidx.paging.LoadState.Loading) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }

            // لود صفحه بعد
            item {
                if (movies.loadState.append is androidx.paging.LoadState.Loading) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }

            // ارور
            item {
                val error =
                    (movies.loadState.refresh as? androidx.paging.LoadState.Error)
                        ?: (movies.loadState.append as? androidx.paging.LoadState.Error)

                if (error != null) {
                    Toast.makeText(
                        LocalContext.current,
                        error.error.message ?: "Error",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ItemUi(movie: Data, navController: NavHostController) {

    Card(
        Modifier
            .wrapContentSize()
            .padding(10.dp)
            .clickable {
                Log.d("test","check id =${movie.id}")
                navController.navigate("DetailScreen/${movie.id}")
            },
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {

            AsyncImage(
                model = movie.poster,
                contentDescription = movie.title,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(10.dp))
            )

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .background(Color.LightGray.copy(.7f))
                    .padding(6.dp)
            ) {
                Text(
                    text = movie.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .basicMarquee(),
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    style = TextStyle(
                        shadow = Shadow(
                            Color(0xFFFC6603),
                            offset = Offset(1f, 1f),
                            3f
                        )
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(Modifier.align(Alignment.End)) {
                    Icon(imageVector = Icons.Rounded.Star, contentDescription = "")
                    Text(
                        text = movie.imdb_rating,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp),
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        maxLines = 2
                    )
                }
            }
        }
    }
}



@Composable
fun MovieImage(
    model: String,
    contentDescription: String,
    modifier: Modifier
) {
    AsyncImage(
        model = model,
        contentDescription = contentDescription,
        modifier = modifier
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    TopAppBar(
        title = { Text(text = "Movie App") },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White.copy(.4f)
        )
    )
}
