package com.zlove.movie.kotlin.favorites

import com.zlove.movie.kotlin.model.Movie

interface FavoritesInteractor {

    fun setFavorite(movie: Movie)
    fun isFavorite(id: String): Boolean
    fun getFavorites(): List<Movie>
    fun unFavorite(id: String)
}