package com.zlove.movie.kotlin.listing.sorting

interface SortingDialogView {
    fun setPopularChecked()
    fun setNewestChecked()
    fun setHighestRatedChecked()
    fun setFavoritesChecked()
    fun dismissDialog()
}