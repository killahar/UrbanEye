package com.example.urbaneye.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun InfoScreen(onBack: () -> Unit) {
    val pages = listOf("Страница 1", "Страница 2", "Страница 3")
    var currentPage by remember { mutableIntStateOf(0) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = pages[currentPage], style = MaterialTheme.typography.bodySmall)
        Spacer(modifier = Modifier.weight(1f))
        Row {
            Button(onClick = { if (currentPage > 0) currentPage-- }) {
                Text("Назад")
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(onClick = { if (currentPage < pages.size - 1) currentPage++ }) {
                Text("Вперед")
            }
        }
    }
}
