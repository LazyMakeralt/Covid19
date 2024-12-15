package com.example.covid19.ui.screens.goodPrac

import com.example.covid19.ui.screens.worldwide.WorldwideStatRow

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.covid19.model.MainViewModel
import com.example.covid19.ui.AnimatedSpaceBackground
import com.example.covid19.ui.AnimatedgelBackground
import com.example.covid19.ui.AnimatedhandBackground
import com.example.covid19.ui.AnimatedmaskBackground
import com.example.covid19.ui.AnimatedvaccinBackground




@Composable
fun GoodPracticesScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController?,
    mainViewModel: MainViewModel = viewModel()
) {
    // Liste des animations avec explications
    val animations = listOf(
        AnimationItem("Apply Gel", { AnimatedgelBackground() }, "Apply hand sanitizer gel to clean your hands."),
        AnimationItem("Maintain Space", { AnimatedSpaceBackground() }, "Always maintain a safe distance to prevent transmission."),
        AnimationItem("Get Vaccinated", { AnimatedvaccinBackground() }, "Vaccination helps in protecting yourself and others."),
        AnimationItem("Wash Hands", { AnimatedhandBackground() }, "Wash your hands frequently to kill viruses."),
        AnimationItem("Wear Mask", { AnimatedmaskBackground() }, "Wear a mask to reduce the spread of respiratory droplets.")
    )

    var selectedAnimation by remember { mutableStateOf<AnimationItem?>(null) }

    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        animations.forEachIndexed { index, animationItem ->
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = {
                            selectedAnimation = animationItem
                        })
                        .padding(8.dp).background(color = MaterialTheme.colorScheme.surfaceContainer).clip(MaterialTheme.shapes.medium)

                ) {
                    // Animation on the left
                    Box(
                        modifier = Modifier.size(100.dp).background(color = MaterialTheme.colorScheme.primary).clip(MaterialTheme.shapes.medium)
                    ) {
                        animationItem.animationContent()
                    }

                    Spacer(modifier = Modifier.width(16.dp))


                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = animationItem.label,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.scrim
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = animationItem.explanation,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.scrim
                        )
                    }
                }
            }
            if (index != animations.lastIndex) {
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

        }

    }

    // PopUp when we click on
    selectedAnimation?.let { animation ->
        AnimationPopup(
            label = animation.label,
            animationContent = animation.animationContent,
            explanation = animation.explanation,
            onDismiss = { selectedAnimation = null }
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Bottom
    ) {
        Button(
            onClick = { navHostController?.navigate("homeScreen") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Icon(
                Icons.Filled.ArrowBack,
                contentDescription = "back",
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text("Back")
        }
    }
}

@Composable
fun AnimationPopup(
    label: String,
    animationContent: @Composable () -> Unit,
    explanation: String,
    onDismiss: () -> Unit
) {
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
                Box(
                    modifier = Modifier.size(150.dp)
                ) {
                    animationContent() // animation of the selected item
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = label,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = explanation,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}
data class AnimationItem(
    val label: String,
    val animationContent: @Composable () -> Unit,
    val explanation: String
)
