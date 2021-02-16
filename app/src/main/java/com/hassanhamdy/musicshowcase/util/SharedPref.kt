package com.hassanhamdy.musicshowcase.util

import android.content.Context
import android.content.SharedPreferences

class SharedPref {
    private val SHARED_PREFERENCE_NAME: String = "MUSIC"
    private val ACCESS_TOKEN_KEY: String = "token"
    private val TOKEN_VALID_TIME_KEY: String = "tokenTime"
    private lateinit var sharedPreference: SharedPreferences

    companion object {
        private var INSTANCE: SharedPref? = null

        val instance: SharedPref
            get() {
                if (INSTANCE == null) {
                    INSTANCE = SharedPref()
                }

                return INSTANCE!!
            }
    }

    fun initSharedPref(context: Context) {
        sharedPreference =
            context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
    }

    fun getToken(): String {
        return sharedPreference.getString(ACCESS_TOKEN_KEY, "") ?: ""
    }

    fun getTokenValidTime(): String {
        return sharedPreference.getString(TOKEN_VALID_TIME_KEY, "") ?: ""
    }

    fun putAccessToken(value: String) {
        val editor = sharedPreference.edit()
        editor.putString(ACCESS_TOKEN_KEY, value)
        editor.apply()
    }

    fun putTokenTime(value: String) {
        val editor = sharedPreference.edit()
        editor.putString(TOKEN_VALID_TIME_KEY, value)
        editor.apply()
    }
}