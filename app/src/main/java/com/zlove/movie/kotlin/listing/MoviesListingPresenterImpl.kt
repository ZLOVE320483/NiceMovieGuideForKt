package com.zlove.movie.kotlin.listing

import android.support.annotation.NonNull
import com.zlove.movie.kotlin.model.Movie
import com.zlove.movie.kotlin.utils.RxUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MoviesListingPresenterImpl(private val moviesListingInteractor: MoviesListingInteractor): MoviesListingPresenter {

    private lateinit var view: MoviesListingView
    private var fetchSubscription: Disposable? = null
    private var movieSearchSubscription: Disposable? = null
    private var currentPage = 1
    private var loadedMovies: MutableList<Movie> = ArrayList()
    private var showingSearchResult = false

    override fun firstPage() {
        currentPage = 1
        loadedMovies.clear()
        displayMovies()
    }

    override fun nextPage() {
        if (showingSearchResult) {
            return
        }
        if (moviesListingInteractor.isPaginationSupported()) {
            currentPage++
            displayMovies()
        }
    }

    override fun setView(view: MoviesListingView) {
        this.view = view
        if (!showingSearchResult) {
            displayMovies()
        }
    }

    override fun searchMovie(searchText: String) {
        if (searchText.isEmpty()) {
            displayMovies()
        } else {
            displayMovieSearchResult(searchText)
        }
    }

    override fun searchMovieBackPressed() {
        if (showingSearchResult) {
            showingSearchResult = false
            loadedMovies.clear()
            displayMovies()
        }
    }

    override fun destroy() {
        fetchSubscription?.let {
            RxUtils.unSubscribe(it)
        }

        movieSearchSubscription.let {
            RxUtils.unSubscribe(it)
        }
    }

    private fun displayMovies() {
        showLoading()
        fetchSubscription = moviesListingInteractor.fetchMovies(currentPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ movies -> onMovieFetchSuccess(movies) }, { e -> onMovieFetchFailed(e) })
    }

    private fun displayMovieSearchResult(@NonNull searchText: String) {
        showingSearchResult = true
        showLoading()
        movieSearchSubscription = moviesListingInteractor.searchMovie(searchText)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ movies -> onMovieSearchSuccess(movies) }, { e -> onMovieSearchFailed(e) })
    }

    private fun showLoading() {
            view.loadingStarted()
    }

    private fun onMovieFetchSuccess(movies: List<Movie>) {
        if (moviesListingInteractor.isPaginationSupported()) {
            loadedMovies.addAll(movies)
        } else {
            loadedMovies = java.util.ArrayList(movies)
        }
            view.showMovies(loadedMovies)
    }

    private fun onMovieFetchFailed(e: Throwable) {
            view.loadingFailed(e.message!!)
    }

    private fun onMovieSearchSuccess(movies: List<Movie>) {
        loadedMovies.clear()
        loadedMovies = ArrayList(movies)
            view.showMovies(loadedMovies)
    }

    private fun onMovieSearchFailed(e: Throwable) {
            view.loadingFailed(e.message!!)
    }
}