package com.lamti.thegameoflife.domain

data class GameBox(
    val status: LifeStatus = LifeStatus.Dead,
    val index: Int
)

fun GameBox.findNeighbours(columns: Int, listSize: Int): List<Int> {
    val north = if (index - columns > 0) index - columns else 0
    val south = if (index + columns < listSize) index + columns else 0
    val east = if (index.rem(columns) == 0) 0 else index + 1
    val west = if ((index - 1).rem(columns) == 0) 0 else index - 1
    val northWest = if (north == 0) 0 else north - 1
    val northEast = if (north == 0) 0 else north + 1
    val southWest = if (south == 0) 0 else south - 1
    val southEast = if (south == 0) 0 else south + 1

    return listOf(north, south, east, west, northWest, northEast, southWest, southEast).filterNot { it == 0 }
}

