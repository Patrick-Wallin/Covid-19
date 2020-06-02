package com.pwmobiledeveloper.covid19.model.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CountriesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCountry(country: TableCountries)

    fun insertAllCountries(countries: List<TableCountries>) {
        countries.forEach {
            insertCountry(it)
        }
    }

    @Update
    fun updateCountry(park: TableCountries)

    @Query("DELETE FROM countries")
    fun clearCountries()

    @Query("SELECT * FROM countries ORDER BY country")
    fun getAllCountries(): LiveData<List<TableCountries>>
}