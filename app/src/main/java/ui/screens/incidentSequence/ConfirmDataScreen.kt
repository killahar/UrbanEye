package com.example.urbaneye.ui.screens.incidentSequence

import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil3.compose.AsyncImage
import com.example.urbaneye.ui.sources.Colors
import com.example.urbaneye.ui.values.CustomButton
import com.example.urbaneye.viewmodel.ReportIncidentViewModel

@Composable
fun ConfirmDataScreen(
    viewModel: ReportIncidentViewModel,
    onSubmit: () -> Unit,
    onBack: () -> Unit
) {
    var selectedImageUri by remember { mutableStateOf<String?>(null) }
    var isDescriptionExpanded by remember { mutableStateOf(false) }

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
                text = "Проверьте, все верно?",
                color = Colors.ButtonTextColor,
                fontSize = 24.sp,
                modifier = Modifier.padding(start = 32.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Colors.BackgroundColor)
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                Text("ФИО: ${viewModel.fullName}", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text("Email: ${viewModel.email}", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text("Телефон: ${viewModel.phoneNumber ?: "Скрыт (анонимно)"}", fontSize = 18.sp, fontWeight = FontWeight.Bold)

                Box(
                    modifier = Modifier
                        .clickable { isDescriptionExpanded = !isDescriptionExpanded }
                        .background(Colors.BackgroundColor)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                ) {
                    Column(
                        modifier = if (isDescriptionExpanded) Modifier.verticalScroll(rememberScrollState()) else Modifier
                    ) {
                        Text(
                            text = "Описание инцидента: " + if (isDescriptionExpanded) viewModel.incidentDescription else viewModel.incidentDescription.take(50) + "...",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                if (viewModel.photoUris.isNotEmpty()) {
                    Text("Прикрепленные фото:", fontSize = 18.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 16.dp))

                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(viewModel.photoUris) { uri ->
                            Box(
                                modifier = Modifier
                                    .size(100.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .clickable { selectedImageUri = uri }
                            ) {
                                AsyncImage(
                                    model = uri,
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        }
                    }
                }
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

    if (selectedImageUri != null) {
        Dialog(onDismissRequest = { selectedImageUri = null }) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Colors.BackgroundColor)
            ) {
                AsyncImage(
                    model = selectedImageUri,
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.8f)
                        .clip(RoundedCornerShape(12.dp))
                        .align(Alignment.Center)
                )
            }
        }
    }
}