package com.zlove.movie.kotlin.favorites

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FavoritesModule {

    @Provides
    @Singleton
    fun provideFavoritesInteractor(store: FavoritesStore): FavoritesInteractor {
        return FavoritesInteractorImpl(store)
    }
}