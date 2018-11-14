package com.zlove.movie.kotlin.listing.sorting

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class SortingOptionStore {

    private var pref: SharedPreferences? = null

    companion object {
        private const val SELECTED_OPTION = "selectedOption"
        private const val PREF_NAME = "SortingOptionStore"
    }

    @Inject
    constructor(context: Context) {
        pref = context.applicationContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun setSelectedOption(sortType: SortType) {
        val editor = pref!!.edit()
        editor.putInt(SELECTED_OPTION, sortType.type)
        editor.apply()
    }

    fun getSelectedOption(): Int {
        return pref!!.getInt(SELECTED_OPTION, 0)
    }
}