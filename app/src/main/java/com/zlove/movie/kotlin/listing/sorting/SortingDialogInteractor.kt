package com.zlove.movie.kotlin.listing.sorting

interface SortingDialogInteractor {
    fun getSelectedSortingOption(): Int
    fun setSortingOption(sortType: SortType)
}