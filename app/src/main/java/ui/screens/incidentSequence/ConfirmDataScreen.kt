package com.example.urbaneye.ui.screens.incidentSequence

import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.background
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.urbaneye.ui.sources.Colors
import com.example.urbaneye.ui.values.CustomButton
import com.example.urbaneye.viewmodel.ReportIncidentViewModel

@Composable
fun ConfirmDataScreen(
    viewModel: ReportIncidentViewModel,
    onSubmit: () -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Colors.BackgroundColor)
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Отображение введенных данных
        Text("Все верно?", fontSize = 24.sp, color = Colors.SecondaryColor)

        Column(modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)) {
            Text("ФИО: ${viewModel.fullName}", fontSize = 18.sp)
            Text("Email: ${viewModel.email}", fontSize = 18.sp)
            Text("Телефон: ${viewModel.phoneNumber ?: "Скрыт (анонимно)"}", fontSize = 18.sp)
            Text("Описание инцидента: ${viewModel.incidentDescription}", fontSize = 18.sp)
            Text("Количество фото: ${viewModel.photoUris.size}", fontSize = 18.sp)
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            CustomButton(
                text = "Назад",
                onClick = { onBack() },
                modifier = Modifier.weight(1f).padding(end = 16.dp)
            )
            CustomButton(
                text = "Отправить",
                onClick = {
                    viewModel.submitIncident()
                    onSubmit()
                },
                modifier = Modifier.weight(1f).padding(start = 16.dp)
            )
        }
    }
}