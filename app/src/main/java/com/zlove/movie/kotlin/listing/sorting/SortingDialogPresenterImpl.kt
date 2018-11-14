package com.zlove.movie.kotlin.listing.sorting

class SortingDialogPresenterImpl(private val sortingDialogInteractor: SortingDialogInteractor): SortingDialogPresenter {

    private var view: SortingDialogView? = null

    override fun setLastSavedOption() {
        if (isViewAttached()) {
            val selectedOption = sortingDialogInteractor.getSelectedSortingOption()
            when(selectedOption) {
                SortType.MOST_POPULAR.type -> view!!.setPopularChecked()
                SortType.HIGHEST_RATED.type -> view!!.setHighestRatedChecked()
                SortType.NEWEST.type -> view!!.setNewestChecked()
                else -> view!!.setFavoritesChecked()
            }
        }
    }

    override fun onPopularMoviesSelected() {
        if (isViewAttached()) {
            sortingDialogInteractor.setSortingOption(SortType.MOST_POPULAR)
            view!!.dismissDialog()
        }
    }

    override fun onHighestRatedMoviesSelected() {
        if (isViewAttached()) {
            sortingDialogInteractor.setSortingOption(SortType.HIGHEST_RATED)
            view!!.dismissDialog()
        }
    }

    override fun onFavoritesSelected() {
        if (isViewAttached()) {
            sortingDialogInteractor.setSortingOption(SortType.FAVORITES)
            view!!.dismissDialog()
        }
    }

    override fun onNewestMoviesSelected() {
        if (isViewAttached()) {
            sortingDialogInteractor.setSortingOption(SortType.NEWEST)
            view!!.dismissDialog()
        }
    }

    override fun setView(view: SortingDialogView) {
        this.view = view
    }

    override fun destroy() {
        this.view = null
    }

    private fun isViewAttached(): Boolean {
        return view != null
    }
}