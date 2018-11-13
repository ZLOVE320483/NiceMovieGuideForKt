package com.zlove.movie.kotlin.favorites

import com.zlove.movie.kotlin.model.Movie

class FavoritesInteractorImpl(private val favoritesStore: FavoritesStore): FavoritesInteractor {

    override fun setFavorite(movie: Movie) {
        favoritesStore.setFavorite(movie)
    }

    override fun isFavorite(id: String): Boolean {
        return favoritesStore.isFavorite(id)
    }

    override fun getFavorites(): List<Movie> {
        return favoritesStore.getFavorites()
    }

    override fun unFavorite(id: String) {
        favoritesStore.unFavorite(id)
    }
}