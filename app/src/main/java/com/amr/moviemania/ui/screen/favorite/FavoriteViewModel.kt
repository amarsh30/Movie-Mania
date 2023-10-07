package com.amr.moviemania.ui.screen.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amr.moviemania.data.MoviesRepository
import com.amr.moviemania.model.Movies
import com.amr.moviemania.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FavoriteViewModel(private val repository: MoviesRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<Movies>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Movies>>> get() = _uiState

    fun getFavoriteMovies() {
        viewModelScope.launch {
            repository.getFavoriteMovies()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { movies ->
                    _uiState.value = UiState.Success(movies)
                }
        }
    }
}