package com.zlove.movie.kotlin.listing.sorting

class SortingDialogInteractorImpl(private val sortingOptionStore: SortingOptionStore): SortingDialogInteractor {

    override fun getSelectedSortingOption(): Int {
        return sortingOptionStore.getSelectedOption()
    }

    override fun setSortingOption(sortType: SortType) {
        sortingOptionStore.setSelectedOption(sortType)
    }
}