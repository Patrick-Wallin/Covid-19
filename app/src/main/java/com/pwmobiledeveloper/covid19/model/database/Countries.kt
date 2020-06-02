package com.pwmobiledeveloper.covid19.model.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "countries")
data class TableCountries(
    @ColumnInfo(name = "country")
    val country: String = "",
    @PrimaryKey
    @ColumnInfo(name = "slug")
    val slug: String = "",
    @ColumnInfo(name = "iso2")
    val iso2: String = ""
)

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