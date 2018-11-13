package com.zlove.movie.kotlin.details

import com.zlove.movie.kotlin.model.Movie

interface MovieDetailsPresenter {
    fun showDetails(movie: Movie)
    fun showTrailers(movie: Movie)
    fun showReviews(movie: Movie)
    fun showFavoriteButton(movie: Movie)
    fun onFavoriteClick(movie: Movie)
    fun setView(view: MovieDetailsView)
    fun destroy()
}