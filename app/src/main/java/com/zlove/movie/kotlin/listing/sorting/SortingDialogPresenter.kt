package com.zlove.movie.kotlin.listing.sorting

interface SortingDialogPresenter {
    fun setLastSavedOption()
    fun onPopularMoviesSelected()
    fun onHighestRatedMoviesSelected()
    fun onFavoritesSelected()
    fun onNewestMoviesSelected()
    fun setView(view: SortingDialogView)
    fun destroy()
}