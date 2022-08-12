package com.lamti.thegameoflife.domain

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class GameEngine {

    private val _board = MutableStateFlow(GameBoard(list = listOf(), columns = 0))
    val board: StateFlow<GameBoard> = _board

    fun createRandomBoard(range: Int, columns: Int) {
        _board.update {
            GameBoard(
                list = (1..range).map { GameBox(status = LifeStatus.random(), index = it) },
                columns = columns
            )
        }
    }

    fun updateState() {
        _board.update {
            it.nextState(it.columns)
        }
    }

    private fun GameBoard.nextState(boardColumns: Int): GameBoard {
        val resultList = mutableListOf<GameBox>()
        val neighboursList = mutableListOf<List<GameBox>>()
        val neighboursMap: MutableMap<GameBox, List<GameBox>> = mutableMapOf()

        list.forEach { box ->
            val boxNeighbours = mutableListOf<GameBox>()
            val neighboursInt: List<Int> = box.findNeighbours(boardColumns, list.size)

            neighboursInt.forEach {
                boxNeighbours.add(list[it - 1])
            }

            neighboursList.add(boxNeighbours)
            neighboursMap[box] = boxNeighbours
        }

        neighboursMap.forEach { (gameBox, list) ->
            val nextStateLifeStatus = gameBox.applyRules(list)
            resultList.add(GameBox(status = nextStateLifeStatus, index = gameBox.index))
        }

        return GameBoard(list = resultList, columns = columns)
    }

    private fun GameBox.applyRules(neighbours: List<GameBox>): LifeStatus = when {
        firstRule(neighbours) -> LifeStatus.Alive
        secondRule(neighbours) -> LifeStatus.Dead
        else -> status
    }

    private fun GameBox.firstRule(neighbours: List<GameBox>) = status == LifeStatus.Dead
            && neighbours.count { it.status == LifeStatus.Alive } == 3

    private fun GameBox.secondRule(neighbours: List<GameBox>) = status == LifeStatus.Alive
            && neighbours.count { it.status == LifeStatus.Alive } < 2
            || neighbours.count { it.status == LifeStatus.Alive } > 3
}
