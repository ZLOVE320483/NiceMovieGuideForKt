package com.zlove.movie.kotlin.model

import com.squareup.moshi.Json

data class VideoWrapper(@Json(name = "results") val videos: List<Video>)