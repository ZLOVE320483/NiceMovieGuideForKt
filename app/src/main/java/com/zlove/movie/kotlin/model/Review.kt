package com.zlove.movie.kotlin.model

import android.os.Parcelable
import android.support.annotation.Keep
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class Review(val id: String, val author: String,
                  val content: String, val url: String): Parcelable