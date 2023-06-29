package com.example.gmail

import android.app.Application
import android.content.Context
import com.example.gmail.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App :Application() {
    init {
        instance = this
    }

    private val modulesList = listOf(
        appModule
    )

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(modulesList)
        }
    }

    companion object {
        var instance: App? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }
}