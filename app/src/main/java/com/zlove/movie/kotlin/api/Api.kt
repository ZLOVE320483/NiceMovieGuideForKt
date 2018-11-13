package com.zlove.movie.kotlin.api

class Api {

    companion object {
        private const val BASE_POSTER_PATH = "http://image.tmdb.org/t/p/w342"
        private const val BASE_BACKDROP_PATH = "http://image.tmdb.org/t/p/w780"

        const val YOUTUBE_VIDEO_URL = "http://www.youtube.com/watch?v=%1\$s"
        const val YOUTUBE_THUMBNAIL_URL = "http://img.youtube.com/vi/%1\$s/0.jpg"

        fun getPosterPath(posterPath: String): String {
            return BASE_POSTER_PATH + posterPath
        }

        fun getBackdropPath(backdropPath: String): String {
            return BASE_BACKDROP_PATH + backdropPath
        }
    }


}