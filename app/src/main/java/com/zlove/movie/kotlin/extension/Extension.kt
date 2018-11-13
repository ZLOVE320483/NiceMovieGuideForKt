package com.zlove.movie.kotlin.extension

fun String.equalsIgnoreCase(other: String) = (this as java.lang.String).equalsIgnoreCase(other)