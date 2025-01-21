package com.example.urbaneye.ui.screens.incidentSequence

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.urbaneye.ui.sources.Colors
import com.example.urbaneye.ui.values.CustomButton
import com.example.urbaneye.ui.values.CustomInput
import com.example.urbaneye.viewmodel.ReportIncidentViewModel

@Composable
fun EnterNameScreen(
    viewModel: ReportIncidentViewModel,
    onBack: () -> Unit,
    onNext: () -> Unit
) {
    var surname by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var patronymic by remember { mutableStateOf("") }
    var isPatronymicAbsent by remember { mutableStateOf(false) }

    var isSurnameError by remember { mutableStateOf(false) }
    var isNameError by remember { mutableStateOf(false) }
    var isPatronymicError by remember { mutableStateOf(false) }

    fun validateFields(): Boolean {
        val surnameValid = surname.isNotBlank() && surname.all { it.isLetter() }
        val nameValid = name.isNotBlank() && name.all { it.isLetter() }
        val patronymicValid = patronymic.isNotBlank() && patronymic.all { it.isLetter() } || isPatronymicAbsent

        isSurnameError = !surnameValid
        isNameError = !nameValid
        isPatronymicError = !patronymicValid && !isPatronymicAbsent

        return surnameValid && nameValid && patronymicValid
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Colors.BackgroundColor)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomInput(
            value = surname,
            onValueChange = { surname = it },
            label = { Text("Фамилия") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
            isError = isSurnameError,
            errorMessage = "Обязательное для заполнения поле"
        )
        CustomInput(
            value = name,
            onValueChange = { name = it },
            label = { Text("Имя") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
            isError = isNameError,
            errorMessage = "Обязательное для заполнения поле"
        )
        if (!isPatronymicAbsent) {
            CustomInput(
                value = patronymic,
                onValueChange = { patronymic = it },
                label = { Text("Отчество") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                isError = isPatronymicError,
                errorMessage = "Обязательное для заполнения поле"
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = isPatronymicAbsent,
                onCheckedChange = {
                    isPatronymicAbsent = it
                    if (it) isPatronymicError = false
                }
            )
            Text("Нет отчества")
        }
        CustomButton(
            text = "Далее",
            onClick = {
                if (validateFields()) {
                    viewModel.updateName(surname, name, if (isPatronymicAbsent) "" else patronymic)
                    onNext()
                }
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
