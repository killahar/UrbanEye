package com.example.urbaneye.ui.screens.incidentSequence

import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
    onBack: () -> Unit,
    totalSteps: Int,
    currentStep: Int
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
                .padding(vertical = 20.dp),
        ) {
            Text(
                text = "Проверьте, все верно?",
                color = Colors.ButtonTextColor,
                fontSize = 30.sp,
                modifier = Modifier.padding(start = 32.dp)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 32.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(totalSteps) { step ->
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(
                            if (step < currentStep) Colors.SecondaryColor else Colors.PrimaryColor
                        )
                )
                if (step < totalSteps - 1) {
                    Spacer(modifier = Modifier.width(16.dp))
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Colors.BackgroundColor)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                Text("ФИО:", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Text(viewModel.fullName, fontSize = 18.sp)
                Text("Почта:", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Text(viewModel.email, fontSize = 18.sp)
                Text("Телефон:", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Text(viewModel.phoneNumber ?: "Скрыт (анонимно)", fontSize = 18.sp)

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
                        Text("Описание инцидента:", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                        Text(
                            if (isDescriptionExpanded) viewModel.incidentDescription
                            else viewModel.incidentDescription.take(30) + "...",
                            fontSize = 18.sp
                        )
                    }
                }

                if (viewModel.photoUris.isNotEmpty()) {
                    Text(
                        text = "Прикрепленные фото:",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 16.dp))

                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(viewModel.photoUris) { uri ->
                            Box(
                                modifier = Modifier
                                    .size(120.dp)
                                    .clip(RoundedCornerShape(10.dp))
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