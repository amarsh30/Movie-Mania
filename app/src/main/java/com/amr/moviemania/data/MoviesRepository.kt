package com.amr.moviemania.data

import com.amr.moviemania.R
import com.amr.moviemania.model.FakeMoviesDataSource
import com.amr.moviemania.model.Movies
import com.amr.moviemania.ui.common.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class MoviesRepository {

    private val movies = mutableListOf<Movies>()

    init {
        if (movies.isEmpty()) {
            FakeMoviesDataSource.dummyMovies.forEach {
                movies.add(it)
            }
        }
    }

    fun getAllMovies(): Flow<List<Movies>> {
        return flowOf(movies)
    }

    fun getMoviesById(moviesId: Long): Movies {
        return movies.first() {
            it.id == moviesId
        }
    }

    fun getFavoriteMovies(): Flow<List<Movies>> {
        return getAllMovies()
            .map { movies ->
                movies.filter {
                    it.isFavorite
                }
            }
    }

    fun updateIsFavorite(moviesId: Long, isFavorite: Boolean): Flow<Boolean> {
        val index = movies.indexOfFirst { it.id == moviesId }
        val result = if (index >= 0) {
            val movie = movies[index]
            movies[index] = movie.copy(isFavorite = isFavorite)
            true
        } else {
            false
        }
        return flowOf(result)
    }

    fun searchMovies(query: String): UiState<List<Movies>> {
        val filteredMovies = FakeMoviesDataSource.dummyMovies.filter {
            it.title.contains(query, ignoreCase = true)
        }

        return if (filteredMovies.isNotEmpty()) {
            UiState.Success(filteredMovies)
        } else {
            UiState.Error(R.string.movies_is_not_found.toString())
        }
    }

    companion object {
        @Volatile
        private var instance: MoviesRepository? = null

        fun getInstance(): MoviesRepository =
            instance?: synchronized(this) {
                MoviesRepository().apply {
                    instance = this
                }
            }
    }
}