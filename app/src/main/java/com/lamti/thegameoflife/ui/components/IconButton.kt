package com.lamti.thegameoflife.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.lamti.thegameoflife.ui.theme.WhiteTransparent

@Composable
fun IconButton(
    modifier: Modifier = Modifier,
    size: Dp = 42.dp,
    cornerRadius: Int = 20,
    shape: Shape = RoundedCornerShape(cornerRadius),
    tint: Color = MaterialTheme.colors.onBackground,
    backgroundColor: Color = WhiteTransparent,
    icon: Int,
    onclick: () -> Unit
) {
    Button(
        modifier = modifier.size(size),
        onClick = onclick,
        shape = shape,
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = backgroundColor)
    ) {
        Icon(
            painter = painterResource(id = icon),
            tint = tint,
            contentDescription = "icon button"
        )
    }
}
