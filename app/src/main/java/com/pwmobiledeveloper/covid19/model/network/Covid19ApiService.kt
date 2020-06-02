package com.pwmobiledeveloper.covid19.model.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api.covid19api.com/"

private val moshi: Moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface Covid19ApiService {
    companion object {

    }
    @GET("countries")
    suspend fun getCountries(): List<NetworkCountries>

    @GET("summary")
    suspend fun getSummary(): NetworkSummary
}

object Covid19Api {
    val retrofitService : Covid19ApiService by lazy {
        retrofit.create(Covid19ApiService::class.java) }
}