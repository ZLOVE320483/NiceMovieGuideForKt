package com.zlove.movie.kotlin.model

import android.os.Parcelable
import android.support.annotation.Keep
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class Movie(val id: String,
                 val overview: String,
                 @Json(name = "release_date")
                 val releaseDate: String,
                 @Json(name = "poster_path")
                 val posterPath: String,
                 @Json(name = "backdrop_path")
                 val backdropPath: String,
                 val title: String,
                 @Json(name = "vote_average")
                val voteAverage: Double): Parcelable