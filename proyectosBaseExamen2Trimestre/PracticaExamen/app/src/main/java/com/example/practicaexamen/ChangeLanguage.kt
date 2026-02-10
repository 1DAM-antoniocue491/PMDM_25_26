package com.example.practicaexamen

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import java.util.Locale

object ChangeLanguage {
    fun setLocale(context: Context, languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)

        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }

    fun reloadActivity(activity: Activity) {
        activity.finish()
        activity.startActivity(activity.intent)
    }

    fun getLocaleLanguage(context: Context): String {
        val locale = context.resources.configuration.locales[0]
        return locale.language
    }
}