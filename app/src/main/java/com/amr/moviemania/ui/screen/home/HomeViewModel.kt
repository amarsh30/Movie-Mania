package com.amr.moviemania.ui.screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amr.moviemania.data.MoviesRepository
import com.amr.moviemania.model.Movies
import com.amr.moviemania.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: MoviesRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<Movies>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Movies>>> get() = _uiState

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    fun getAllMovies(){
        viewModelScope.launch {
            repository.getAllMovies()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { movies ->
                    _uiState.value = UiState.Success(movies)
                }
        }
    }

    fun search(newQuery: String) {
        _query.value = newQuery
        _uiState.value = repository.searchMovies(_query.value)
    }
}