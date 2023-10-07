package com.amr.moviemania.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Favorite : Screen("favorite")
    object DetailMovies : Screen("home/{moviesId}") {
        fun createRoute(rewardId: Long) = "home/$rewardId"
    }
}