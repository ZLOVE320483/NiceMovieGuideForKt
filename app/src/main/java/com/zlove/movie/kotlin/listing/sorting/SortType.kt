package com.zlove.movie.kotlin.listing.sorting

enum class SortType(var type: Int) {
    MOST_POPULAR(0),
    HIGHEST_RATED(1),
    FAVORITES(2),
    NEWEST(3);
}