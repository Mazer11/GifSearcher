package ru.internship.gifsearcher

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class GifApp: Application(){
    var isDarkTheme = false

    fun setAppTheme(
        themeValue: Boolean
    ) {
        isDarkTheme = themeValue
    }

    fun switchAppTheme() {
        isDarkTheme = !isDarkTheme
    }
}