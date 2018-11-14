package com.zlove.movie.kotlin.listing

import com.zlove.movie.kotlin.model.Movie
import io.reactivex.Observable

interface MoviesListingInteractor {
    fun isPaginationSupported(): Boolean
    fun fetchMovies(page: Int): Observable<List<Movie>>
    fun searchMovie(searchQuery: String): Observable<List<Movie>>
}