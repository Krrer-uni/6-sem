package mobilki.minesweeper


import kotlin.random.Random

class GameField(val gameBoardSize: Int, val mines: Int) {

    var minesLeft = 0;
    var flagCount = 0;
    var revealedCount = 0;
    var gameState = GameState.OK

    enum class GameState {
        WIN, LOSE, OK, FLAGGED
    }


    val Board = mutableListOf<GameCell>()

    init {
        for (i in 1..gameBoardSize * gameBoardSize) {
            Board.add(GameCell())
        }

        for (i in 1..mines) {
            var r = Random.nextInt(0, gameBoardSize * gameBoardSize)
            while (Board[r].hasBomb) {
                r = (r + 1) % (gameBoardSize * gameBoardSize)
            }
            Board[r].hasBomb = true
            for (j in getNeighbours(r)) {
                Board[j].proximityBombs++
            }
        }

    }

    fun handleReveal(cellid: Int) {
        val cell = Board[cellid]
        if (cell.isFlagged) {
            gameState = GameState.FLAGGED
            return
        }
        if (cell.hasBomb) {
            gameState = GameState.LOSE
            return
        }
        if (cell.isRevealed) {
            gameState = GameState.OK
            return
        }

        cell.isRevealed = true
        revealedCount++
        val queue = MutableList<Int>(0) { _ -> 0 }
        val hashMap = HashMap<Int, Boolean>()
        if (cell.proximityBombs == 0) {
            for (x in getNeighbours(cellid)) {
                hashMap[x] = true
                queue.add(x)
            }
            while (!queue.isEmpty()) {
                val front = queue.first()
                val frontCell = Board[front]
                if (!frontCell.hasBomb && !frontCell.isFlagged) {
                    if (!frontCell.isRevealed) revealedCount++
                    frontCell.isRevealed = true
                    if (frontCell.proximityBombs == 0) {
                        for (x in getNeighbours(front)) {
                            if (!Board[x].isRevealed && hashMap[x] == null) {
                                hashMap[x] = true
                                queue.add(x)
                            }

                        }
                    }
                }

                queue.remove(queue.first())
            }
        }

        winCondition()
    }

    fun handleFlag(cellid: Int) {
        val cell = Board[cellid]
        if (cell.isRevealed)
            return

        if (cell.isFlagged) {
            flagCount--
        } else {
            flagCount++
        }
        cell.isFlagged = !cell.isFlagged

        winCondition()
    }

    private fun getNeighbours(cell: Int): MutableList<Int> {
        val exists = Array(9) { _ -> true }
        exists[4] = false
        if (cell < gameBoardSize) {
            for (i in 0..2) exists[i] = false
        }
        if (cell >= gameBoardSize * (gameBoardSize - 1)) {
            for (i in 6..8)
                exists[i] = false
        }
        if (cell % gameBoardSize == 0) {
            for (i in 0..2) exists[i * 3] = false
        }
        if ((cell + 1) % gameBoardSize == 0) {
            for (i in 0..2) exists[2 + i * 3] = false
        }
        val neighbours = mutableListOf<Int>()
        for (i in 0..8) {
            if (exists[i]) {
                neighbours.add(i + cell - 4 + (i / 3 - 1) * (gameBoardSize - 3))
            }
        }
        return neighbours
    }

    private fun winCondition() {
        if (flagCount == mines && revealedCount + flagCount == Board.size)
            gameState = GameState.WIN
    }

}