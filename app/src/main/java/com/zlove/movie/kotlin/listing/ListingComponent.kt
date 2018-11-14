package com.zlove.movie.kotlin.listing

import com.zlove.movie.kotlin.listing.sorting.SortingDialogFragment
import com.zlove.movie.kotlin.listing.sorting.SortingModule
import dagger.Subcomponent

@ListingScope
@Subcomponent(modules = [ListingModule::class, SortingModule::class])
interface ListingComponent {
    fun inject(fragment: MoviesListingFragment)
    fun inject(fragment: SortingDialogFragment)
}