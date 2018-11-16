package com.zlove.movie.kotlin.model

import android.support.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class MoviesWrapper(@Json(name = "results") val movies: List<Movie>)