package com.zlove.movie.kotlin.listing

import com.zlove.movie.kotlin.favorites.FavoritesInteractor
import com.zlove.movie.kotlin.listing.sorting.SortingOptionStore
import com.zlove.movie.kotlin.network.TmdbWebService
import dagger.Module
import dagger.Provides

@Module
class ListingModule {

    @Provides
    @ListingScope
    fun provideMovieListingInteractor(favoritesInteractor: FavoritesInteractor,
                                      tmdbWebService: TmdbWebService,
                                      sortingOptionStore: SortingOptionStore): MoviesListingInteractor {
        return MoviesListingInteractorImpl(favoritesInteractor, tmdbWebService, sortingOptionStore)
    }

    @Provides
    @ListingScope
    fun provideMovieListingPresenter(interactor: MoviesListingInteractor): MoviesListingPresenter {
        return MoviesListingPresenterImpl(interactor)
    }
}