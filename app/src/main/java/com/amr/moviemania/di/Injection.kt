package com.amr.moviemania.di

import com.amr.moviemania.data.MoviesRepository

object Injection {
    fun provideRepository(): MoviesRepository {
        return MoviesRepository.getInstance()
    }
}