package com.example.covid19.ui.screens.principal

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.covid19.ui.AnimatedVirusBackground
import com.example.covid19.ui.Routes
import com.example.covid19.ui.theme.Covid19Theme


@Preview(showBackground = true, showSystemUi = true)
@Preview(showBackground = true, showSystemUi = true,
    uiMode = UI_MODE_NIGHT_YES
)
@Composable
fun StartScreenPreview() {
    val navHostController = rememberNavController()
    Covid19Theme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            StartScreen(
                modifier = Modifier.padding(innerPadding),


                navHostController = navHostController )

        }
    }
}
@Composable
fun StartScreen(modifier: Modifier = Modifier, navHostController: NavHostController? ){
    Box(modifier = modifier.fillMaxSize()){
       AnimatedVirusBackground()
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Title
            Text(
                text = "Covid Statistics",
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 50.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimary),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top= 150.dp)
                    .background(MaterialTheme.colorScheme.tertiary, shape = MaterialTheme.shapes.small)
                    .wrapContentHeight(Alignment.CenterVertically),
                textAlign = TextAlign.Center,
            )
            // Enter Button
            Button(
                onClick = {

                        Log.d("Navigation", "Navigating to HomeScreen")
                        navHostController?.navigate(Routes.HomeScreen.route)

                },
                modifier = Modifier
                    .padding(top = 16.dp)
                    .size(width = 200.dp, height = 55.dp),
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiary)




            ) {
                Text(text = "Enter")
                Icon(
                    Icons.Filled.PlayArrow,
                    contentDescription = "",
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
            }

        }
    }



}