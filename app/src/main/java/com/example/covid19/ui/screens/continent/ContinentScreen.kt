package com.example.covid19.ui.screens.continent

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.covid19.R
import com.example.covid19.model.ContinentStat
import com.example.covid19.model.MainViewModel


@Composable
fun ContinentScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController?,
    mainViewModel: MainViewModel = viewModel()
) {
    val continentStats = mainViewModel.continentStats.collectAsState().value
    val errorMessage by mainViewModel.errorMessage.collectAsState()
    val isLoading by mainViewModel._runInProgress.collectAsState()

    var selectedContinent by remember { mutableStateOf<ContinentStat?>(null) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.tertiary)
            .padding(16.dp)
    ) {
        Text(
            text = "Continent Statistics",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onTertiary,
            modifier = Modifier.padding(bottom = 16.dp, top = 50.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { navHostController?.navigate("homeScreen") },
                modifier = Modifier.weight(1f).padding(end = 8.dp)
            ) {
                Icon(
                    Icons.Filled.ArrowBack,
                    contentDescription = "back",
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text("Back")
            }
            Button(
                onClick = { mainViewModel.loadCovidContinent() },
                modifier = Modifier.weight(1f).padding(start = 8.dp)
            ) {
                Icon(
                    Icons.Filled.Refresh,
                    contentDescription = "Load Data",
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text("Load Data ")
            }
        }

        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.padding(top = 16.dp),
                color = MaterialTheme.colorScheme.onPrimary
            )
        } else if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.error
            )
        } else {
            // Filter to not show the continent ALL
            val filteredStats = continentStats.filterNot { it.continentName == "All" }

            LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                items(filteredStats) { stat ->
                    ContinentStatCard(
                        stat = stat,
                        onClick = { selectedContinent = stat }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }

    // POPUP for each continent when we click on
    selectedContinent?.let { continent ->
        ContinentPopup(
            continent = continent,
            onDismiss = { selectedContinent = null }
        )
    }
}

@Composable
fun ContinentStatCard(stat: ContinentStat, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(8.dp)
            .background(
                color = MaterialTheme.colorScheme.surfaceContainer,
                shape = MaterialTheme.shapes.medium
            )
            .clip(MaterialTheme.shapes.medium)
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = MaterialTheme.shapes.medium
                )
                .clip(MaterialTheme.shapes.medium)
        ) {
            stat.image?.let { imageResId ->
                Image(
                    painter = painterResource(id = imageResId),
                    contentDescription = "${stat.continentName} Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            } ?: Text( // if no img put the delfault one
                text = stat.continentName.take(1),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = stat.continentName,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.scrim
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Total Cases: ${stat.totalCases}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.scrim
            )
            Text(
                text = "......",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.scrim
            )
        }
    }
}

@Composable
fun ContinentPopup(continent: ContinentStat, onDismiss: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background.copy(alpha = 0.8f))
            .clickable(onClick = onDismiss)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .background(MaterialTheme.colorScheme.primary, shape = MaterialTheme.shapes.medium)
                .padding(24.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = continent.continentName,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Number of Countries: ${continent.numberOfCountries}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Text(
                    text = "Total Cases: ${continent.totalCases}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Text(
                    text = "Total Deaths: ${continent.totalDeaths}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Text(
                    text = "Total Tests: ${continent.totalTests}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Text(
                    text = "Total Critical Cases: ${continent.totalCriticalCases}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Text(
                    text = "Total Recovered Cases: ${continent.totalRecoveredCases}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Text(
                    text = "Total Active Cases: ${continent.totalActiveCases}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}
fun getContinentImage(continentName: String): Int {
    val formattedName = continentName.replace("-", "_").lowercase()
    return when (continentName) {
        "Africa" -> R.raw.africa
        "Asia" -> R.raw.asia
        "Europe" -> R.raw.europe
        "North_America" -> R.raw.north_america
        "South_America" -> R.raw.south_america
        "Oceania" -> R.raw.oceania
        else -> R.raw.placeholder
    }
}

