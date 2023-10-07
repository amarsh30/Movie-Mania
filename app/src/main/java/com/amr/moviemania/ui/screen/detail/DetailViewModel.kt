package com.amr.moviemania.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amr.moviemania.data.MoviesRepository
import com.amr.moviemania.model.Movies
import com.amr.moviemania.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: MoviesRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<Movies>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<Movies>> get() = _uiState

    fun getMoviesById(moviesId: Long) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getMoviesById(moviesId))
        }
    }

    fun updateIsFavorite(moviesId: Long, isFav: Boolean){
        viewModelScope.launch {
            repository.updateIsFavorite(moviesId, isFav)
        }
    }
}