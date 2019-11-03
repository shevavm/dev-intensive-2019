package ru.skillbranch.devintensive

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import ru.skillbranch.devintensive.repositories.PreferencesRepository


class App : Application() {
    companion object {
        private var Instance : App? = null
        fun applicationContext(): Context {
            return Instance!!.applicationContext
        }
    }
    init {
        Instance = this
    }

    override fun onCreate() {
        super.onCreate()
        PreferencesRepository.getAppTheme().also{
            AppCompatDelegate.setDefaultNightMode(it)
        }
    }
}