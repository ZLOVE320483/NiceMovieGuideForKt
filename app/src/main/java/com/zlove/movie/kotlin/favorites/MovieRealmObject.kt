package com.zlove.movie.kotlin.favorites

import android.support.annotation.Keep
import com.zlove.movie.kotlin.model.Movie
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

@Keep
open class MovieRealmObject : RealmObject {
    @PrimaryKey
    var id: String? = null

    var overview: String? = null
    var releaseDate: String? = null
    var posterPath: String? = null
    var backdropPath: String? = null
    var title: String? = null
    var voteAverage: Double = 0.toDouble()

    constructor(movie: Movie) {
        id = movie.id
        overview = movie.overview
        releaseDate = movie.releaseDate
        posterPath = movie.posterPath
        backdropPath = movie.backdropPath
        title = movie.title
        voteAverage = movie.voteAverage
    }

    constructor()

    companion object {
        const val COLUMN_NAME_ID = "id"
    }
}