package com.pwmobiledeveloper.covid19.model.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "global_summary")
data class TableGlobalSummary(
    @PrimaryKey
    val globalSummaryId: Int,
    @ColumnInfo(name = "new_confirmed")
    val newConfirmed: Int = 0,
    @ColumnInfo(name = "total_confirmed")
    val totalConfirmed: Int = 0,
    @ColumnInfo(name = "new_deaths")
    val newDeaths: Int = 0,
    @ColumnInfo(name = "total_deaths")
    val totalDeaths: Int = 0,
    @ColumnInfo(name = "new_recovered")
    val newRecovered: Int = 0,
    @ColumnInfo(name = "total_recovered")
    val totalRecovered: Int = 0
)

@Entity(tableName = "countries_summary")
data class TableCountriesSummary(
    @ColumnInfo(name = "country")
    val country: String = "",
    @ColumnInfo(name = "country_code")
    val countryCode: String = "",
    @PrimaryKey
    @ColumnInfo(name = "slug")
    val slug: String = "",
    @ColumnInfo(name = "new_confirmed")
    val newConfirmed: Int = 0,
    @ColumnInfo(name = "total_confirmed")
    val totalConfirmed: Int = 0,
    @ColumnInfo(name = "new_deaths")
    val newDeaths: Int = 0,
    @ColumnInfo(name = "total_deaths")
    val totalDeaths: Int = 0,
    @ColumnInfo(name = "new_recovered")
    val newRecovered: Int = 0,
    @ColumnInfo(name = "date")
    val date: String = ""
)
/*
data class DomainCountry(val country: String,
                         val slug: String,
                         val iso2: String) {}

fun List<TableCountries>.asDomainModel(): List<DomainCountry> {
    return map {
        DomainCountry(
            country = it.country,
            slug = it.slug,
            iso2 = it.iso2
        )
    }
}

 */