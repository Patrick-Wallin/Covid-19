package com.pwmobiledeveloper.covid19

import android.app.Application
import com.pwmobiledeveloper.covid19.model.database.Covid19Database
import com.pwmobiledeveloper.covid19.model.database.getDatabase
import timber.log.Timber

class TimberApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
        database = getDatabase(applicationContext)
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    companion object {
        lateinit var instance: TimberApplication
        lateinit var database: Covid19Database
    }
}