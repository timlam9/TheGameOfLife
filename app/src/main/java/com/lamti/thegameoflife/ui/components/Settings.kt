package com.lamti.thegameoflife.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lamti.thegameoflife.R
import com.lamti.thegameoflife.ui.theme.ELEVATION
import com.lamti.thegameoflife.ui.theme.PADDING
import com.lamti.thegameoflife.ui.theme.WhiteTransparent

@Composable
fun Settings(
    modifier: Modifier = Modifier,
    color: Color = WhiteTransparent,
    onCloseClicked: () -> Unit,
    onRestartClicked: () -> Unit,
    onSliderValueChanged: (Float) -> Unit,
) {
    Card(
        backgroundColor = color,
        modifier = modifier
            .fillMaxWidth()
            .padding(PADDING.dp),
        elevation = ELEVATION.dp,
    ) {
        Column(
            modifier = Modifier.padding(PADDING.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                IconButton(icon = R.drawable.ic_close, onclick = onCloseClicked)
            }
            CellSizeSlider(onSliderValueChanged)
            RestartButton(
                modifier = Modifier.padding(PADDING.dp),
                onClick = onRestartClicked
            )
        }
    }
}

@Composable
fun CellSizeSlider(onSliderValueChanged: (Float) -> Unit) {
    var sliderPosition by remember { mutableStateOf(0.2f) }
    Text(text = sliderPosition.toString())
    Slider(
        value = sliderPosition,
        onValueChange = {
            sliderPosition = it
            onSliderValueChanged(it)
        }
    )
}
