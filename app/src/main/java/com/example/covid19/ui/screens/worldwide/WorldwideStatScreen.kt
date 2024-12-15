package com.example.covid19.ui.screens.worldwide

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.covid19.model.MainViewModel
import com.example.covid19.ui.AnimatedWorldAnimBackground
import com.example.covid19.ui.screens.principal.HomeScreen
import com.example.covid19.ui.theme.Covid19Theme

@Preview(showBackground = true, showSystemUi = true)
@Preview(showBackground = true, showSystemUi = true,
    uiMode = UI_MODE_NIGHT_YES
)
@Composable
fun HomesScreenPreview() {
    val navHostController = rememberNavController()
    Covid19Theme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            WorldwideStatScreen(
                modifier = Modifier.padding(innerPadding),
                //jeu de donnée pour la Preview

                navHostController = navHostController)
        }
    }
}


@Composable
fun WorldwideStatScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController?,
    mainViewModel: MainViewModel = viewModel()
) {
    val statistic = mainViewModel.totals.collectAsState().value

    val errorMessage by mainViewModel.errorMessage.collectAsState()
    val isLoading by mainViewModel._runInProgress.collectAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.tertiary)
            .padding(16.dp)
    ) {
        Text(
            text = "World Statistics",
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
                onClick = {navHostController?.navigate("homeScreen")},
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
                onClick = { mainViewModel.loadCovid() },
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
            CircularProgressIndicator(modifier = Modifier.padding(top = 16.dp), color = MaterialTheme.colorScheme.onPrimary)
        } else if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.error
            )
        } else {

            statistic.forEach { stat ->
                WorldwideStatRow(label = stat.label, value = stat.value)
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedWorldAnimBackground()
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun WorldwideStatRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onTertiary
        )
        Text(
            text = value,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onTertiary
        )
    }
}
