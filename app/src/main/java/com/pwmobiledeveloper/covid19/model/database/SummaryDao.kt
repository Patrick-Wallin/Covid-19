package com.pwmobiledeveloper.covid19.model.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*

@Dao
interface SummaryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGlobalSummary(summaryGlobal: TableGlobalSummary)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCountriesSummary(summaryCountries: TableCountriesSummary)

    fun insertAllCountriesSummary(countries: List<TableCountriesSummary>) {
        countries.forEach {
            insertCountriesSummary(it)
        }
    }

    @Update
    fun updateGlobalSummary(summaryGlobal: TableGlobalSummary)

    @Query("DELETE FROM global_summary")
    fun clearGlobalSummary()

    @Query("DELETE FROM countries_summary")
    fun clearCountriesSummary()

    fun getAllCountriesSummary(orderBy: String) : LiveData<List<TableCountriesSummary>> {
        when(orderBy) {
            "total_confirmed_high_to_low" -> {
                return getAllCountriesSummarySortByTotalConfirmedHighToLow()
            }
            "total_confirmed_low_to_high" -> {
                return getAllCountriesSummarySortByTotalConfirmedLowToHigh()
            }
            "alphabetical_a_to_z" -> {
                return getAllCountriesSummarySortByCountryAToZ()
            }
            "alphabetical_z_to_a" -> {
                return getAllCountriesSummarySortByCountryZToA()
            }
        }

        return getAllCountriesSummarySortByTotalConfirmedHighToLow()



    }


    @Query("SELECT * FROM countries_summary ORDER BY total_confirmed desc")
    fun getAllCountriesSummarySortByTotalConfirmedHighToLow(): LiveData<List<TableCountriesSummary>>

    @Query("SELECT * FROM countries_summary ORDER BY total_confirmed asc")
    fun getAllCountriesSummarySortByTotalConfirmedLowToHigh(): LiveData<List<TableCountriesSummary>>

    @Query("SELECT * FROM countries_summary ORDER BY country ASC")
    fun getAllCountriesSummarySortByCountryAToZ(): LiveData<List<TableCountriesSummary>>

    @Query("SELECT * FROM countries_summary ORDER BY country desc")
    fun getAllCountriesSummarySortByCountryZToA(): LiveData<List<TableCountriesSummary>>

    @Query("SELECT * FROM global_summary")
    fun getSummary(): LiveData<TableGlobalSummary>

}