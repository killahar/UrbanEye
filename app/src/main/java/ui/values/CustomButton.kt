package com.example.urbaneye.ui.values

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.urbaneye.ui.sources.Colors

@Composable
fun CustomButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Colors.ButtonBackgroundColor),
        shape = RoundedCornerShape(10.dp),
        modifier = modifier
            .wrapContentSize()
            .padding(16.dp)
    ) {
        Text(text, color = Colors.ButtonTextColor, fontSize = 20.sp)
    }
}
