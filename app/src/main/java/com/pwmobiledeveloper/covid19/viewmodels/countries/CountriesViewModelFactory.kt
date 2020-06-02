package com.pwmobiledeveloper.covid19.viewmodels.countries

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CountriesViewModelFactory(
    private val application: Application
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CountriesViewModel::class.java)) {
            return CountriesViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown Countries ViewModel class")
    }
}