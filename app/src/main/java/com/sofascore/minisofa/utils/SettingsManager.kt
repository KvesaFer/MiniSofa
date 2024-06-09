package com.sofascore.minisofa.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
object SettingsManager {
    private const val PREFS_NAME = "app_settings"
    private const val KEY_THEME = "theme"
    private const val KEY_DATE_FORMAT = "date_format"
    private const val THEME_LIGHT = "light"
    private const val THEME_DARK = "dark"
    private const val DATE_FORMAT_EU = "eu"
    private const val DATE_FORMAT_US = "us"

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    @SuppressLint("ApplySharedPref")
    fun setTheme(context: Context, isDark: Boolean) {
        val editor = getPreferences(context).edit()
        editor.putString(KEY_THEME, if (isDark) THEME_DARK else THEME_LIGHT)
        editor.commit()
    }

    fun isDarkTheme(context: Context): Boolean {
        val prefs = getPreferences(context)
        return prefs.getString(KEY_THEME, THEME_LIGHT) == THEME_DARK
    }

    @SuppressLint("ApplySharedPref")
    fun setDateFormat(context: Context, isEU: Boolean) {
        val editor = getPreferences(context).edit()
        editor.putString(KEY_DATE_FORMAT, if (isEU) DATE_FORMAT_EU else DATE_FORMAT_US)
        editor.commit()
    }

    fun isEUDateFormat(context: Context): Boolean {
        val prefs = getPreferences(context)
        return prefs.getString(KEY_DATE_FORMAT, DATE_FORMAT_EU) == DATE_FORMAT_EU
    }
}
