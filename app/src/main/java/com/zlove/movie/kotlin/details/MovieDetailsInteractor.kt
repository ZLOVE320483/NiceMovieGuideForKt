package com.zlove.movie.kotlin.details

import com.zlove.movie.kotlin.model.Review
import com.zlove.movie.kotlin.model.Video
import io.reactivex.Observable

interface MovieDetailsInteractor {
    fun getTrailers(id: String): Observable<List<Video>>
    fun getReviews(id: String): Observable<List<Review>>
}