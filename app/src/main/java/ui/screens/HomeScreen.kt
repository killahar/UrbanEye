package com.example.urbaneye.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.urbaneye.ui.sources.Colors
import com.example.urbaneye.ui.values.CustomButton

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Colors.BackgroundColor)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomButton(
            text = "Сообщить о происшествии",
            onClick = { navController.navigate("enterName")}
        )
        CustomButton(
            text = "Справка",
            onClick = { navController.navigate("info") }
        )
    }
}