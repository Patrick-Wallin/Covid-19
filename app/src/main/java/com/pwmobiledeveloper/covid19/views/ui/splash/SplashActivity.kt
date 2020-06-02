package com.pwmobiledeveloper.covid19.views.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.pwmobiledeveloper.covid19.MainActivity
import com.pwmobiledeveloper.covid19.R
import com.pwmobiledeveloper.covid19.databinding.ActivitySplashBinding
import com.pwmobiledeveloper.covid19.utils.Covid19ApiStatus
import com.pwmobiledeveloper.covid19.viewmodels.countries.CountriesViewModel
import com.pwmobiledeveloper.covid19.viewmodels.countries.CountriesViewModelFactory
import com.pwmobiledeveloper.covid19.viewmodels.home.SummaryViewModel
import com.pwmobiledeveloper.covid19.viewmodels.home.SummaryViewModelFactory
import kotlinx.coroutines.*
import timber.log.Timber

class SplashActivity : AppCompatActivity() {
    val activityScope = CoroutineScope(Dispatchers.Main)

    companion object {
        val SPLASH_SCREEN = 100L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        val binding = DataBindingUtil.setContentView<ActivitySplashBinding>(this,
            R.layout.activity_splash
        )

        val topAnimation = android.view.animation.AnimationUtils.loadAnimation(this,
            R.anim.top_animation
        )
        val bottomAnimation = android.view.animation.AnimationUtils.loadAnimation(this,
            R.anim.bottom_animation
        )

        val bottomHorizontalAnimation = android.view.animation.AnimationUtils.loadAnimation(this,
            R.anim.horizontal_bottom_animation
        )

        binding.covid19Imageview.animation = topAnimation
        binding.covid19Textview.animation = bottomAnimation
        binding.loadingMessageTextview.animation = bottomHorizontalAnimation

        val countriesViewModelFactory = CountriesViewModelFactory(application)
        val countriesViewModel = ViewModelProvider(this, countriesViewModelFactory).get(CountriesViewModel::class.java)
        countriesViewModel.refreshDataFromRepository()

        val summaryViewModelFactory = SummaryViewModelFactory(application)
        val summaryViewModel = ViewModelProvider(this, summaryViewModelFactory).get(SummaryViewModel::class.java)
        summaryViewModel.refreshDataFromRepository()
        //viewModel.countryList.observe(this, Observer {
          //  Timber.d("done")
            //Timber.d(it.toString())
        //})
        activityScope.launch {
            var statusLoading = true
            while(statusLoading) {
                statusLoading = (countriesViewModel.status.value == Covid19ApiStatus.LOADING || summaryViewModel.status.value == Covid19ApiStatus.LOADING)
                delay(SPLASH_SCREEN)
                //Timber.d("waiting for it is to be done or error")
            }

            var intent = Intent(baseContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onPause() {
        activityScope.cancel()
        super.onPause()
    }
}
