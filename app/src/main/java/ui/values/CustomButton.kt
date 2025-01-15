package com.example.urbaneye.ui.values

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.urbaneye.ui.sources.Colors

@Composable
fun CustomButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Colors.ButtonBackground),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .padding(8.dp)
            .width(150.dp)
            .height(50.dp)
    ) {
        Text(text, color = Colors.ButtonText)
    }
}
