package com.zlove.movie.kotlin.details

import com.zlove.movie.kotlin.model.Review
import com.zlove.movie.kotlin.model.Video
import com.zlove.movie.kotlin.network.TmdbWebService
import io.reactivex.Observable

class MovieDetailsInteractorImpl(private val tmdbWebService: TmdbWebService): MovieDetailsInteractor {

    override fun getTrailers(id: String): Observable<List<Video>> {
        return tmdbWebService.trailers(id).map { videoWrapper -> videoWrapper.videos }
    }

    override fun getReviews(id: String): Observable<List<Review>> {
        return tmdbWebService.reviews(id).map { reviewsWrapper -> reviewsWrapper.reviews }
    }
}