package com.lamti.thegameoflife.ui.components

import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun TextButton(modifier: Modifier, text: String, onClick: () -> Unit) {
    Button(
        modifier = modifier,
        onClick = onClick,
        colors = ButtonDefaults.textButtonColors(backgroundColor = Color.LightGray)
    ) {
        Text(text, color = MaterialTheme.colors.onBackground)
    }
}
