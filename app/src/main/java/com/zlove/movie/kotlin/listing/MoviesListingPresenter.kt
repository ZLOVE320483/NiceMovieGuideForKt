package com.zlove.movie.kotlin.listing

interface MoviesListingPresenter {
    fun firstPage()
    fun nextPage()
    fun setView(view: MoviesListingView)
    fun searchMovie(searchText: String)
    fun searchMovieBackPressed()
    fun destroy()
}