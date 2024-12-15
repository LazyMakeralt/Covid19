package com.example.covid19.ui

import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.getValue
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.covid19.R


@Composable
fun AnimatedVirusBackground() {

    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.virus_anim)
    )


    LottieAnimation(
        composition = composition,
        iterations = LottieConstants.IterateForever,
        modifier = Modifier.fillMaxSize() ,
        contentScale = ContentScale.Crop
    )
}
@Composable
fun AnimatedCountryBackground() {

    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.country)
    )


    LottieAnimation(
        composition = composition,
        iterations = LottieConstants.IterateForever,
        modifier = Modifier.fillMaxWidth().height(150.dp),
        contentScale = ContentScale.Crop
    )
}
@Composable
fun AnimatedContinentBackground() {

    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.continent)
    )


    LottieAnimation(
        composition = composition,
        iterations = LottieConstants.IterateForever,
        modifier = Modifier.fillMaxWidth().height(150.dp),
        contentScale = ContentScale.Crop
    )
}
@Composable
fun AnimatedQuizBackground() {

    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.quiz)
    )


    LottieAnimation(
        composition = composition,
        iterations = LottieConstants.IterateForever,
        modifier = Modifier.fillMaxWidth().height(150.dp),
        contentScale = ContentScale.Crop
    )
}
@Composable
fun AnimatedWorldBackground() {

    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.world)
    )


    LottieAnimation(
        composition = composition,
        iterations = LottieConstants.IterateForever,
        modifier = Modifier.fillMaxWidth().height(150.dp),
        contentScale = ContentScale.Crop
    )
}
@Composable
fun AnimatedPraticsBackground() {

    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.pratics)
    )


    LottieAnimation(
        composition = composition,
        iterations = LottieConstants.IterateForever,
        modifier = Modifier.fillMaxWidth().height(150.dp),
        contentScale = ContentScale.Crop
    )
}
@Composable
fun AnimatedWorldAnimBackground() {

    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.worldanim)
    )


    LottieAnimation(
        composition = composition,
        iterations = LottieConstants.IterateForever,
        modifier = Modifier.fillMaxWidth().height(150.dp),
        contentScale = ContentScale.Crop
    )
}
@Composable
fun AnimatedmaskBackground() {

    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.mask)
    )


    LottieAnimation(
        composition = composition,
        iterations = LottieConstants.IterateForever,
        modifier = Modifier.fillMaxWidth().height(150.dp),
        contentScale = ContentScale.Crop
    )
}
@Composable
fun AnimatedhandBackground() {

    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.handcleaning)
    )


    LottieAnimation(
        composition = composition,
        iterations = LottieConstants.IterateForever,
        modifier = Modifier.fillMaxWidth().height(150.dp),
        contentScale = ContentScale.Crop
    )
}
@Composable
fun AnimatedvaccinBackground() {

    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.vaccin)
    )


    LottieAnimation(
        composition = composition,
        iterations = LottieConstants.IterateForever,
        modifier = Modifier.fillMaxWidth().height(150.dp),
        contentScale = ContentScale.Crop
    )
}
@Composable
fun AnimatedSpaceBackground() {

    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.space)
    )


    LottieAnimation(
        composition = composition,
        iterations = LottieConstants.IterateForever,
        modifier = Modifier.fillMaxWidth().height(150.dp),
        contentScale = ContentScale.Crop
    )
}
@Composable
fun AnimatedgelBackground() {

    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.gel)
    )


    LottieAnimation(
        composition = composition,
        iterations = LottieConstants.IterateForever,
        modifier = Modifier.fillMaxWidth().height(150.dp),
        contentScale = ContentScale.Crop
    )
}
@Composable
fun AnimatedlearnBackground() {

    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.learn)
    )


    LottieAnimation(
        composition = composition,
        iterations = LottieConstants.IterateForever,
        modifier = Modifier.fillMaxWidth().height(150.dp),
        contentScale = ContentScale.Crop
    )
}