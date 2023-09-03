package com.example.nftproject.network.util

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

class PreferenceUtil(context: Context) {
	private val prefs: SharedPreferences = context.getSharedPreferences(SPFileName, MODE_PRIVATE)
	fun getString(key: String, defValue: String?): String? = prefs.getString(key, defValue)
	fun setString(key: String, value: String) = prefs.edit().putString(key, value).apply()
	fun removeKey(key: String) =  prefs.edit().remove(key).commit()
}