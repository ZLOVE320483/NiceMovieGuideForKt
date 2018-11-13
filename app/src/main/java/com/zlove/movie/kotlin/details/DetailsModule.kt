package com.zlove.movie.kotlin.details

import com.zlove.movie.kotlin.favorites.FavoritesInteractor
import com.zlove.movie.kotlin.network.TmdbWebService
import dagger.Module
import dagger.Provides

@Module
class DetailsModule {

    @Provides
    @DetailScope
    fun provideInteractor(tmdbWebService: TmdbWebService): MovieDetailsInteractor {
        return MovieDetailsInteractorImpl(tmdbWebService)
    }

    @Provides
    @DetailScope
    fun providePresenter(movieDetailsInteractor: MovieDetailsInteractor, favoritesInteractor: FavoritesInteractor): MovieDetailsPresenter {
        return MovieDetailsPresenterImpl(movieDetailsInteractor, favoritesInteractor)
    }
}