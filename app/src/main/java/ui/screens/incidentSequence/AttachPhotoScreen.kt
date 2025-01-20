package com.example.urbaneye.ui.screens.incidentSequence

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.urbaneye.ui.sources.Colors
import com.example.urbaneye.ui.values.CustomButton
import com.example.urbaneye.ui.values.CustomInput
import com.example.urbaneye.viewmodel.ReportIncidentViewModel

@Composable
fun AttachPhotoScreen(
    viewModel: ReportIncidentViewModel,
    onSubmit: () -> Unit,
    onBack: () -> Unit
) {
    var photoDescription by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .background(Colors.BackgroundColor)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomButton(
            text = "Прикрепить фото",
            onClick = { /* Logic to attach photo */ },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        )
        CustomInput(
            value = photoDescription,
            onValueChange = { photoDescription = it },
            label = { Text("Описание фотографии") },
            modifier = Modifier.fillMaxWidth()
        )
        CustomButton(
            text = "Отправить",
            onClick = {
                viewModel.updateIncidentDescription(photoDescription)
                onSubmit()
            },
            modifier = Modifier.padding(top = 16.dp)
        )
        CustomButton(
            text = "Назад",
            onClick = onBack,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}