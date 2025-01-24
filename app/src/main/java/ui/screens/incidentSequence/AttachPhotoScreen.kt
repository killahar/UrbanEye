package com.example.urbaneye.ui.screens.incidentSequence

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.urbaneye.ui.sources.Colors
import com.example.urbaneye.ui.values.CustomButton
import com.example.urbaneye.ui.values.CustomInput
import com.example.urbaneye.viewmodel.ReportIncidentViewModel
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.LaunchedEffect

@Composable
fun AttachPhotoScreen(
    viewModel: ReportIncidentViewModel,
    onNext: () -> Unit,
    onBack: () -> Unit,
    totalSteps: Int,
    currentStep: Int
) {
    var photoDescription by remember { mutableStateOf("") }
    val photoUris = remember { mutableStateListOf<Uri>() }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia()
    ) { uris ->
        if (uris != null) {
            val newUris = uris.filterNot { photoUris.contains(it) } // Исключаем дубликаты
            val totalUris = (photoUris + newUris).take(10) // Ограничиваем общее количество 10
            photoUris.clear()
            photoUris.addAll(totalUris)
            viewModel.updatePhotoUris(photoUris.map { it.toString() })
        }
    }

    // Состояние для Snackbar
    val snackbarHostState = remember { SnackbarHostState() }
    var showSnackbar by remember { mutableStateOf(false) }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Colors.BackgroundColor)
                .padding(paddingValues)
        ) {
            // Синий заголовок
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Colors.ButtonBackgroundColor)
                    .padding(vertical = 20.dp),
            ) {
                Text(
                    text = "Фото и описание",
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
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CustomButton(
                    text = "Прикрепить фото",
                    onClick = { launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 32.dp),
                )

                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 32.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(photoUris) { uri ->
                        Box(
                            modifier = Modifier
                                .size(120.dp)
                                .clip(RoundedCornerShape(10.dp)) // Скругление углов
                        ) {
                            AsyncImage(
                                model = uri,
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                            IconButton(
                                onClick = {
                                    photoUris.remove(uri)
                                    viewModel.updatePhotoUris(photoUris.map { it.toString() })
                                },
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .size(20.dp)
                                    .background(Colors.PrimaryColor, CircleShape)
                            ) {
                                Icon(
                                    Icons.Default.Close,
                                    contentDescription = null,
                                    tint = Colors.ButtonTextColor
                                )
                            }
                        }
                    }
                }

                CustomInput(
                    value = photoDescription,
                    onValueChange = { photoDescription = it },
                    label = { Text("Описание фотографии") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 6,
                    maxChars = 450
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    CustomButton(
                        text = "Назад",
                        onClick = onBack,
                        modifier = Modifier.padding(top = 32.dp)
                    )
                    CustomButton(
                        text = "Далее",
                        onClick = {
                            if (photoUris.isEmpty()) {
                                // Устанавливаем состояние для показа Snackbar
                                showSnackbar = true
                            } else {
                                viewModel.updateIncidentDescription(photoDescription)
                                onNext()
                            }
                        },
                        modifier = Modifier.padding(top = 32.dp)
                    )
                }
            }
        }
    }

    // LaunchedEffect для показа Snackbar
    LaunchedEffect(showSnackbar) {
        if (showSnackbar) {
            snackbarHostState.showSnackbar("Пожалуйста, добавьте хотя бы одно фото.")
            showSnackbar = false // Сбрасываем состояние после показа
        }
    }
}