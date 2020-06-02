package com.pwmobiledeveloper.covid19.model.network

import androidx.lifecycle.Transformations.map
import com.pwmobiledeveloper.covid19.model.database.DomainCountry
import com.pwmobiledeveloper.covid19.model.database.TableCountries
import com.pwmobiledeveloper.covid19.model.database.TableCountriesSummary
import com.pwmobiledeveloper.covid19.model.database.TableGlobalSummary

data class NetworkCountries(
    val Country: String,
    val Slug: String,
    val ISO2: String
)

data class NetworkSummary(
    val Global: NetworkGlobal,
    val Countries: List<NetworkCountry>,
    val Date: String
)

data class NetworkCountry(
    val Country: String,
    val CountryCode: String,
    val Slug: String,
    val NewConfirmed: Int,
    val TotalConfirmed: Int,
    val NewDeaths: Int,
    val TotalDeaths: Int,
    val NewRecovered: Int,
    val Date: String
)

data class NetworkGlobal(
    val NewConfirmed: Int,
    val TotalConfirmed: Int,
    val NewDeaths: Int,
    val TotalDeaths: Int,
    val NewRecovered: Int,
    val TotalRecovered: Int
)

/*
fun NetworkCountries.asDomainModel(): List<DomainCountry> {
    return data.map {
        DomainCountry(
            countr = it.,
            name = it.name,
            addresses = it.addresses
        )
    }
}

 */

fun List<NetworkCountries>.asDatabaseModel(): List<TableCountries> {
    return map {
        TableCountries(it.Country, it.Slug, it.ISO2)
    }
}

fun getGlobalSummaryAsDatabaseModel(networkSummary: NetworkSummary): TableGlobalSummary {
    return TableGlobalSummary(
        1,
        networkSummary.Global.NewConfirmed,
        networkSummary.Global.TotalConfirmed,
        networkSummary.Global.NewDeaths,
        networkSummary.Global.TotalDeaths,
        networkSummary.Global.NewRecovered,
        networkSummary.Global.TotalRecovered
        )
}

fun getCountriesSummaryAsDatabaseModel(networkSummary: NetworkSummary) : List<TableCountriesSummary> {
    return networkSummary.Countries.map {
        TableCountriesSummary(
            it.Country, it.CountryCode, it.Slug,
            it.NewConfirmed, it.TotalConfirmed, it.NewDeaths,
            it.TotalDeaths, it.NewRecovered, it.Date)
    }
}
/*
fun NetworkSummary.asDatabaseModel(): List<TableSummary> {

}

 */


