package com.example.covid19.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.covid19.ui.screens.continent.getContinentImage
import com.example.covid19.ui.screens.country.getCountryFlagResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.math.abs


class MainViewModel : ViewModel(){

    // General attribute
    val dataList = MutableStateFlow(emptyList<CovidBean>())
    val _runInProgress = MutableStateFlow(false)
    val errorMessage = MutableStateFlow("")

    // attribute for World Stats
    private val totalsWorld = MutableStateFlow<List<WorldwideStat>>(emptyList())
    val totals: StateFlow<List<WorldwideStat>> = totalsWorld

    // attribute for Continent Stats
    private val _continentStats = MutableStateFlow<List<ContinentStat>>(emptyList())
    val continentStats: StateFlow<List<ContinentStat>> = _continentStats

    // attribute for Country Stats
    private val _countryStats = MutableStateFlow<List<CountryStat>>(emptyList())
    val countryStats: StateFlow<List<CountryStat>> = _countryStats

    // function for WORLD Statistic
    fun loadCovid() {
        errorMessage.value = ""
        _runInProgress.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {

                delay(5000)
                var list: List<CovidBean> = CovidRepository.loadCovid()

                dataList.value = list.map {
                    CovidBean(
                        continent = it.continent ?: "Unknown",
                        country =  it.country,
                        population = it.population,
                        cases = it.cases,
                        deaths = it.deaths,
                        tests = it.tests,
                        lastUpdate = it.lastUpdate.also { date -> println("Loaded date : $date") }
                    )
                }
                calculateTotalsFromCountries(dataList.value)

            } catch (e: Exception) {

                errorMessage.value = "Error loading data: ${e.message}"

            }finally {
                _runInProgress.value = false
            }
        }
    }
    // function for Continent Statistic
    fun loadCovidContinent() {
        errorMessage.value = ""
        _runInProgress.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                delay(5000)

                //  list of covid bean from Repository
                val list: List<CovidBean> = CovidRepository.loadCovid()


                dataList.value = list.map {
                    CovidBean(
                        continent = it.continent ?: "Unknown",
                        country = it.country,
                        population = it.population,
                        cases = it.cases,
                        deaths = it.deaths,
                        tests = it.tests,
                        lastUpdate = it.lastUpdate.also { date -> println("Loaded date : $date") }
                    )
                }

                // calculate total
                val continentStats = calculateTotalsFromContinents(list) // List covid Bean in param

                // Updata data for Continent
                _continentStats.value = continentStats

            } catch (e: Exception) {
                errorMessage.value = "Error loading data: ${e.message}"
            } finally {
                _runInProgress.value = false
            }
        }
    }
    // function for Country Statistic
    fun loadCovidCountry() {
        errorMessage.value = ""
        _runInProgress.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                delay(5000)

                // list of covid bean from Repository
                val list: List<CovidBean> = CovidRepository.loadCovid()


                dataList.value = list.map {
                    CovidBean(
                        continent = it.continent ?: "Unknown",
                        country = it.country,
                        population = it.population,
                        cases = it.cases,
                        deaths = it.deaths,
                        tests = it.tests,
                        lastUpdate = it.lastUpdate.also { date -> println("Loaded date : $date") }
                    )
                }

                // calculate total
                val countryStats = calculateTotalsFromCountrie(list) // List of covidBean in parameters

                // Update values for countries
                _countryStats.value = countryStats

            } catch (e: Exception) {
                errorMessage.value = "Error loading data: ${e.message}"
            } finally {
                _runInProgress.value = false
            }
        }
    }
    // function to calculate Total of the world
    private fun calculateTotalsFromCountries(countryData: List<CovidBean>) {
        val totalCases = countryData.sumOf { it.cases.total ?: 0 }
        val totalDeaths = countryData.sumOf { it.deaths.totalDeaths ?: 0 }
        val totalTests = countryData.sumOf { it.tests.totalTest ?: 0 }
        val totalCritical = countryData.sumOf { it.cases.critical ?: 0 }
        val totalRecovered = countryData.sumOf { it.cases.recovered ?: 0 }
        val totalActive = countryData.sumOf { it.cases.active ?: 0 }

        totalsWorld.value = listOf(
            WorldwideStat("Total Cases", formatNumber(abs(totalCases))),
            WorldwideStat("Total Deaths", formatNumber(abs(totalDeaths))),
            WorldwideStat("Total Tests", formatNumber(abs(totalTests))),
            WorldwideStat("Total Critical Cases", formatNumber(abs(totalCritical))),
            WorldwideStat("Total Recovered Cases", formatNumber(abs(totalRecovered))),
            WorldwideStat("Total Active Cases", formatNumber(abs(totalActive))),
        )
    }
    fun formatNumber(number: Int): String {
        return String.format("%,d", number).replace(",", " ")
    }

    // function to calculate Total of all continent
    private fun calculateTotalsFromContinents(continentData: List<CovidBean>): List<ContinentStat> {
        val groupedByContinent = continentData.filter { it.continent != null } // Filtrer les données où continent n'est pas null
            .groupBy { it.continent }

        // List of stats for each Continent
        val continentStats = groupedByContinent.map { (continent, countries) ->
            // Calculer les totaux pour chaque continent
            val totalCases = countries.sumOf { it.cases?.total ?: 0 }
            val totalDeaths = countries.sumOf { it.deaths?.totalDeaths ?: 0 }
            val totalTests = countries.sumOf { it.tests?.totalTest ?: 0 }
            val totalCritical = countries.sumOf { it.cases?.critical ?: 0 }
            val totalRecovered = countries.sumOf { it.cases?.recovered ?: 0 }
            val totalActive = countries.sumOf { it.cases?.active ?: 0 }
            val imageResId = getContinentImage(continent ?: "Unknown")

            // Object Continent
            ContinentStat(
                continentName = continent ?: "Unknown",  // if null "Unknow"
                numberOfCountries = countries.size,
                totalCases = formatNumber(abs(totalCases)),
                totalDeaths = formatNumber(abs(totalDeaths)),
                totalTests = formatNumber(abs(totalTests)),
                totalCriticalCases = formatNumber(abs(totalCritical)),
                totalRecoveredCases = formatNumber(abs(totalRecovered)),
                totalActiveCases = formatNumber(abs(totalActive)),
                image = imageResId

            )
        }

        // return list of continent stats
        return continentStats
    }
    // function to calculate Total of all country
    private fun calculateTotalsFromCountrie(countryData: List<CovidBean>): List<CountryStat> {

        val groupedByCountry = countryData.filter { it.country != null } // filter where the countrie is not null
            .groupBy { it.country }

        // List of stats for each countries
        val countryStats = groupedByCountry.map { (country, stats) ->
            val totalCases = stats.sumOf { it.cases?.total ?: 0 }
            val totalDeaths = stats.sumOf { it.deaths?.totalDeaths ?: 0 }
            val totalTests = stats.sumOf { it.tests?.totalTest ?: 0 }
            val totalCritical = stats.sumOf { it.cases?.critical ?: 0 }
            val totalRecovered = stats.sumOf { it.cases?.recovered ?: 0 }
            val totalActive = stats.sumOf { it.cases?.active ?: 0 }
            val continentName = stats.firstOrNull()?.continent ?: "Unknown" // Name of continent
            val imageResId = getCountryFlagResource(country ?: "Unknown")


            CountryStat(
                countryName = country,
                continentName = continentName,
                totalCases = formatNumber(abs(totalCases)),
                totalDeaths = formatNumber(abs(totalDeaths)),
                totalTests = formatNumber(abs(totalTests)),
                totalCriticalCases = formatNumber(abs(totalCritical)),
                totalRecoveredCases = formatNumber(abs(totalRecovered)),
                totalActiveCases = formatNumber(abs(totalActive)),
                image = imageResId
            )
        }

        return countryStats
    }



}


