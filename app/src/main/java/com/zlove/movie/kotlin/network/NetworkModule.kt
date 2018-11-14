package com.zlove.movie.kotlin.network

import com.zlove.movie.kotlin.BuildConfig
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module class NetworkModule {

    companion object {
        private const val CONNECT_TIMEOUT_IN_MS: Long = 30 * 1000
    }

    @Provides
    @Singleton
    fun provideRequestInterceptor(interceptor: RequestInterceptor): Interceptor {
        return interceptor
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(requestInterceptor: RequestInterceptor): OkHttpClient {
        val builder: OkHttpClient.Builder = OkHttpClient.Builder()
        builder.connectTimeout(CONNECT_TIMEOUT_IN_MS, TimeUnit.MILLISECONDS)
                .addInterceptor(requestInterceptor)

        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(loggingInterceptor)
        }
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BuildConfig.TMDB_BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build()
    }

    @Provides
    @Singleton
    fun provideTmdbWebService(retrofit: Retrofit): TmdbWebService {
        return retrofit.create(TmdbWebService::class.java)
    }

}