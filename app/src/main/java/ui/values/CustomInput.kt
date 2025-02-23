package com.example.urbaneye.ui.values

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.urbaneye.ui.sources.Colors

@Composable
fun CustomInput(
    value: String,
    onValueChange: (String) -> Unit,
    label: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    errorMessage: String = "",
    filter: ((Char) -> Boolean)? = null,
    maxLines: Int = Int.MAX_VALUE,
    maxChars: Int = Int.MAX_VALUE
) {
    Column(modifier = modifier) {
        OutlinedTextField(
            value = value,
            onValueChange = { newValue ->
                if (newValue.length <= maxChars && (filter == null || newValue.all { filter(it) })) {
                    onValueChange(newValue)
                }
            },
            label = label,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            isError = isError,
            singleLine = maxLines == 1,
            maxLines = maxLines,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = if (maxLines == 1) ImeAction.Done else ImeAction.Default
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = if (isError) Colors.Error else Colors.SecondaryColor,
                unfocusedBorderColor = if (isError) Colors.Error else Colors.PrimaryColor,
                focusedLabelColor = if (isError) Colors.Error else Colors.SecondaryColor,
                unfocusedLabelColor = if (isError) Colors.Error else Colors.PrimaryColor,
                cursorColor = if (isError) Colors.Error else Colors.SecondaryColor
            )
        )
        if (isError) {
            Text(
                text = errorMessage,
                color = Colors.Error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }
}