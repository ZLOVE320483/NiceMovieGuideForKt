package com.zlove.movie.kotlin.model

import com.squareup.moshi.Json

data class MoviesWrapper(@Json(name = "results") val movies: List<Movie>)