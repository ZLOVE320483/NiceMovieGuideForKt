package com.zlove.movie.kotlin.details

import com.zlove.movie.kotlin.model.Movie
import com.zlove.movie.kotlin.model.Review
import com.zlove.movie.kotlin.model.Video

interface MovieDetailsView {
    fun showDetails(movie: Movie)
    fun showTrailers(trailers: List<Video>)
    fun showReviews(reviews: List<Review>)
    fun showFavorited()
    fun showUnFavorited()
}