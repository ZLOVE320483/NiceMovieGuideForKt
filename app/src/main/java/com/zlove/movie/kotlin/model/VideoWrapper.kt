package com.zlove.movie.kotlin.model

import android.support.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class VideoWrapper(@Json(name = "results") val videos: List<Video>)