package com.amr.moviemania.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.amr.moviemania.di.Injection
import com.amr.moviemania.model.Movies
import com.amr.moviemania.ui.ViewModelFactory
import com.amr.moviemania.ui.common.UiState
import com.amr.moviemania.ui.components.MoviesItem
import com.amr.moviemania.ui.components.NotFound
import com.amr.moviemania.ui.components.SearchBar

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Long) -> Unit
) {
    val query by viewModel.query
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when(uiState){
            is UiState.Loading -> {
                viewModel.getAllMovies()
            }
            is UiState.Success -> {
                HomeContent(
                    movies = uiState.data,
                    modifier = modifier,
                    navigateToDetail = navigateToDetail,
                    query = query,
                    onQueryChange = viewModel::search
                )
            }
            is UiState.Error -> {
                NotFound()
            }
        }
    }

    SearchBar(
        query = query,
        onQueryChange = viewModel::search,
        modifier = Modifier.background(MaterialTheme.colorScheme.primary)
    )
}

@Composable
fun HomeContent(
    movies: List<Movies>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Long) -> Unit,
    query: String,
    onQueryChange: (String) -> Unit
) {
    Column(modifier) {
        SearchBar(
            query = query,
            onQueryChange = onQueryChange,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth()
        )
        LazyVerticalGrid(
            columns = GridCells.Adaptive(160.dp),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = modifier.testTag("MoviesList")
        ) {
            items(movies) { data ->
                MoviesItem(
                    image = data.image,
                    title = data.title,
                    director = data.director,
                    modifier = Modifier.clickable {
                        navigateToDetail(data.id)
                    }
                )
            }
        }
    }
}
