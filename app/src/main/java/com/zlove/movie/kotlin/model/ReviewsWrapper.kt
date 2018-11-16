package com.zlove.movie.kotlin.model

import android.support.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class ReviewsWrapper(@Json(name = "results") val reviews: List<Review>)