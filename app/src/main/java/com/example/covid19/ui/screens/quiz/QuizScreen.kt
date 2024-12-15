package com.example.covid19.ui.screens.quiz

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding

import androidx.compose.foundation.layout.width

import androidx.compose.material3.Button

import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme

import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

import com.example.covid19.model.MainViewModel
import com.example.covid19.ui.AnimatedlearnBackground


@Composable
fun QuizzScreen(
    modifier: Modifier = Modifier.background(MaterialTheme.colorScheme.tertiary),
    navHostController: NavHostController? = null,
    mainViewModel: MainViewModel = viewModel()
) {
    val questions = listOf(
        "Whats is the number of case in France ?" to ("40.138.560" to listOf("40.138.560", "35", "35.905.305", "7.456.424")),
        "How many critical cases are currently recorded to date ?" to ("72.465" to listOf("850", "72.465", "0", "2.865.345")),
        "How many people have been affected by covid ?" to ("2.114.260.946" to listOf("5", "3.005.425", "2.114.260.946", "1.456.700.345")),
        "Total deaths of Asia ? " to ("3.107.324" to listOf("3.107.324", "367", "156.356.436", "35.678.345")),
        "What is the minimum safety distance to respect ? " to ("1m" to listOf("1m", "0", "10m", "2m")),
        "What is the total of active Case in Europe ?" to ("3.425.491" to listOf("2.000", "45.978", "3.425.491", "49")),
        "how many countries are there in africa ?" to ("59" to listOf("170", "35", "4", "59")),
        "Total of test in USA" to ("1.186.851.502" to listOf("4", "45.809", "500.345.354", "1.186.851.502")),
        "How many deaths due to Covid in the World" to ("21.032.028" to listOf("21.032.028", "70.572.407", "42", "2.456.031")),
        "Number of death of Nauru for 5.393 registered case ?" to ("1" to listOf("1", "20", "369", "14"))
    )

    var currentQuestionIndex by remember { mutableStateOf(0) }
    var selectedAnswer by remember { mutableStateOf<String?>(null) }
    var correctAnswers by remember { mutableStateOf(0) }
    var isQuizFinished by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        if (isQuizFinished) {
            // Result
            Text(
                text = "Result !",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onTertiary,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Text(
                text = "You correctly answered at $correctAnswers questions on ${questions.size}.",
                color = MaterialTheme.colorScheme.onTertiary,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            val message = when {
                correctAnswers == questions.size -> "well done ! You answered all the questions correctly you are a real pro"
                correctAnswers > questions.size / 2 -> "Good job! this is a good score"
                else -> "Start again, keep learning !"
            }

            Text(
                text = message,
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onTertiary,
                modifier = Modifier.padding(bottom = 32.dp)

            )

            Button(
                onClick = {
                    isQuizFinished = false
                    currentQuestionIndex = 0
                    correctAnswers = 0
                    selectedAnswer = null
                },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(text = "start again", color = MaterialTheme.colorScheme.onTertiary,)
            }
            Button(
                onClick = {
                    navHostController?.navigate("homeScreen")
                },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(text = "Home", color = MaterialTheme.colorScheme.onTertiary,)
            }
            Spacer(modifier = Modifier.height(150.dp))
            AnimatedlearnBackground()
        } else {
            // print of question
            Text(
                text = "Quizz",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onTertiary,
                modifier = Modifier.padding(16.dp)
            )

            LinearProgressIndicator(
                progress = (currentQuestionIndex + 1) / questions.size.toFloat(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Question ${currentQuestionIndex + 1}/${questions.size}: ${questions[currentQuestionIndex].first}",
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onTertiary,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Column(modifier = Modifier.fillMaxWidth()) {
                questions[currentQuestionIndex].second.second.forEach { answer ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                    ) {
                        RadioButton(
                            selected = selectedAnswer == answer,
                            colors = RadioButtonDefaults.colors(
                                selectedColor = Color.White,
                                unselectedColor = MaterialTheme.colorScheme.onTertiary
                            ),
                            onClick = { selectedAnswer = answer }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = answer, fontSize = 16.sp, color = MaterialTheme.colorScheme.onTertiary,)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = {
                        if (currentQuestionIndex > 0) {
                            currentQuestionIndex--
                            selectedAnswer = null
                        }
                    },
                    enabled = currentQuestionIndex > 0
                ) {
                    Text("Previous")
                }
                Button(
                    onClick = {
                        navHostController?.navigate("homeScreen")
                    },
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text(text = "Home", color = MaterialTheme.colorScheme.onTertiary,)
                }

                Button(
                    onClick = {
                        if (selectedAnswer == questions[currentQuestionIndex].second.first) {
                            correctAnswers++
                        }
                        if (currentQuestionIndex < questions.size - 1) {
                            currentQuestionIndex++
                            selectedAnswer = null
                        } else {
                            isQuizFinished = true
                        }
                    },
                    enabled = selectedAnswer != null
                ) {
                    Text(if (currentQuestionIndex < questions.size - 1) "Next" else "Finish")
                }
            }
            Spacer(modifier = Modifier.height(150.dp))
            AnimatedlearnBackground()
        }
    }
}




