package com.lamti.thegameoflife.domain

enum class LifeStatus {
    Dead,
    Alive;

    companion object {

        fun random(): LifeStatus = if ((0..1).random() == 0) Dead else Alive
    }
}
