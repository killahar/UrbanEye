package com.example.urbaneye.ui.screens.incidentSequence

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.urbaneye.ui.sources.Colors
import com.example.urbaneye.ui.values.CustomButton
import com.example.urbaneye.ui.values.CustomInput
import com.example.urbaneye.viewmodel.ReportIncidentViewModel

@Composable
fun AttachPhotoScreen(
    viewModel: ReportIncidentViewModel,
    onNext: () -> Unit,
    onBack: () -> Unit
) {
    var photoDescription by remember { mutableStateOf("") }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Colors.BackgroundColor)
    ) {
        // Синий заголовок
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Colors.ButtonBackgroundColor)
                .padding(vertical = 24.dp),
        ) {
            Text(
                text = "Фото и описание",
                color = Colors.ButtonTextColor,
                fontSize = 24.sp,
                modifier = Modifier.padding(start = 32.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Colors.BackgroundColor)
                .padding(horizontal = 16.dp, vertical = 32.dp),
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

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                CustomButton(
                    text = "Назад",
                    onClick = onBack,
                    modifier = Modifier.padding(top = 16.dp)
                )
                CustomButton(
                    text = "Далее",
                    onClick = {
                        viewModel.updateIncidentDescription(photoDescription)
                        onNext()
                    },
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}