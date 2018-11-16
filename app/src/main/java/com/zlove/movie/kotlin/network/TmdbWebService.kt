package com.zlove.movie.kotlin.network

import com.zlove.movie.kotlin.model.MoviesWrapper
import com.zlove.movie.kotlin.model.ReviewsWrapper
import com.zlove.movie.kotlin.model.VideoWrapper
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbWebService {

    @GET("3/discover/movie?language=en&sort_by=popularity.desc")
    fun popularMovies(@Query("page") page: Int): Observable<MoviesWrapper>

    @GET("3/discover/movie?vote_count.gte=500&language=en&sort_by=vote_average.desc")
    fun highestRatedMovies(@Query("page") page: Int): Observable<MoviesWrapper>

    @GET("3/discover/movie?language=en&sort_by=elease_date.desc")
    fun newestMovies(@Query("release_date.lte") maxReleaseDate: String?, @Query("vote_count.gte") minVoteCount: Int): Observable<MoviesWrapper>

    @GET("3/movie/{movieId}/videos")
    fun trailers(@Path("movieId") movieId: String): Observable<VideoWrapper>

    @GET("3/movie/{movieId}/reviews")
    fun reviews(@Path("movieId") movieId: String): Observable<ReviewsWrapper>

    @GET("3/search/movie?language=en-US&page=1")
    fun searchMovies(@Query("query") searchQuery: String): Observable<MoviesWrapper>
}