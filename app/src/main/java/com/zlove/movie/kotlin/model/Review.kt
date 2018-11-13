package com.zlove.movie.kotlin.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Review(val id: String, val author: String,
                  val content: String, val url: String): Parcelable