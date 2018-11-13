package com.zlove.movie.kotlin.model

import com.squareup.moshi.Json

data class ReviewsWrapper(@Json(name = "results") val reviews: List<Review>)