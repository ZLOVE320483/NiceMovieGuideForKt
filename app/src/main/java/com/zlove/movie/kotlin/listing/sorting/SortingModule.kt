package com.zlove.movie.kotlin.listing.sorting

import com.zlove.movie.kotlin.listing.ListingScope
import dagger.Module
import dagger.Provides

@Module
class SortingModule {

    @Provides
    @ListingScope
    internal fun provideSortingDialogInteractor(store: SortingOptionStore): SortingDialogInteractor {
        return SortingDialogInteractorImpl(store)
    }

    @Provides
    @ListingScope
    internal fun providePresenter(interactor: SortingDialogInteractor): SortingDialogPresenter {
        return SortingDialogPresenterImpl(interactor)
    }
}