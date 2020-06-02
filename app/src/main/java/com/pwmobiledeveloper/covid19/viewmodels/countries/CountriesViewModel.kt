package com.pwmobiledeveloper.covid19.viewmodels.countries

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pwmobiledeveloper.covid19.TimberApplication
import com.pwmobiledeveloper.covid19.model.repository.Covid19Repository
import com.pwmobiledeveloper.covid19.utils.Covid19ApiStatus
import com.pwmobiledeveloper.covid19.utils.utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

//enum class Covid19ApiStatus { LOADING, ERROR, DONE }

class CountriesViewModel(Application: Application): AndroidViewModel(Application) {
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(
        viewModelJob + Dispatchers.Main )

    private val _status = MutableLiveData<Covid19ApiStatus>()
    val status: LiveData<Covid19ApiStatus>
        get() = _status

    //private var instanceValue : ParksDatabase = getDatabase(application)

    private val covid19Repository = Covid19Repository(TimberApplication.database)
    //private val parksRepository = ParksRepository(getDatabase(application))

    //val countryList = covid19Repository.countries

    init {
        Timber.d("CountriesViewModel-init")
        //refreshDataFromRepository()
    }

    fun refreshDataFromRepository() {
        coroutineScope.launch {
            _status.value =
                Covid19ApiStatus.LOADING

            try {
                covid19Repository.refreshCountries()
                _status.value =
                    Covid19ApiStatus.DONE
            } catch (networkError: Exception) {
                _status.value =
                    Covid19ApiStatus.ERROR
                Timber.d(networkError.message)
                //if(countryList.value.isNullOrEmpty())
               //     _status.value =
               //         Covid19ApiStatus.ERROR
            }
        }
    }

    override fun onCleared() {
        Timber.d("onCleared")

        super.onCleared()
        viewModelJob.cancel()
    }
}