package com.zlove.movie.kotlin.network

import com.zlove.movie.kotlin.BuildConfig
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RequestInterceptor: Interceptor {

    @Inject
    constructor()

    override fun intercept(chain: Interceptor.Chain?): Response {
        val original: Request = chain!!.request()
        val originalHttpUrl: HttpUrl = original.url()
        val url: HttpUrl = originalHttpUrl.newBuilder()
                .addQueryParameter("api_key", BuildConfig.TMDB_API_KEY)
                .build()

        val request: Request = original.newBuilder().url(url).build()
        return chain.proceed(request)
    }
}