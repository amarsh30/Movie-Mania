package com.amr.moviemania.model

data class Movies(
    val id: Long,
    val image: Int,
    val title: String,
    val director: String,
    val description: String,
    val isFavorite: Boolean = false
)