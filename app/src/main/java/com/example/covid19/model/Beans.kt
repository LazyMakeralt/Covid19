package com.example.covid19.model

import com.google.gson.annotations.SerializedName

/* -------------------------------- */
// Covid
/* -------------------------------- */
data class CovidBean(
    var continent: String?,
    var country: String,
    var population: Int,
    var cases : CasesBean,
    var deaths : DeathsBean,
    var tests: TestsBean,
    @SerializedName("day")var lastUpdate: String?
    )

data class CasesBean(
    @SerializedName("new")  var new: Int?,              // news cases in last 24H
    @SerializedName("active")var active: Int?,            // total active cases
    @SerializedName("critical") var critical: Int?,         // critical cases
    @SerializedName("recovered") var recovered: Int?,        // Total number of people who have been recovered
    @SerializedName("1M_pop") var m_Pop: String,          // total number of cases per one million
    @SerializedName("total")var total: Int?,            // total of cases
)


data class DeathsBean(
    @SerializedName("new")var newDeaths: Int?,          // news deaths in last 24H
    @SerializedName("1M_pop") var deathsPerMil: String,   // total number of deaths per one million
    @SerializedName("total") var totalDeaths: Int?,      // total of deaths
)

data class TestsBean(
    @SerializedName("1M_pop")var testPerMil: String,     // total number of test per one million
    @SerializedName("total")var totalTest: Int?,        // total of test
)

data class CovidResultBean(
    @SerializedName("response")var list: List<CovidBean>   // List of CovidBean
)

data class WorldwideStat(
    val label: String,
    val value: String
)

 // Data class for Continent Screen
data class ContinentStat(
    val continentName: String,
    val numberOfCountries: Int,
    val totalCases: String,
    val totalDeaths: String,
    val totalTests: String,
    val totalCriticalCases: String,
    val totalRecoveredCases: String,
    val totalActiveCases: String,
    val image: Int? = null
)

 // Data class for Country Stats Screen
data class CountryStat(
    val countryName: String,
    val continentName: String,
    val totalCases: String,
    val totalDeaths: String,
    val totalTests: String,
    val totalCriticalCases: String,
    val totalRecoveredCases: String,
    val totalActiveCases: String,
    val image: Int? = null
)