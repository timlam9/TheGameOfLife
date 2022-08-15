package com.lamti.thegameoflife.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lamti.thegameoflife.ui.theme.CELL_PADDING
import com.lamti.thegameoflife.ui.theme.ELEVATION

@Composable
fun GameCell(
    text: String,
    color: Color = MaterialTheme.colors.background,
    textColor: Color = MaterialTheme.colors.onBackground,
    showText: Boolean = false
) {
    Card(
        backgroundColor = color,
        modifier = Modifier
            .padding(CELL_PADDING.dp)
            .aspectRatio(1f),
        elevation = ELEVATION.dp,
    ) {
        AnimatedVisibility(visible = showText) {
            Text(
                text = text,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = textColor,
                textAlign = TextAlign.Center,
                modifier = Modifier.wrapContentSize(Alignment.Center)
            )
        }
    }
}
