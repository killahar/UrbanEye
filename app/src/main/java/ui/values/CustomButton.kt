package com.example.urbaneye.ui.values

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CustomButton() {
    Column {
        Button(
            onClick = { /* do something on click */ },
            colors = ButtonDefaults.buttonColors(containerColor = androidx.compose.ui.graphics.Color.Magenta),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.padding(8.dp).width(120.dp).height(40.dp)
        ) {
            Text("Click Me")
        }
    }
}