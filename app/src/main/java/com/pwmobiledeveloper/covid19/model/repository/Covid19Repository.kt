package com.pwmobiledeveloper.covid19.model.repository

import androidx.lifecycle.*
import com.pwmobiledeveloper.covid19.model.database.Covid19Database
import com.pwmobiledeveloper.covid19.model.database.TableCountriesSummary
import com.pwmobiledeveloper.covid19.model.network.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class Covid19Repository(private val database: Covid19Database) {
    val sortSummaryCountries = MutableLiveData<String>("total_confirmed_high_to_low")
    val summaryCountries = sortSummaryCountries.switchMap { sortSummaryCountries ->
        database.summaryDao.getAllCountriesSummary(sortSummaryCountries)
    }

    val countries = database.countriesDao.getAllCountries()
    //var summaryCountries = database.summaryDao.getAllCountriesSummary("total_confirmed_high_to_low")
    val summaryGlobal = database.summaryDao.getSummary()

    init {
        Timber.d("Injection Covid19Repository")
    }

    suspend fun refreshCountries() {
        withContext(Dispatchers.IO) {
            Timber.d("refreshCountries() is called")
            val countryList = Covid19Api.retrofitService.getCountries()
            //Timber.d(countryList.toString())
            database.countriesDao.insertAllCountries(countryList.asDatabaseModel())
        }
    }

    suspend fun refreshSummary() {
        withContext(Dispatchers.IO) {
            Timber.d("refreshSummary() is called")
            val summaryList = Covid19Api.retrofitService.getSummary()
            Timber.d(summaryList.toString())
            database.summaryDao.insertGlobalSummary(getGlobalSummaryAsDatabaseModel(summaryList))
            database.summaryDao.insertAllCountriesSummary(getCountriesSummaryAsDatabaseModel(summaryList))
        }
    }

    fun sortCountries(orderBy: String)  {
        //summaryCountries = database.summaryDao.getAllCountriesSummary(orderBy)
        //summaryCountries = database.summaryDao.getAllCountriesSummary("alphabetical_a_to_z")
          // summaryCountries = database.summaryDao.getAllCountriesSummarySortByTotalConfirmedHighToLow()
        //Timber.d("country name: %s", test.value?.size.toString())
    }
}