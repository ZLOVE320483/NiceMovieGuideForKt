package com.zlove.movie.kotlin.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.zlove.movie.kotlin.api.Api
import com.zlove.movie.kotlin.constant.Constants
import com.zlove.movie.kotlin.extension.equalsIgnoreCase
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Video(val id: String,
                 val name: String,
                 val site: String,
                 @Json(name = "key")
                 val videoId: String,
                 val size: Int,
                 val type: String): Parcelable {

    companion object {
        private const val SITE_YOUTUBE: String = "SITE_YOUTUBE"

        fun getUrl(video: Video): String {
            if (SITE_YOUTUBE.equalsIgnoreCase(video.site))
                return Api.YOUTUBE_VIDEO_URL.format(video.videoId)
            else
                return Constants.EMPTY
        }

        fun getThumbnailUrl(video: Video): String {
            if (SITE_YOUTUBE.equalsIgnoreCase(video.site))
                return Api.YOUTUBE_THUMBNAIL_URL.format(video.videoId)
            else
                return Constants.EMPTY
        }
    }
}