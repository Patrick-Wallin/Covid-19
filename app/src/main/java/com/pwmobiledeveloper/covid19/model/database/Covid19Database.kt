package com.pwmobiledeveloper.covid19.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import timber.log.Timber

@Database(entities = [TableCountries::class, TableGlobalSummary::class,
    TableCountriesSummary::class], version = 1, exportSchema = false)
abstract class Covid19Database : RoomDatabase() {
    abstract val countriesDao: CountriesDao
    abstract val summaryDao: SummaryDao
}

private lateinit var INSTANCE: Covid19Database

fun getDatabase(context: Context): Covid19Database {
    synchronized(Covid19Database::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                Covid19Database::class.java,
                "covid_database"
            ).fallbackToDestructiveMigration().build()

            //
            if(INSTANCE == null)
                Timber.d("INSTANCE is NULL")
            else
                Timber.d("INSTANCE IS NOT NULL!")
        }
    }

    return INSTANCE
}

private val CALLBACK = object : RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        Timber.d("create database")
        //db.execSQL("INSERT CATEGORY (id, name) VALUES (1, \"TESTE\") ")
    }
}
