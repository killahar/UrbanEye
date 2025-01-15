package com.example.urbaneye.ui.screens.incidentSequence

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.urbaneye.data.repository.IncidentRepository
import com.example.urbaneye.viewmodel.ReportIncidentViewModel

@Composable
fun ReportIncidentScreen(viewModel: ReportIncidentViewModel, repository: IncidentRepository, onBack: () -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = viewModel.description,
            onValueChange = { viewModel.updateDescription(it) },
            label = { Text("Описание происшествия") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { /* Attach photo logic */ }) {
            Text("Приложить фото")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = viewModel.isAnonymous,
                onCheckedChange = { viewModel.toggleAnonymous() }
            )
            Text("Оставить анонимным")
        }
        if (!viewModel.isAnonymous) {
            OutlinedTextField(
                value = viewModel.phoneNumber,
                onValueChange = { viewModel.phoneNumber = it },
                label = { Text("Номер телефона") },
                modifier = Modifier.fillMaxWidth()
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            viewModel.submitIncident(repository)
            onBack()
        }) {
            Text("Отправить")
        }
    }
}