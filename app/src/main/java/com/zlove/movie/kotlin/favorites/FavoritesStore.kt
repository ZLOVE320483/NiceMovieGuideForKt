package com.zlove.movie.kotlin.favorites

import com.zlove.movie.kotlin.model.Movie
import io.realm.Realm
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoritesStore {

    private var realm: Realm? = null

    @Inject
    constructor(realm: Realm) {
        this.realm = realm
    }

    fun setFavorite(movie: Movie) {
        realm!!.beginTransaction()
        realm!!.copyToRealmOrUpdate(MovieRealmObject(movie))
        realm!!.commitTransaction()
    }

    fun isFavorite(id: String): Boolean {
        val res = realm!!.where(MovieRealmObject::class.java).equalTo(MovieRealmObject.COLUMN_NAME_ID, id).findFirst()
        return res != null
    }

    fun getFavorites(): List<Movie> {
        val res = realm!!.where(MovieRealmObject::class.java).findAll()
        val movies = ArrayList<Movie>()

        for (`object` in res) {
            movies.add(movieRealmObjectToMovie(`object`))
        }
        return movies
    }

    fun unFavorite(id: String) {
        realm!!.beginTransaction()
        val movie = realm!!.where(MovieRealmObject::class.java).equalTo(MovieRealmObject.COLUMN_NAME_ID, id).findFirst()
        movie?.deleteFromRealm()
        realm!!.commitTransaction()
    }

    private fun movieRealmObjectToMovie(movieRealmObject: MovieRealmObject): Movie {
        return Movie(movieRealmObject.id!!,
                movieRealmObject.overview!!,
                movieRealmObject.releaseDate!!,
                movieRealmObject.posterPath!!,
                movieRealmObject.backdropPath!!,
                movieRealmObject.title!!,
                movieRealmObject.voteAverage)
    }
}