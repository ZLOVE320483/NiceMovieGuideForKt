package com.zlove.movie.kotlin.listing

import com.zlove.movie.kotlin.favorites.FavoritesInteractor
import com.zlove.movie.kotlin.listing.sorting.SortType
import com.zlove.movie.kotlin.listing.sorting.SortingOptionStore
import com.zlove.movie.kotlin.model.Movie
import com.zlove.movie.kotlin.network.TmdbWebService
import io.reactivex.Observable
import java.text.SimpleDateFormat
import java.util.*

class MoviesListingInteractorImpl(private val favoritesInteractor: FavoritesInteractor,
                                  private val tmdbWebService: TmdbWebService,
                                  private val sortingOptionStore: SortingOptionStore): MoviesListingInteractor {

    private var dateFormat: SimpleDateFormat? = null

    init {
        dateFormat = SimpleDateFormat("yyyy-MM-dd")
    }

    companion object {
        const val NEWEST_MIN_VOTE_COUNT = 50
    }

    override fun isPaginationSupported(): Boolean {
        val selectedOption = sortingOptionStore.getSelectedOption()
        return selectedOption != SortType.FAVORITES.type
    }

    override fun fetchMovies(page: Int): Observable<List<Movie>> {
        val selectedOption = sortingOptionStore.getSelectedOption()
        when(selectedOption) {
            SortType.MOST_POPULAR.type -> return tmdbWebService.popularMovies(page).map { moviesWrapper -> moviesWrapper.movies }
            SortType.HIGHEST_RATED.type -> return tmdbWebService.highestRatedMovies(page).map { moviesWrapper -> moviesWrapper.movies }
            SortType.NEWEST.type -> {
                val cal = Calendar.getInstance()
                val maxReleaseDate = dateFormat!!.format(cal.time)
                return tmdbWebService.newestMovies(maxReleaseDate, NEWEST_MIN_VOTE_COUNT).map { moviesWrapper -> moviesWrapper.movies }
            }
            else -> return Observable.just(favoritesInteractor.getFavorites())
        }
    }

    override fun searchMovie(searchQuery: String): Observable<List<Movie>> {
        return tmdbWebService.searchMovies(searchQuery).map { moviesWrapper -> moviesWrapper.movies }
    }
}