package com.example.urbaneye.ui.screens.incidentSequence

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.urbaneye.ui.sources.Colors
import com.example.urbaneye.ui.values.CustomButton
import com.example.urbaneye.ui.values.CustomInput
import com.example.urbaneye.viewmodel.ReportIncidentViewModel

@Composable
fun AttachPhotoScreen(
    viewModel: ReportIncidentViewModel,
    onNext: () -> Unit,
    onBack: () -> Unit,
    totalSteps: Int,
    currentStep: Int
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia()
    ) { uris ->
        uris?.forEach { uri -> viewModel.attachPhoto(uri.toString())
        }
    }

    var showSnackbar by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Colors.BackgroundColor)
                .padding(paddingValues)
        ) {
            Header()
            StepIndicator(totalSteps, currentStep)
            Content(viewModel, { launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) }, onNext, onBack, { showSnackbar = true })
        }
    }

    LaunchedEffect(showSnackbar) {
        if (showSnackbar) {
            snackbarHostState.showSnackbar("Пожалуйста, добавьте хотя бы одно фото.")
            showSnackbar = false
        }
    }
}

@Composable
fun Header() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Colors.ButtonBackgroundColor)
            .padding(vertical = 20.dp)
    ) {
        Text(
            text = "Фото и описание",
            color = Colors.ButtonTextColor,
            fontSize = 30.sp,
            modifier = Modifier.padding(start = 32.dp)
        )
    }
}

@Composable
fun StepIndicator(totalSteps: Int, currentStep: Int) {
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
                    .background(if (step < currentStep) Colors.SecondaryColor else Colors.PrimaryColor)
            )
            if (step < totalSteps - 1) Spacer(modifier = Modifier.width(16.dp))
        }
    }
}

@Composable
fun Content(
    viewModel: ReportIncidentViewModel,
    launcher: () -> Unit,
    onNext: () -> Unit,
    onBack: () -> Unit,
    showSnackbar: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Colors.BackgroundColor)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomButton(
            text = "Прикрепить фото",
            onClick = launcher,
            modifier = Modifier.fillMaxWidth().padding(bottom = 32.dp)
        )

        PhotoList(viewModel)

        CustomInput(
            value = viewModel.incidentDescription,
            onValueChange = viewModel::updateIncidentDescription,
            label = { Text("Описание фотографии") },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 6,
            maxChars = 450
        )

        ActionButtons(onBack, onNext, viewModel, showSnackbar)
    }
}

@Composable
fun PhotoList(viewModel: ReportIncidentViewModel) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 32.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(viewModel.photoUris) { uri ->
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(10.dp))
            ) {
                AsyncImage(
                    model = uri,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                IconButton(
                    onClick = { viewModel.removePhoto(uri) },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .size(20.dp)
                        .background(Colors.PrimaryColor, CircleShape)
                ) {
                    Icon(Icons.Default.Close, contentDescription = null, tint = Colors.ButtonTextColor)
                }
            }
        }
    }
}

@Composable
fun ActionButtons(onBack: () -> Unit, onNext: () -> Unit, viewModel: ReportIncidentViewModel, showSnackbar: () -> Unit) {
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
                if (viewModel.photoUris.isEmpty()) {
                    showSnackbar()
                } else {
                    onNext()
                }
            },
            modifier = Modifier.padding(top = 32.dp)
        )
    }
}