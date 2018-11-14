package com.zlove.movie.kotlin.app

import com.zlove.movie.kotlin.details.DetailsComponent
import com.zlove.movie.kotlin.details.DetailsModule
import com.zlove.movie.kotlin.favorites.FavoritesModule
import com.zlove.movie.kotlin.listing.ListingComponent
import com.zlove.movie.kotlin.listing.ListingModule
import com.zlove.movie.kotlin.network.NetworkModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class, FavoritesModule::class])
interface AppComponent {
    fun plus(detailsModule: DetailsModule): DetailsComponent
    fun plus(listingModule: ListingModule): ListingComponent
}