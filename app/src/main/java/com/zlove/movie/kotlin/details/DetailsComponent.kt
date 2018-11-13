package com.zlove.movie.kotlin.details

import dagger.Subcomponent

@DetailScope
@Subcomponent(modules = [DetailsModule::class])
interface DetailsComponent {
    fun inject(target: MovieDetailsFragment)
}