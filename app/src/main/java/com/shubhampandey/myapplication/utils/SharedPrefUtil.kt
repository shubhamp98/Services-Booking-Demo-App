package com.shubhampandey.myapplication.utils

import android.content.Context
import com.shubhampandey.myapplication.R

object SharedPrefUtil {

    /**
     * Retrieve data from shared pref
     */
    fun getMobileNumberFromPref(context: Context): String? {
        val sharedPreferences =
            context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        return sharedPreferences.getString(
            MOBILE_NUMBER,
            null
        )
    }

    fun saveMobileNumberToPref(context: Context, mobileNumber: String) {
        val sharedPreferences =
            context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE) ?: return
        with (sharedPreferences.edit()) {
            putString(MOBILE_NUMBER, mobileNumber)
            apply()
        }
    }

    private const val MOBILE_NUMBER = "mobile_number"
}