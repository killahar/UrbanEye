package com.example.urbaneye.ui.screens.incidentSequence

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.urbaneye.ui.sources.Colors
import com.example.urbaneye.ui.values.CustomButton
import com.example.urbaneye.ui.values.CustomInput
import com.example.urbaneye.viewmodel.ReportIncidentViewModel

@Composable
fun EnterEmailScreen(
    viewModel: ReportIncidentViewModel,
    onNext: () -> Unit,
    onBack: () -> Unit,
    totalSteps: Int,
    currentStep: Int
) {
    var email by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var emailErrorMessage by remember { mutableStateOf("") }
    var phoneErrorMessage by remember { mutableStateOf("") }

    // Функции UI-валидации
    fun validateEmail(): String {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()
        return when {
            email.isBlank() -> "Поле обязательно для заполнения"
            !emailRegex.matches(email) -> "Введите корректный адрес электронной почты"
            else -> ""
        }
    }
    fun validatePhoneNumber(): String {
        return when {
            phoneNumber.isBlank() -> "Поле обязательно для заполнения"
            else -> ""
        }
    }
    fun validateFields(): Boolean {
        emailErrorMessage = validateEmail()
        phoneErrorMessage = validatePhoneNumber()
        return emailErrorMessage.isEmpty() && phoneErrorMessage.isEmpty()
    }

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
                text = "Почта и номер",
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
            CustomInput(
                value = email,
                onValueChange = {
                    email = it
                    emailErrorMessage = validateEmail()
                },
                label = { Text("Электронная почта") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                isError = emailErrorMessage.isNotEmpty(),
                errorMessage = emailErrorMessage,
                maxLines = 1,
                maxChars = 100
            )
            CustomInput(
                value = phoneNumber,
                onValueChange = {
                    phoneNumber = it
                    phoneErrorMessage = validatePhoneNumber()
                },
                label = { Text("Номер телефона") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 32.dp),
                isError = phoneErrorMessage.isNotEmpty(),
                errorMessage = phoneErrorMessage,
                filter = { it.isDigit() || it in "+ ()-" },
                maxLines = 1,
                maxChars = 35
            )

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                CustomButton(
                    text = "Назад",
                    onClick = onBack,
                )
                CustomButton(
                    text = "Далее",
                    onClick = {
                        if (validateFields()) {
                            viewModel.updateEmail(email)
                            viewModel.updatePhoneNumber(phoneNumber)
                            onNext()
                        }
                    },
                )
            }
        }
    }
}