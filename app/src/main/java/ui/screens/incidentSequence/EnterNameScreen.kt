package com.example.urbaneye.ui.screens.incidentSequence

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
fun EnterNameScreen(
    viewModel: ReportIncidentViewModel,
    onBack: () -> Unit,
    onNext: () -> Unit,
    totalSteps: Int,
    currentStep: Int
) {
    var surname by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var patronymic by remember { mutableStateOf("") }
    var isPatronymicAbsent by remember { mutableStateOf(false) }

    var surnameErrorMessage by remember { mutableStateOf("") }
    var nameErrorMessage by remember { mutableStateOf("") }
    var patronymicErrorMessage by remember { mutableStateOf("") }

    // Функции UI-валидации
    fun validateField(value: String, isRequired: Boolean = true): String {
        return when {
            isRequired && value.isBlank() -> "Поле обязательно для заполнения"
            !value.all { it.isLetter() } -> "Поле не должно содержать цифры или специальные символы"
            else -> ""
        }
    }
    fun validateFields(): Boolean {
        surnameErrorMessage = validateField(surname)
        nameErrorMessage = validateField(name)
        patronymicErrorMessage = if (!isPatronymicAbsent) validateField(patronymic) else ""

        return surnameErrorMessage.isEmpty() && nameErrorMessage.isEmpty() && patronymicErrorMessage.isEmpty()
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
                text = "ФИО",
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
                value = surname,
                onValueChange = { surname = it },
                label = { Text("Фамилия") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                isError = surnameErrorMessage.isNotEmpty(),
                errorMessage = surnameErrorMessage,
                filter = { it.isLetter() || it == '-' },
                maxLines = 1,
                maxChars = 50
            )
            CustomInput(
                value = name,
                onValueChange = { name = it },
                label = { Text("Имя") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                isError = nameErrorMessage.isNotEmpty(),
                errorMessage = nameErrorMessage,
                filter = { it.isLetter() || it == '-' },
                maxLines = 1,
                maxChars = 30
            )
            if (!isPatronymicAbsent) {
                CustomInput(
                    value = patronymic,
                    onValueChange = { patronymic = it },
                    label = { Text("Отчество") },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 32.dp),
                    isError = patronymicErrorMessage.isNotEmpty(),
                    errorMessage = patronymicErrorMessage,
                    filter = { it.isLetter() || it == '-' },
                    maxLines = 1,
                    maxChars = 50
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = isPatronymicAbsent,
                    onCheckedChange = {
                        isPatronymicAbsent = it
                        if (it) patronymicErrorMessage = ""
                    },
                    colors = CheckboxDefaults.colors(checkedColor = Colors.ButtonBackgroundColor)
                )
                Text("Нет отчества")
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                CustomButton(
                    text = "Назад",
                    onClick = onBack,
                    modifier = Modifier.padding(top = 32.dp)
                )
                CustomButton(
                    text = "Далее",
                    onClick = {
                        if (validateFields()) {
                            viewModel.updateName(surname, name, if (isPatronymicAbsent) "" else patronymic)
                            onNext()
                        }
                    },
                    modifier = Modifier.padding(top = 32.dp)
                )
            }
        }
    }
}