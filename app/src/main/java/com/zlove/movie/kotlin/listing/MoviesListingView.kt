package com.zlove.movie.kotlin.listing

import com.zlove.movie.kotlin.model.Movie

interface MoviesListingView {
    fun showMovies(movies: List<Movie>)
    fun loadingStarted()
    fun loadingFailed(errorMessage: String)
    fun onMovieClicked(movie: Movie)
}