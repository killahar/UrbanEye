package com.example.urbaneye.ui.values

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.urbaneye.ui.sources.Colors

@Composable
fun CustomInput(
    value: String,
    onValueChange: (String) -> Unit,
    label: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = label,
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Colors.SecondaryColor,
            unfocusedBorderColor = Colors.PrimaryColor,
            focusedLabelColor = Colors.SecondaryColor,
            unfocusedLabelColor = Colors.PrimaryColor,
            cursorColor = Colors.SecondaryColor
        )
    )
}