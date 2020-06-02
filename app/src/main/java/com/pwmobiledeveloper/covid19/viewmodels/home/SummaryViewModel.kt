package com.pwmobiledeveloper.covid19.viewmodels.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.pwmobiledeveloper.covid19.TimberApplication
import com.pwmobiledeveloper.covid19.model.repository.Covid19Repository
import com.pwmobiledeveloper.covid19.utils.Covid19ApiStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

class SummaryViewModel(Application: Application): AndroidViewModel(Application) {
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(
        viewModelJob + Dispatchers.Main )

    private val _status = MutableLiveData<Covid19ApiStatus>()
    val status: LiveData<Covid19ApiStatus>
        get() = _status

    private val covid19Repository = Covid19Repository(TimberApplication.database)

    val summaryGlobal = covid19Repository.summaryGlobal

    val summaryCountries = covid19Repository.summaryCountries

    init {
        Timber.d("SummaryViewModel-init")
        //refreshDataFromRepository()
    }

    fun refreshDataFromRepository() {
        coroutineScope.launch {
            _status.value =
                Covid19ApiStatus.LOADING

            try {
                covid19Repository.refreshSummary()
                _status.value =
                    Covid19ApiStatus.DONE
            } catch (networkError: Exception) {
                _status.value =
                    Covid19ApiStatus.ERROR
                Timber.d(networkError.message)


                //if(summaryCountries.value.isNullOrEmpty())
                 //   _status.value =
                //        Covid19ApiStatus.ERROR
            }
        }
    }

    fun sortCountries(orderBy: String) {
        Timber.d("test" + orderBy)
        covid19Repository.sortSummaryCountries.value = orderBy
    }

    override fun onCleared() {
        Timber.d("onCleared")

        super.onCleared()
        viewModelJob.cancel()
    }
}