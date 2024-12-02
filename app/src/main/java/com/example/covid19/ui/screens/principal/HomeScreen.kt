package com.example.covid19.ui.screens.principal

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.covid19.ui.AnimatedContinentBackground
import com.example.covid19.ui.AnimatedCountryBackground
import com.example.covid19.ui.AnimatedQuizBackground
import com.example.covid19.ui.AnimatedWorldBackground
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
            HomeScreen(
                modifier = Modifier.padding(innerPadding),
                //jeu de donnée pour la Preview

                navHostController = navHostController)
        }
    }
}

@Composable
fun HomeScreen(modifier: Modifier = Modifier, navHostController: NavHostController?) {

    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Ajouter chaque carte avec animation à la colonne
        item {
            CardWithAnimation("Search By Continent", { AnimatedContinentBackground() }, {})
        }
        item {
            Spacer(modifier = Modifier.height(8.dp)) // Espacement entre les cartes
            CardWithAnimation("Search By Country", { AnimatedCountryBackground() }, {})
        }
        item {
            Spacer(modifier = Modifier.height(8.dp))
            CardWithAnimation("World statistic", { AnimatedWorldBackground() }, {})
        }
        item {
            Spacer(modifier = Modifier.height(8.dp))
            CardWithAnimation("Quiz", { AnimatedQuizBackground() }, {})
        }
    }

}

@Composable
fun CardWithAnimation(title: String, animationContent: @Composable () -> Unit, onClick: ()-> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp) // Espacement entre les cartes
            .height(200.dp), // Hauteur fixe pour chaque carte
        shape = MaterialTheme.shapes.medium,
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.scrim)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.tertiary)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp) // Hauteur de l'animation à l'intérieur de la carte
            ) {
                animationContent() // Contenu de l'animation
            }
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimary),
                modifier = Modifier.padding(8.dp)
                    .background(MaterialTheme.colorScheme.tertiary, shape = MaterialTheme.shapes.small)
            )
        }
    }
}


