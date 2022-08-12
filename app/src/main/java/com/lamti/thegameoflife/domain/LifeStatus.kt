package com.lamti.thegameoflife.domain

import androidx.compose.ui.graphics.Color

enum class LifeStatus(val color: Color) {
    Dead(Color.DarkGray),
    Alive(Color.White);

    companion object {

        fun random(): LifeStatus = if ((0..1).random() == 0) Dead else Alive
    }
}
